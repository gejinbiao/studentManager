package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.DictMapper;
import com.stylefeng.guns.common.persistence.model.Dict;
import com.stylefeng.guns.modular.system.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.stylefeng.guns.common.constant.factory.MutiStrFactory.*;

@Service
@Transactional
public class DictServiceImpl implements IDictService {

    @Resource
    DictMapper dictMapper;

    @Override
    public void addDict(String dictCode, String dictName, String dictValues) {
        //判断有没有该字典
        Example example = new Example(Dict.class);
        example.createCriteria().andEqualTo("name", dictName).andEqualTo("pid", 0);
        List<Dict> dicts = dictMapper.selectByExample(example);
        if (dicts != null && dicts.size() > 0) {
            throw new BussinessException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = parseKeyValue(dictValues);

        //添加字典
        Dict dict = new Dict();
        dict.setDicCode(dictCode);
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MUTI_STR_KEY);
            String name = item.get(MUTI_STR_VALUE);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            } catch (NumberFormatException e) {
                throw new BussinessException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    public void editDict(Integer dictId, String dictCode, String dictName, String dicts) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictCode, dictName, dicts);
    }

    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        Dict query = new Dict();
        query.setPid(dictId);
        dictMapper.delete(query);
        //删除这个词典
        dictMapper.deleteByPrimaryKey(dictId);
    }

    /**
     * 根据英文名称获取列表
     *
     * @param dicCode
     * @return
     */
    public List<Dict> selectDicByDicCode(String dicCode) {
        return dictMapper.selectDicByDicCode(dicCode);
    }


    /**
     * 获取树形列表
     *
     * @return
     */
    public List<Dict> selectTreeNode() {
        List<Dict> dicts = dictMapper.selectAll();
        // 最后的结果
        List<Dict> dictList = new ArrayList<Dict>();
        // 先找到所有的一级菜单
        for (int i = 0; i < dicts.size(); i++) {
            // 一级菜单parentId = 0
            if (dicts.get(i).getPid() == 0) {
                dictList.add(dicts.get(i));
                dicts.remove(i);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (Dict dict : dictList) {
            dict.setDictList(getChild(dict.getId(), dicts));
        }
        return dictList;
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param dicts 要查找的列表
     * @return
     */
    private List<Dict> getChild(Integer id, List<Dict> dicts) {
        // 子菜单
        List<Dict> childList = new ArrayList<>();
        for (Dict menu : dicts) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getPid() != 0) {
                if (menu.getPid() == id) {
                    childList.add(menu);
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
