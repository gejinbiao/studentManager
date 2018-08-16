package com.stylefeng.guns.core.template.config;


import java.util.ArrayList;
import java.util.List;

/**
 * 控制器模板生成的配置
 *
 * @author fengshuonan
 * @date 2017-05-07 22:12
 */
public class ControllerConfig {

    private ContextConfig contextConfig;

    private String controllerPathTemplate;
    private String packageName;//包名称
    private List<String> imports;//所引入的包

    public void init() {
        ArrayList<String> imports = new ArrayList<>();
        imports.add("com.stylefeng.guns.common.constant.tips.ErrorTip");
        imports.add("com.stylefeng.guns.common.controller.BaseController");
        imports.add("com.stylefeng.guns.common.exception.BizExceptionEnum");
        imports.add("com.stylefeng.guns.common.exception.BussinessException");
        imports.add("com.stylefeng.guns.common.log.Log4jUtil");
        imports.add("com.stylefeng.guns.common.page.PageReq");
        imports.add("com.stylefeng.guns.core.shiro.ShiroKit");
        imports.add("com.stylefeng.guns.core.shiro.ShiroUser");
        imports.add("com.stylefeng.guns.core.util.ToolUtil");
        imports.add("com.stylefeng.guns.modular.system.entity." + contextConfig.getBizEnBigName());
        imports.add("com.stylefeng.guns.modular.system.service." + contextConfig.getBizEnBigName() + "Service");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.stereotype.Controller");
        imports.add("org.springframework.ui.ModelMap");
        imports.add("org.springframework.validation.BindingResult");
        imports.add("org.springframework.web.bind.annotation.PathVariable");
        imports.add("org.springframework.web.bind.annotation.RequestMapping");
        imports.add("org.springframework.web.bind.annotation.RequestParam");
        imports.add("org.springframework.web.bind.annotation.ResponseBody");

        imports.add("javax.validation.Valid");
        imports.add("java.util.List");
        this.imports = imports;
        this.packageName = "com.stylefeng.guns.modular." + contextConfig.getModuleName() + ".controller";
        this.controllerPathTemplate = "\\src\\main\\java\\com\\stylefeng\\guns\\modular\\" + contextConfig.getModuleName() + "\\controller\\{}Controller.java";
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getControllerPathTemplate() {
        return controllerPathTemplate;
    }

    public void setControllerPathTemplate(String controllerPathTemplate) {
        this.controllerPathTemplate = controllerPathTemplate;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }
}
