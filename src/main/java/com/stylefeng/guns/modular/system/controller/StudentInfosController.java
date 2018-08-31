package com.stylefeng.guns.modular.system.controller;

import com.github.crab2died.ExcelUtils;
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
import com.stylefeng.guns.modular.system.entity.StudentInfos;
import com.stylefeng.guns.modular.system.entity.VisitorRecord;
import com.stylefeng.guns.modular.system.service.StudentInfosService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生信息控制器
 *
 * @author fengshuonan
 * @Date 2018-07-04 14:41:16
 */
@Controller
@RequestMapping("/StudentInfos")
public class StudentInfosController extends BaseController {

    @Autowired
    private StudentInfosService studentInfosService;

    private String PREFIX = "/system/StudentInfos/";

    /**
     * 跳转到学生信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "StudentInfos.html";
    }

    /**
     * 跳转到添加学生信息
     */
    @RequestMapping("/StudentInfos_add")
    public String StudentInfosAdd() {
        return PREFIX + "StudentInfos_add.html";
    }

    /**
     * 跳转到修改学生信息
     */
    @RequestMapping("/StudentInfos_update/{studentInfosId}")
    public String StudentInfosUpdate(@PathVariable Integer studentInfosId, ModelMap model) {

        if (ToolUtil.isEmpty(studentInfosId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        StudentInfos studentInfos = studentInfosService.selectStudentInfosByKey(studentInfosId);
        model.put("studentInfos", studentInfos);
        return PREFIX + "StudentInfos_edit.html";
    }

    /**
     * 跳转到回访学生信息
     */
    @RequestMapping("/StudentInfos_visit/{studentInfosId}")
    public String visit(@PathVariable Integer studentInfosId, ModelMap model) {

        if (ToolUtil.isEmpty(studentInfosId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        StudentInfos studentInfos = studentInfosService.selectStudentInfosByKey(studentInfosId);
        model.put("studentInfos", studentInfos);
        return PREFIX + "StudentInfos_visit.html";
    }

    /**
     * 获取学生信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(StudentInfos bean, PageReq params) {

        try {
            ShiroUser user = ShiroKit.getUser();
            bean.setCurrentOperator(user.getId());
            if (bean.getNextVisitDateBak() != null) {
                bean.setNextVisitDate(bean.getNextVisitDateBak());
            }
            List<StudentInfos> studentInfos = studentInfosService.selectStudentsByUserId(bean, params);
            return packForBT(studentInfos);
        } catch (Exception e) {
            Log4jUtil.error(e, "查询学生信息错误");
        }
        return null;
    }

    /**
     * 新增学生信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(StudentInfos bean) {

        try {
            ShiroUser user = ShiroKit.getUser();
            bean.setOperator(user.getId());
            bean.setFlag("0");//是否分配
            int i = studentInfosService.addStudentInfos(bean);
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
     * 删除学生信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String studentInfosIds) {

        if (ToolUtil.isEmpty(studentInfosIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        int i = studentInfosService.deleteStudentInfos(studentInfosIds);
        if (i > 0) {
            return SUCCESS_TIP;
        } else {
            return new ErrorTip(0, "删除失败");
        }
    }


    /**
     * 修改学生信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid StudentInfos bean, BindingResult result) {

        try {
            if (result.hasErrors()) {
                throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
            }
            int i = studentInfosService.updateStudentInfos(bean);
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
     * 修改学生信息
     */
    @RequestMapping(value = "/updateStudentAndVisit")
    @ResponseBody
    public Object update(@Valid StudentInfos bean, String description, BindingResult result) {

        try {
            if (result.hasErrors()) {
                throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
            }
            ShiroUser user = ShiroKit.getUser();
            VisitorRecord visitorRecord = new VisitorRecord();
            visitorRecord.setOperator(user.getId());
            visitorRecord.setStudentId(bean.getId());
            visitorRecord.setDescription(description);
            int i = studentInfosService.updateStudentAndVisit(bean, visitorRecord);
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
     * 学生信息详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }


    /**
     * 导入excel
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/importExcel")
    @ResponseBody
    public Object importExcel(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {

        if (multipartFile == null) {
            return new ErrorTip(BizExceptionEnum.REQUEST_NULL.getCode(), "请选择上传文件");
        }
        String fileName = multipartFile.getOriginalFilename();//原文件名
        System.out.println("文件名称:" + fileName);
        try {
            List<StudentInfos> studentInfoses = ExcelUtils.getInstance().readExcel2Objects(multipartFile.getInputStream(), StudentInfos.class);
            int i = studentInfosService.addBathStudents(studentInfoses);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(BizExceptionEnum.REQUEST_NULL.getCode(), "上传excel失败");
            }

        } catch (Exception e) {
            Log4jUtil.error(e, "上传excel错误");
            return new ErrorTip(BizExceptionEnum.REQUEST_NULL.getCode(), "上传excel失败,请联系管理员");
        }
    }


    /**
     * 分量
     *
     * @param userId
     * @param studentInfosIds
     * @return
     */
    @RequestMapping(value = "/share")
    @ResponseBody
    public Object share(String userId, String studentInfosIds) {

        try {
            if (!StringUtils.isNotBlank(userId) || !StringUtils.isNotBlank(studentInfosIds)) {
                return new ErrorTip(0, "用户id或学生id为空");
            }
            int i = studentInfosService.updateStudentInfos(userId, studentInfosIds);
            if (i > 0) {
                return super.SUCCESS_TIP;
            } else {
                return new ErrorTip(0, "操作失败");
            }
        } catch (Exception e) {
            Log4jUtil.error(e, "分量错误");
            return ErrorTip.error();
        }
    }

    @RequestMapping(value = "/visitCount")
    @ResponseBody
    public Object visitCount() {

        try {
            int[] array = null;
            String[] nameArry = null;
            List<Map<String, Object>> resultMap = studentInfosService.selectCountVisit();
            array = new int[resultMap.size()];
            nameArry = new String[resultMap.size()];

            for (int i = 0; i < resultMap.size(); i++) {
                array[i] = ((BigDecimal)resultMap.get(i).get("num")).intValue();
                nameArry[i] = (String) resultMap.get(i).get("name");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("nums", array);
            map.put("names", nameArry);
            return new SuccessTip(map);
        } catch (Exception e) {
            Log4jUtil.error(e, "查询上门错误");
            return ErrorTip.error();
        }
    }
}
