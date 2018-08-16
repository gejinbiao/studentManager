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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.stylefeng.guns.modular.system.service.TestUserService;
import com.stylefeng.guns.modular.system.entity.TestUser;

import javax.validation.Valid;
import java.util.List;

/**
 * 测试用户控制器
 *
 * @author fengshuonan
 * @Date 2018-07-04 10:27:05
 */
@Controller
@RequestMapping("/TestUser")
public class TestUserController extends BaseController {

    @Autowired
    private TestUserService testUserService;

    private String PREFIX = "/system/TestUser/";

    /**
     * 跳转到测试用户首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "TestUser.html";
    }

    /**
     * 跳转到添加测试用户
     */
    @RequestMapping("/TestUser_add")
    public String TestUserAdd() {
        return PREFIX + "TestUser_add.html";
    }

    /**
     * 跳转到修改测试用户
     */
    @RequestMapping("/TestUser_update/{testUserId}")
    public String TestUserUpdate(@PathVariable Integer testUserId, ModelMap model) {

        if (ToolUtil.isEmpty(testUserId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        TestUser testUser = testUserService.selectTestUserByKey(testUserId);
        model.put("testUser", testUser);
        return PREFIX + "TestUser_edit.html";
    }

    /**
     * 获取测试用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TestUser bean, PageReq params) {

        List<TestUser> testUser = testUserService.selectByExample(bean, params);
        return packForBT(testUser);
    }

    /**
     * 新增测试用户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TestUser bean) {

        try {
            ShiroUser user = ShiroKit.getUser();
            bean.setOpreator(String.valueOf(user.getId()));
            int i = testUserService.addTestUser(bean);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "添加失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "添加学生信息错误");
            return ErrorTip.error();
        }
    }

    /**
     * 删除测试用户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String testUserIds) {

        if (ToolUtil.isEmpty(testUserIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        int i = testUserService.deleteTestUser(testUserIds);
        if (i > 0) {
            return SUCCESS_TIP;
        } else {
            return new ErrorTip(0, "删除失败");
        }
    }


    /**
     * 修改测试用户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid TestUser bean, BindingResult result) {

        try {
            if (result.hasErrors()) {
                throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
            }
            int i = testUserService.updateTestUser(bean);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "修改失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "修改学生信息错误");
            return ErrorTip.error();
        }
    }

    /**
     * 测试用户详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
