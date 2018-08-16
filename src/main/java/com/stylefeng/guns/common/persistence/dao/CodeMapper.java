package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.CodeModel;

import java.util.List;

/**
 * @author gjb
 * @Title
 * @Description:生成代码dao层
 * @Created 2018-06-22
 */
public interface CodeMapper {


    /**
     * 根据表明获取表结构
     * @param tableName
     * @return
     */
    List<CodeModel> getTableStructure(String tableName);
}