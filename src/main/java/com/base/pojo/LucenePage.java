package com.base.pojo;

import com.base.model.Essay;

import java.util.List;

/**
 * 用于lucene全文搜索分页的Java bean
 *
 */
public class LucenePage {
	//存放搜索文章的结果集合
	private List<Essay> list;
	//存放搜索符合条件的总条数
	private Integer totalCount;
	//存放每页条数
	public static Integer pageSize = 15;

	public List<Essay> getList() {
		return list;
	}

	public void setList(List<Essay> list) {
		this.list = list;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
