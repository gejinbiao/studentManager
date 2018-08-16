package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.constant.tips.ErrorTip;
import com.stylefeng.guns.common.constant.tips.SuccessTip;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.log.Log4jUtil;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.entity.RoleDataAuthority;
import com.stylefeng.guns.modular.system.service.RoleDataAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色数据权限控制器
 *
 * @author fengshuonan
 * @Date 2018-07-06 16:44:35
 */
@Controller
@RequestMapping("/RoleDataAuthority")
public class RoleDataAuthorityController extends BaseController {

    @Autowired
    private RoleDataAuthorityService roleDataAuthorityService;

    private String PREFIX = "/system/RoleDataAuthority/";

    /**
     * 跳转到角色数据权限首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "RoleDataAuthority.html";
    }

    /**
     * 跳转到添加角色数据权限
     */
    @RequestMapping("/RoleDataAuthority_add")
    public String RoleDataAuthorityAdd() {
        return PREFIX + "RoleDataAuthority_add.html";
    }

    /**
     * 跳转到修改角色数据权限
     */
    @RequestMapping("/RoleDataAuthority_update/{roleDataAuthorityId}")
    public String RoleDataAuthorityUpdate(@PathVariable Integer roleDataAuthorityId, ModelMap model) {

        if (ToolUtil.isEmpty(roleDataAuthorityId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        RoleDataAuthority roleDataAuthority = roleDataAuthorityService.selectRoleDataAuthorityByKey(roleDataAuthorityId);
        model.put("roleDataAuthority", roleDataAuthority);
        return PREFIX + "RoleDataAuthority_edit.html";
    }

    /**
     * 获取角色数据权限列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(RoleDataAuthority bean, PageReq params) {

        try {

            List<RoleDataAuthority> roleDataAuthority = roleDataAuthorityService.selectByExample(bean, params);
            return packForBT(roleDataAuthority);
        } catch (Exception e) {
            Log4jUtil.error(e, "查询角色数据权限错误");
        }
        return null;
    }

    /**
     * 新增角色数据权限
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(RoleDataAuthority bean) {

        try {
            if (bean.getRoleId() == null) {
                return ErrorTip.error("角色id为空");
            }
            ShiroUser user = ShiroKit.getUser();
            bean.setOpreator(user.getId());
            int i = roleDataAuthorityService.addRoleDataAuthority(bean);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "添加失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "添加角色数据权限错误");
            return ErrorTip.error();
        }
    }

    /**
     * 删除角色数据权限
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String roleDataAuthorityIds) {

        if (ToolUtil.isEmpty(roleDataAuthorityIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        int i = roleDataAuthorityService.deleteRoleDataAuthority(roleDataAuthorityIds);
        if (i > 0) {
            return SUCCESS_TIP;
        } else {
            return new ErrorTip(0, "删除失败");
        }
    }


    /**
     * 修改角色数据权限
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid RoleDataAuthority bean, BindingResult result) {

        try {
            if (result.hasErrors()) {
                throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
            }
            int i = roleDataAuthorityService.updateRoleDataAuthority(bean);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "修改失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "修改角色数据权限错误");
            return ErrorTip.error();
        }
    }

    /**
     * 角色数据权限详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(Integer roleId) {
        RoleDataAuthority roleDataAuthorityParam = new RoleDataAuthority();
        roleDataAuthorityParam.setRoleId(roleId);
        RoleDataAuthority roleDataAuthority = roleDataAuthorityService.selectOneByExample(roleDataAuthorityParam);
        /*Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleDataAuthority", roleDataAuthority);*/
        SuccessTip successTip = new SuccessTip(roleDataAuthority);
        return successTip;
    }
}
