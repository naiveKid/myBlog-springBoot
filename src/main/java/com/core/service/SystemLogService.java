package com.core.service;

import com.base.model.OperationLog;
import com.base.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogService {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 插入日志数据
     * @param log
     * @return void
     */
    public void insertOperationLog(OperationLog log) {
        mongoTemplate.insert(log);//向myBlog数据库的operationLog集合插入一条文档
    }

    /**
     * 分页查询系统日志
     * @return
     */
    public List<OperationLog> listPagedOperationLog() {
        Query query = new Query();
        query.skip(Page.getOffset());// 从那条记录开始
        query.limit(Page.getPageSize());// 取多少条记录
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));//排序规则
        List<OperationLog> list = mongoTemplate.find(query, OperationLog.class);//得到operationLog集合中符合条件的文档记录
        long count = mongoTemplate.count(query, OperationLog.class);//得到operationLog集合中文档的条数
        Page.setTotalCount((int) count);
        return list;//返回分页对象
    }

    /**
     * 查询日志详情
     *
     * @param id
     */
    public OperationLog getOperationLog(String id) {
        return mongoTemplate.findById(id, OperationLog.class);//得到myBlog数据库的operationLog集合中对应的一个文档记录
    }

    /**
     * 删除指定的日志记录
     * @param id
     */
    public void delOperationLog(String id) {
        //根据id查询到对应的文档查询对象
        Query query = Query.query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,OperationLog.class);//执行删除文档操作
    }
}