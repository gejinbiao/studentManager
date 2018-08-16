package com.stylefeng.guns.core.template.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Service模板生成的配置
 *
 * @author fengshuonan
 * @date 2017-05-07 22:12
 */
public class ServiceConfig {

    private ContextConfig contextConfig;

    private String servicePathTemplate;
    private String serviceImplPathTemplate;

    private String packageName;

    private List<String> serviceImplImports;

    private List<String> serviceImports;

    public void init() {
        ArrayList<String> imports = new ArrayList<>();
        imports.add("com.stylefeng.guns.modular.system.entity." + contextConfig.getBizEnBigName());
        imports.add("com.stylefeng.guns.common.page.PageReq");
        imports.add("java.util.List");
        this.serviceImports = imports;
        this.serviceImplImports = new ArrayList<>();
        serviceImplImports.add("com.github.pagehelper.PageHelper");
        serviceImplImports.add("com.stylefeng.guns.common.page.PageReq");
        serviceImplImports.add("com.stylefeng.guns.modular.system.dao." + contextConfig.getBizEnBigName() + "Dao");
        serviceImplImports.add("com.stylefeng.guns.modular.system.entity." + contextConfig.getBizEnBigName());
        serviceImplImports.add("com.stylefeng.guns.modular.system.service." + contextConfig.getBizEnBigName() + "Service");
        serviceImplImports.add("org.springframework.beans.factory.annotation.Autowired");
        serviceImplImports.add("org.springframework.stereotype.Service");
        serviceImplImports.add("org.springframework.transaction.annotation.Transactional");
        serviceImplImports.add("tk.mybatis.mapper.entity.Example");
        serviceImplImports.add("java.text.SimpleDateFormat");
        serviceImplImports.add("java.util.List");

        this.servicePathTemplate = "\\src\\main\\java\\com\\stylefeng\\guns\\modular\\" + contextConfig.getModuleName() + "\\service\\{}Service.java";
        this.serviceImplPathTemplate = "\\src\\main\\java\\com\\stylefeng\\guns\\modular\\" + contextConfig.getModuleName() + "\\service\\impl\\{}ServiceImpl.java";
        this.packageName = "com.stylefeng.guns.modular." + contextConfig.getModuleName() + ".service";
    }



    public String getServicePathTemplate() {
        return servicePathTemplate;
    }

    public void setServicePathTemplate(String servicePathTemplate) {
        this.servicePathTemplate = servicePathTemplate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getServiceImplPathTemplate() {
        return serviceImplPathTemplate;
    }

    public void setServiceImplPathTemplate(String serviceImplPathTemplate) {
        this.serviceImplPathTemplate = serviceImplPathTemplate;
    }

    public List<String> getServiceImplImports() {
        return serviceImplImports;
    }

    public void setServiceImplImports(List<String> serviceImplImports) {
        this.serviceImplImports = serviceImplImports;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }

    public List<String> getServiceImports() {
        return serviceImports;
    }

    public void setServiceImports(List<String> serviceImports) {
        this.serviceImports = serviceImports;
    }
}
