package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.core.template.config.ContextConfig;
import com.stylefeng.guns.core.template.engine.SimpleTemplateEngine;
import com.stylefeng.guns.core.template.engine.base.GunsTemplateEngine;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.service.ICodeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @Date 2017-05-23 18:52:34
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {


    @Autowired
    private ICodeService codeService;

    private String PREFIX = "/system/code/";

    /**
     * 跳转到代码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "code.html";
    }

    /**
     * 代码生成
     */
    @ApiOperation("生成代码")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object add(@ApiParam(value = "模块名称", required = true) @RequestParam String moduleName,
                      @RequestParam String bizChName,
                      @RequestParam String bizEnName,
                      @RequestParam String path) {
        if (ToolUtil.isOneEmpty(bizChName, bizEnName)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName(bizChName);
        contextConfig.setModuleName(moduleName);
        //标结构list
        contextConfig.setCodeModels(codeService.getTableStructure(bizEnName));
        //将表明转化为类名 每_首字母大写
        String className = getTablesNameToClassName(bizEnName);
        contextConfig.setBizEnName(getTablesNameToClassName(className));
        //首字母大写
        contextConfig.setLowerName(className.substring(0,1).toLowerCase() + className.substring(1));
        if (ToolUtil.isNotEmpty(path)) {
            contextConfig.setProjectPath(path);
        }

        GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
        gunsTemplateEngine.setContextConfig(contextConfig);
        gunsTemplateEngine.start();

        return super.SUCCESS_TIP;
    }

    /**
     * <br>
     * <b>功能：</b>表名转换成类名 每_首字母大写<br>
     * <b>作者：</b>肖财高<br>
     * <b>日期：</b> 2011-12-21 <br>
     *
     * @param tableName
     * @return
     */
    public String getTablesNameToClassName(String tableName) {
        String[] split = tableName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String tempTableName = split[i].substring(0, 1).toUpperCase()
                        + split[i].substring(1, split[i].length())
                        .toLowerCase();
                sb.append(tempTableName);
            }
            return sb.toString();
        } else {
            String tempTables = split[0].substring(0, 1).toUpperCase()
                    + split[0].substring(1, split[0].length());
            return tempTables;
        }
    }
}
