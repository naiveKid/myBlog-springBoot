package com.core.job;

import com.base.model.Essay;
import com.base.model.PickRedPacket;
import com.base.util.DateTimeUtil;
import com.base.util.LuceneUtil;
import com.core.redis.RedisTemplateUtils;
import com.core.service.EssayService;
import com.core.service.RedPacketService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Configuration
@EnableScheduling
public class SystemJob {
    @Autowired
    EssayService essayService;
    @Autowired
    RedPacketService redPacketService;
    @Autowired
    RedisTemplateUtils redisTemplateUtils;

    /**
     * 启动30秒执行一次之后每10个小时执行一次(为创建索引之前的已存在的数据创建索引)
     */
    @Scheduled(initialDelay = 30000, fixedDelay = 36000000)
    public void createLuceneIndexJob() {
        // 查询数据库，必须要批量查询
        int count = essayService.getCount("essay");// 查询文章总记录数
        int start = 0; // 开始位置
        int rows = 5; // 每页行数

        while (start < count) {
            // 每拉取一次数据
            List<Essay> essayList = essayService.getEssayLimitRows("essay", start, rows);
            // 获取字段
            for (int i = 0; i < essayList.size(); i++) {
                // 获取每行数据
                Essay essay = essayList.get(i);
                // 创建Document对象
                Document doc = new Document();
                // 获取每列数据
                Field essayId = new Field("essayId", essay.getEssayId().toString(), TextField.TYPE_STORED);
                Field text = new Field("text", essay.getContent().toString(), TextField.TYPE_STORED);
                // 添加到Document中
                doc.add(essayId);
                doc.add(text);
                // 以主键作为域进行添加更新操作
                Term term = new Term("essayId", essay.getEssayId().toString());
                // 调用，创建索引库
                LuceneUtil.addDoc(term, doc);
            }
            start += rows;
        }
    }

    /**
     * 定时同步redis存放的文章点击数到数据库
     * 单位毫秒，小于redis过期时间300秒即可
     * @return: void
     */
    @Scheduled(initialDelay = 20000, fixedDelay = 20000)
    public void createRedisJob() {
        Set<Object> keyList = redisTemplateUtils.keys("essayId_"+"*");//模糊搜索
        for (Object o : keyList) {
            String key=(String) o;
            if (key.matches("^essayId_\\d+_click")) {//判断是否匹配
                String essayId=key.substring(key.lastIndexOf("essayId_") + 8, key.lastIndexOf("_click"));
                Integer clickNum =(Integer) redisTemplateUtils.get(key);

                Essay essay = new Essay();
                essay.setEssayId(Integer.parseInt(essayId));
                essay.setClickNum(clickNum);
                essay=essayService.updateEssay(essay);
                if(essay!=null){
                    //将对应的redis缓存删除
                    redisTemplateUtils.del(key);
                }
            }
        }
    }

    /**
     * 清理超过一天仍未发完的红包活动
     */
    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void clearOverTimeRedPacket() throws ParseException {
        //一次清理一页即可
        List<PickRedPacket> pickRedPacketList=redPacketService.getPickRedPacketRestNumberByPage();
        for (PickRedPacket pickRedPacket:pickRedPacketList){
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            String doTime=pickRedPacket.getDoTime();
            Date d1 =df.parse(doTime);

            String nowTime=DateTimeUtil.getNowDateStr(6);
            Date d2 =df.parse(nowTime);

            long diff = d2.getTime() - d1.getTime();//差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);//相差的天数
            if (days>=1L){//超过一天,则版本号置为-1表示该记录不再参与红包活动
                redPacketService.saveUserRedPacketByRedis(pickRedPacket.getId(),pickRedPacket.getRestNumber(),-1);
            }
        }
    }
}