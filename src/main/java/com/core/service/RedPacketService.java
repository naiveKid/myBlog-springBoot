package com.core.service;

import com.alibaba.fastjson.JSONObject;
import com.base.model.PickRedPacket;
import com.base.model.RedPacket;
import com.base.model.UserInfo;
import com.base.util.DateTimeUtil;
import com.base.util.RequestContextHolderUtil;
import com.core.dao.PickRedPacketMapper;
import com.core.dao.RedPacketMapper;
import com.core.redis.RedisTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * @Description: 红包业务逻辑类
 */
@Service
public class RedPacketService {
    @Autowired
    UserService userService;
    @Autowired
    PickRedPacketMapper pickRedPackeDao;
    @Autowired
    RedPacketMapper redPacketDao;
    @Autowired
    RedisTemplateUtils redisTemplateUtils;

    //  -- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空
    //  -- 参数：红包队列名,已消费的队列名，去重的Map名，用户ID
    //  -- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money
    String tryGetHongBaoScript =
            "if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then\n"//如果用户已抢过红包，则返回nil
                    + "return nil\n"
                    + "else\n"
                    + "local hongBao = redis.call('rpop', KEYS[1]);\n"//先从未消费队列取出一个小红包
                    + "if hongBao then\n"//未消费队列不为空
                    + "local re = string.gsub(hongBao, \"#\",KEYS[4]) ;\n"//加入用户id信息
                    + "redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);\n"//把用户ID放到去重的set里 hashSet的key和value都是KEYS[4]
                    + "redis.call('lpush', KEYS[2], re);\n"//把红包放到已消费队列里
                    + "return re;\n"
                    + "end\n"
                    + "end\n"
                    + "return nil";

    /**
     * 分页查看发起的红包活动
     *
     * @return
     */
    public List<PickRedPacket> getPickRedPacketByPage() {
        return pickRedPackeDao.getPickRedPacketByPage();
    }

    /**
     * 分页查看可以参与抢红包的活动
     *
     * @return
     */
    public List<PickRedPacket> getPickRedPacketRestNumberByPage() {
        return pickRedPackeDao.getPickRedPacketRestNumberByPage();
    }

    /**
     * 新增红包活动
     *
     * @param pickRedPacket
     */
    public int addPickRedPacket(PickRedPacket pickRedPacket) {
        return pickRedPackeDao.insert(pickRedPacket);
    }

    /**
     * 均分红包金额算法
     *
     * @param money
     * @param num
     * @return
     */
    public double[] avgMoney(double money, int num) {
        double[] dou = new double[num];
        double avgNum = money / num;
        for (int i = 0; i < num; i++) {
            dou[i] = avgNum;
        }
        return dou;
    }

    /**
     * 随机生成红包金额算法
     * <p>
     * 首先算出红包的平均值，再通过一个随机红包数小于这个平均值的红包，当该红包发完后，红包总金额需要相应减少，
     * 同时重新计算这个平均值：新的总金额%（原总红包数-1），
     * 直到最后一个红包时，把所有的剩余金额塞进去
     *
     * @param money
     * @param num
     * @return
     */
    public double[] randMoney(double money, int num) {
        Random r = new Random();
        DecimalFormat format = new DecimalFormat(".##");

        double middle = Double.parseDouble(format.format(money / num));
        double[] dou = new double[num];
        double redMoney = 0;
        double nextMoney = money;
        double sum = 0;
        int index = 0;
        for (int i = num; i > 0; i--) {
            if (i == 1) {
                dou[index] = nextMoney;
            } else {
                while (true) {
                    String str = format.format(r.nextDouble() * nextMoney);
                    redMoney = Double.parseDouble(str);
                    if (redMoney > 0 && redMoney < middle) {
                        break;
                    }
                }
                nextMoney = Double.parseDouble(format.format(nextMoney - redMoney));
                sum = sum + redMoney;
                dou[index] = redMoney;
                middle = Double.parseDouble(format.format(nextMoney / (i - 1)));
                index++;
            }
        }
        return dou;
    }

    /**
     * 预先生成小红包的相关金额信息缓存
     *
     * @param pickRedPacket
     */
    public void createRedPacketCache(PickRedPacket pickRedPacket) {
        int num = pickRedPacket.getNumber();
        double[] dou;
        if (pickRedPacket.getType() == 0) {//金额等分
            dou = avgMoney(pickRedPacket.getSumMoney().floatValue(), num);
        } else {//随机金额
            dou = randMoney(pickRedPacket.getSumMoney().floatValue(), num);
        }
        //生成的金额放入未消费队列
        JSONObject object = new JSONObject();
        for (int i = 0; i < num; i++) {
            object.put("id", i);
            object.put("money", dou[i]);
            object.put("userId", "#");//赋予空值
            redisTemplateUtils.lSetRightPush("redPacket_" + pickRedPacket.getId(), object.toJSONString(), 3600 * 24);//缓存时间1天
        }
    }

    /**
     * 将红包活动剩余个数重置
     *
     * @param packetId
     * @param restNumber
     */
    @Async//开启新的线程运行
    public void saveUserRedPacketByRedis(Integer packetId, Integer restNumber, Integer version) {
        PickRedPacket pickRedPacket = new PickRedPacket();
        pickRedPacket.setId(packetId);
        pickRedPacket.setRestNumber(restNumber);
        pickRedPacket.setVersion(version);
        pickRedPackeDao.update(pickRedPacket);
        //将相关的缓存置为10秒过期
        redisTemplateUtils.expire("redPacket_" + packetId, 10);
        redisTemplateUtils.expire("useRedPacket_" + packetId, 10);
        redisTemplateUtils.expire("userRedPacketMap_" + packetId, 10);
    }

    /**
     * 抢红包
     *
     * @param packetId
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public double doRedPacket(Integer packetId) {
        HttpSession session = RequestContextHolderUtil.getSession();
        PickRedPacket pickRedPacket = pickRedPackeDao.getPickRedPacketById(packetId);
        if (pickRedPacket.getRestNumber() == 0 || pickRedPacket.getVersion() != 0) {
            return -2;//活动已结束
        }
        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            UserInfo user = userService.getUser(userName);
            //加载lua脚本
            redisTemplateUtils.getJedis().scriptLoad(tryGetHongBaoScript);
            Object object = redisTemplateUtils.getJedis().eval(tryGetHongBaoScript, 4, "redPacket_" + packetId, "useRedPacket_" + packetId, "userRedPacketMap_" + packetId, user.getUserId() + "");

            if (object != null) {//没有重复领取,且库存大于0
                String jsonStr = (String) object;
                jsonStr = jsonStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                //生成红包信息
                RedPacket redPacket = new RedPacket();
                redPacket.setDoTime(DateTimeUtil.getNowDateStr(1));
                redPacket.setPacketId(packetId);
                redPacket.setUserId(jsonObject.getIntValue("userId"));
                redPacket.setMoney(jsonObject.getBigDecimal("money"));
                redPacketDao.insert(redPacket);
                if (redisTemplateUtils.getJedis().llen("redPacket_" + packetId) == 0) {
                    saveUserRedPacketByRedis(packetId, 0, 0);
                }
                return jsonObject.getBigDecimal("money").doubleValue();
            } else {
                //未消费红包队列长度为0,则表示没有可用红包，触发异步保存到数据库
                if (redisTemplateUtils.getJedis().llen("redPacket_" + packetId) == 0) {
                    saveUserRedPacketByRedis(packetId, 0, 0);
                } else {
                    return -1;//重复领取
                }
            }
        }
        return 0;//失败
    }

    /**
     * 得到总记录条数
     *
     * @return
     */
    public int getCount() {
        return pickRedPackeDao.countInt();
    }

    /**
     * 得到可领取的记录条数
     *
     * @return
     */
    public int countRestNumber() {
        return pickRedPackeDao.countRestNumber();
    }
}