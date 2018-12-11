package com.core.service;

import com.base.model.Essay;
import com.base.model.Picture;
import com.base.pojo.LucenePage;
import com.base.pojo.Page;
import com.base.util.CommonUtil;
import com.base.util.LuceneUtil;
import com.base.util.RequestContextHolderUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebService {
	@Autowired
	AboutMeService aboutMeService;
	@Autowired
	EssayService essayService;
	@Autowired
	MoodService moodService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	PictureService pictureService;
	@Autowired
	UserService userService;

	/**
	 * 首页得到前四张幻灯片记录
	 * 
	 * @return
	 */
	public List<Picture> indexPicture() {
		Page.setPageSize(4);
		List<Picture> list = pictureService.getPictureByPage("index");
		return list;
	}

    /**
     * 首页得到前五条文章记录
     * @param showType
     * @return
     */
	public List<Essay> indexEssay(String showType) {
		Page.setPageSize(3);
		List<Essay> list = essayService.getEssayByPage("essay",showType);
		return list;
	}

	/**
	 * 判断session中的用户身份 -1未登录 0普通 1管理员
	 * 
	 * @return
	 */
	public int role() {
        String userName=(String) RequestContextHolderUtil.getSession().getAttribute("userName");
        if(!CommonUtil.isNull(userName)){
            return userService.role(userName);
        }
		return -1;
	}

	/**
	 * 创建索引
	 * 
	 */
	public void createIndex(Essay essay) {
		// 创建Document对象
		Document doc = new Document();
		// 获取每列数据
		Field essayId = new Field("essayId", essay.getEssayId().toString(), TextField.TYPE_STORED);
		Field text = new Field("text", essay.getText().toString(), TextField.TYPE_STORED);
		// 添加到Document中
		doc.add(essayId);
		doc.add(text);
		// 以主键作为域进行添加更新操作
		Term term = new Term("essayId", essay.getEssayId().toString());
		// 调用，创建索引库
		LuceneUtil.addDoc(term, doc);
	}

	/**
	 * 删除索引
	 * 
	 * @param essay
	 */
	public void deleteIndex(Essay essay) {
		// 以主键作为域进行添加更新操作
		Term term = new Term("essayId", essay.getEssayId().toString());
		// 调用，创建索引库
		LuceneUtil.deleteDoc(term);
	}

	/**
	 * 根据输入的keyword对文章内容进行搜索
	 * 
	 * @param pageIndex 当前页数
	 * @param keyWord 关键词
	 * @return
	 * @throws Exception
	 */
	public LucenePage listEssayBySearch(int pageIndex, String keyWord) throws Exception {
		return LuceneUtil.search(pageIndex, LucenePage.pageSize, "text", keyWord);
	}
}