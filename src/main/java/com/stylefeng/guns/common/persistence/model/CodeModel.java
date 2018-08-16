package com.stylefeng.guns.common.persistence.model;

/**
 * @author gjb
 * @Title
 * @Description:生成代码model
 * @Created 2018-06-22
 */
public class CodeModel {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 字段大小
     */
    private String dataLength;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 类型
     */
    private String jdbcType;

    private String firstUpperAttrName;//首字母大写，原因：由于velocity没有大小写转换的函数，所以只能后台组织

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getFirstUpperAttrName() {
        return firstUpperAttrName;
    }

    public void setFirstUpperAttrName(String firstUpperAttrName) {
        this.firstUpperAttrName = firstUpperAttrName;
    }
}