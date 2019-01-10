package com.core.service;

import com.base.model.Notice;
import com.base.model.UserInfo;
import com.base.util.RequestContextHolderUtil;
import com.core.dao.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
	@Autowired
	NoticeMapper noticeDao;
	@Autowired
	UserService userService;

	/**
	 * 得到分页留言记录
	 *
	 * @return
	 */
	public List<Notice> getNoticeByPage() {
		return noticeDao.getNoticeByPage();
	}

	/**
	 * 得到总记录条数
	 *
	 * @return
	 */
	public int getCount() {
		return noticeDao.countInt();
	}

	/**
	 * 执行新增操作
	 *
	 * @param content
	 * @return
	 */
	public void addNotice(String content) {
		String userName = (String) RequestContextHolderUtil.getSession().getAttribute("userName");
		UserInfo user = userService.getUser(userName);
		Notice notice = new Notice();
		notice.setContent(content);
		notice.setDoUserId(user.getUserId());
		noticeDao.insertSelective(notice);
	}

	/**
	 * 执行删除操作
	 *
	 * @param id
	 */
	public void delNotice(int id) {
		noticeDao.deleteByPrimaryKey(id);
	}
}
