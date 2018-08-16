package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.common.persistence.dao.CodeMapper;
import com.stylefeng.guns.common.persistence.model.CodeModel;
import com.stylefeng.guns.modular.system.service.ICodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjb
 * @Title
 * @Description:生成代码实现service
 * @Created 2018-06-22
 */
@Service
public class CodeServiceImpl implements ICodeService {


    @Autowired
    private CodeMapper codeMapper;

    @Override
    public List<CodeModel> getTableStructure(String tableName) {
        List<CodeModel> list = new ArrayList<>();
        List<CodeModel> tableStructures = codeMapper.getTableStructure(tableName);
        if (tableStructures != null && tableStructures.size() > 0) {
            for (CodeModel codeModel : tableStructures) {
                int length = 0;
                if (StringUtils.isNotBlank(codeModel.getDataLength())) {
                    length = Integer.valueOf(codeModel.getDataLength());
                }
                String javaType = this.getJavaType(codeModel.getDataType(), length);
                String jdbcType = this.getJdbcType(codeModel.getDataType(), length);
                codeModel.setDataType(javaType);
                codeModel.setJdbcType(jdbcType);
                codeModel.setAttrName(getFeildsNameTo(codeModel.getColumnName()));
                String attrName = codeModel.getAttrName();
                codeModel.setFirstUpperAttrName(attrName.substring(0, 1).toUpperCase()+attrName.substring(1, attrName.length()));
                list.add(codeModel);
            }
        }
        return list;
    }

    /**
     * 表字段名 转换驼峰命令（ _ 分割）
     *
     * @param feildName
     * @return
     */
    public String getFeildsNameTo(String feildName) {
        String[] split = feildName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String tempTableName = null;
                if (i == 0) {
                    tempTableName = split[i];
                } else {
                    tempTableName = split[i].substring(0, 1).toUpperCase()
                            + split[i].substring(1, split[i].length());
                }
                sb.append(tempTableName);
            }
            return sb.toString();
        } else {
            return feildName;
        }
    }


    public String getJdbcType(String type, int scale) {
        type = type.toLowerCase();
        if ("char".equals(type) || "varchar".equals(type)
                || "varchar2".equals(type) || "nvarchar2".equals(type)) {
            return "VARCHAR";
        } else if ("binary_double".equals(type)) {
            return "BINART_DOUBLE";
        } else if ("binary_float".equals(type)) {
            return "BINARY_FLOAT";
        } else if ("int".equals(type)) {
            return "INTEGER";
        } else if ("float".equals(type)) {
            return "Float";
        } else if ("bigint".equals(type)) {
            return "BIGINT";
        } else if ("number".equals(type) || "double".equals(type)) {
            return scale > 0 ? "DOUBLE" : "INTEGER";
        } else if ("date".equals(type) || "datetime".equals(type)) {
            return "DATE";
        } else if (type != null && type.startsWith("timestamp")) {
            return "TIMESTAMP";
        } else if (type != null && type.equals("clob")) {
            return "CLOB";
        } else if ("tinyint".equals(type) || "TINYINT".equals(type)) {//mysql特有
            return "BOOLEAN";
        }
        return null;
    }

    public String getJavaType(String type, int scale) {
        type = type.toLowerCase();
        if ("char".equals(type) || "varchar".equals(type)
                || "varchar2".equals(type) || "nvarchar2".equals(type)) {
            return "String";
        } else if ("binary_double".equals(type)) {
            return "Double";
        } else if ("binary_float".equals(type)) {
            return "Float";
        } else if ("int".equals(type)) {
            return "Integer";
        } else if ("float".equals(type)) {
            return "Float";
        } else if ("bigint".equals(type)) {
            return "Long";
        } else if ("number".equals(type) || "double".equals(type)) {
            return scale > 0 ? "Double" : "Integer";
        } else if ((type != null && type.startsWith("timestamp"))
                || "date".equalsIgnoreCase(type)
                || "datetime".equalsIgnoreCase(type)) {
            return "java.util.Date";
        } else if ("clob".equals(type)) {
            return "String";
        } else if ("tinyint".equals(type) || "TINYINT".equals(type)) {//mysql特有
            return "Boolean";
        }
        return null;
    }
}