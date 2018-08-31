package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.Dict;

import java.util.List;

/**
 * 字典服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface IDictService {

    /**
     * 添加字典
     *
     * @author fengshuonan
     * @Date 2017/4/27 17:01
     */
    void addDict(String dictCode, String dictName, String dictValues);

    /**
     * 编辑字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:01
     */
    void editDict(Integer dictId,String dictCode, String dictName, String dicts);

    /**
     * 删除字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:39
     */
    void delteDict(Integer dictId);

    /**
     * 根据英文名称获取列表
     * @param dicCode
     * @return
     */
    List<Dict> selectDicByDicCode(String dicCode);


    /**
     * 获取树形列表
     * @return
     */
    List<Dict> selectTreeNode();



}
