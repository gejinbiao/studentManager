package com.stylefeng.guns.core.template.config;

import com.stylefeng.guns.common.persistence.model.CodeModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gjb
 * @Title
 * @Description:
 * @Created 2018-06-22
 */
public class EntityConfig {

    private ContextConfig contextConfig;

    private String modelPathTemplate;
    private String packageName;//包名称
    private List<String> imports;//所引入的包
    private String fields; //页面数据

    public void init() {
        ArrayList<String> imports = new ArrayList<>();
        imports.add("java.io.Serializable");
        imports.add("javax.persistence.Id");
        this.imports = imports;
        this.packageName = "com.stylefeng.guns.modular." + contextConfig.getModuleName() + ".entity";
        this.modelPathTemplate = "\\src\\main\\java\\com\\stylefeng\\guns\\modular\\" + contextConfig.getModuleName() + "\\entity\\{}.java";
        this.fields = getBeanFeilds(contextConfig.getCodeModels());
    }

    /**
     * 生成实体Bean 的属性和set,get方法
     *
     * @param dataList
     * @return
     * @throws SQLException
     */
    public String getBeanFeilds(List<CodeModel> dataList) {
        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        for (CodeModel d : dataList) {
            String name = d.getAttrName();
            String type = d.getDataType();
            String comment = d.getColumnComment();
            // type=this.getType(type);
            String maxChar = name.substring(0, 1).toUpperCase();
            str.append("\r\t").append("// ").append(comment);
            if ("id".equals(name)) {
                str.append("\r\t").append("@Id");
            }
            str.append("\r\t").append("private ").append(type + " ")
                    .append(name).append(";");
            String method = maxChar + name.substring(1, name.length());
            getset.append("\r\t").append("/**\r\t *\r\t * @return the ")
                    .append(name).append("\r\t */");
            getset.append("\r\t").append("public ").append(type + " ")
                    .append("get" + method + "() {\r\t");
            getset.append("    return this.").append(name).append(";}\r\t");
            getset.append("/**\r\t *\r\t * @param ").append(name)
                    .append(" the ").append(name).append(" to set")
                    .append("\r\t */");

            getset.append("\r\t")
                    .append("public void ")
                    .append("set" + method + "(" + type + " " + name
                            + ") {\r\t");
            getset.append("    this." + name + "=").append(name)
                    .append(";\r\t}");

        }
        /*argv = str.append("\r\t").toString();
        method = getset.toString();*/
        return str.append("\r\t").toString() + getset.toString();
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

    public String getModelPathTemplate() {
        return modelPathTemplate;
    }

    public void setModelPathTemplate(String modelPathTemplate) {
        this.modelPathTemplate = modelPathTemplate;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}