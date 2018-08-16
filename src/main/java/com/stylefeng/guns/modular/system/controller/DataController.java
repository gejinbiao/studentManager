package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 数据处理控制器
 *
 * @author fengshuonan
 * @Date 2018-06-21 20:54:53
 */
@Controller
@RequestMapping("/data")
public class DataController extends BaseController {

    private String PREFIX = "/system/data/";

    /**
     * 跳转到数据处理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "data.html";
    }

    /**
     * 跳转到添加数据处理
     */
    @RequestMapping("/data_add")
    public String dataAdd() {
        return PREFIX + "data_add.html";
    }

    /**
     * 跳转到修改数据处理
     */
    @RequestMapping("/data_update/{dataId}")
    public String dataUpdate(@PathVariable Integer dataId, Model model) {
        return PREFIX + "data_edit.html";
    }

    /**
     * 获取数据处理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增数据处理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除数据处理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改数据处理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 数据处理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
