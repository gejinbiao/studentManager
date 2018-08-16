package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.constant.tips.ErrorTip;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.log.Log4jUtil;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.entity.VisitorRecord;
import com.stylefeng.guns.modular.system.service.VisitorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 回访记录控制器
 *
 * @author fengshuonan
 * @Date 2018-07-17 18:22:23
 */
@Controller
@RequestMapping("/VisitorRecord")
public class VisitorRecordController extends BaseController {

    @Autowired
    private VisitorRecordService visitorRecordService;

    private String PREFIX = "/system/VisitorRecord/";

    /**
     * 跳转到回访记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "VisitorRecord.html";
    }

    /**
     * 跳转到添加回访记录
     */
    @RequestMapping("/VisitorRecord_add")
    public String VisitorRecordAdd() {
        return PREFIX + "VisitorRecord_add.html";
    }

    /**
     * 跳转到修改回访记录
     */
    @RequestMapping("/VisitorRecord_update/{visitorRecordId}")
    public String VisitorRecordUpdate(@PathVariable Integer visitorRecordId, ModelMap model) {

        if (ToolUtil.isEmpty(visitorRecordId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        VisitorRecord visitorRecord = visitorRecordService.selectVisitorRecordByKey(visitorRecordId);
        model.put("visitorRecord", visitorRecord);
        return PREFIX + "VisitorRecord_edit.html";
    }

    /**
     * 获取回访记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(VisitorRecord bean, PageReq params) {

        try {
            if (bean == null || bean.getStudentId() == null) {
                return new ErrorTip(0, "学生id为空");
            }
            //List<VisitorRecord> visitorRecord = visitorRecordService.selectByExample(bean, params);
            List<VisitorRecord> visitorRecord = visitorRecordService.selectVisitByStudentId(bean, params);
            return packForBT(visitorRecord);
        } catch (Exception e) {
            Log4jUtil.error(e, "查询回访记录错误");
        }
        return null;
    }

    /**
     * 新增回访记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(VisitorRecord bean) {

        try {
            ShiroUser user = ShiroKit.getUser();
            bean.setOperator(user.getId());
            int i = visitorRecordService.addVisitorRecord(bean);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "添加失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "添加回访记录错误");
            return ErrorTip.error();
        }
    }

    /**
     * 删除回访记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String visitorRecordIds) {

        if (ToolUtil.isEmpty(visitorRecordIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        int i = visitorRecordService.deleteVisitorRecord(visitorRecordIds);
        if (i > 0) {
            return SUCCESS_TIP;
        } else {
            return new ErrorTip(0, "删除失败");
        }
    }


    /**
     * 修改回访记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid VisitorRecord bean, BindingResult result) {

        try {
            if (result.hasErrors()) {
                throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
            }
            int i = visitorRecordService.updateVisitorRecord(bean);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "修改失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "修改回访记录错误");
            return ErrorTip.error();
        }
    }

    /**
     * 回访记录详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
