package com.core.controller;

import com.base.model.OperationLog;
import com.core.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    /**
     * 分页查询系统日志
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/listPagedOperationLog")
    public ModelAndView listPagedOperationLog() {
        ModelAndView mav = new ModelAndView("/admin/operationLog");
        List<OperationLog> list = systemLogService.listPagedOperationLog();
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 查询日志详细信息
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/showOperationLog/{id}")
    public ModelAndView showOperationLog(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("/admin/showOperationLog");
        OperationLog log = systemLogService.getOperationLog(id);
        mav.addObject("log", log);
        return mav;
    }

    /**
     * 删除指定日志记录
     * @param id
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/delOperationLog/{id}",method = RequestMethod.GET)
    public ModelAndView delOperationLog(@PathVariable String id){
        ModelAndView mav = new ModelAndView("blank");
        systemLogService.delOperationLog(id);
        mav.addObject("msg", "删除成功!");
        mav.addObject("gotoPage", "systemLog/listPagedOperationLog");
        return mav;
    }
}