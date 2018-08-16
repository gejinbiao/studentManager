package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.CodeModel;

import java.util.List;

/**
 * @author gjb
 * @Title
 * @Description:
 * @Created 2018-06-22
 */
public interface ICodeService {

    /**
     * 根据表明获取表结构
     * @param tableName
     * @return
     */
    List<CodeModel> getTableStructure(String tableName);
}