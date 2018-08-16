package com.stylefeng.guns.modular.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.modular.system.dao.RoleDataAuthorityDao;
import com.stylefeng.guns.modular.system.entity.RoleDataAuthority;
import com.stylefeng.guns.modular.system.service.RoleDataAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 角色数据权限Service接口实现
 *
 * @author gjb
 * @Date 2018-07-06 16:44:36
 */
@Service
public class RoleDataAuthorityServiceImpl implements RoleDataAuthorityService {

    @Autowired
    private RoleDataAuthorityDao roleDataAuthorityDao;

    public List<RoleDataAuthority> selectByExample(RoleDataAuthority bean, PageReq params) {
        PageHelper.offsetPage(params.getOffset(), params.getLimit());
        Example example = new Example(RoleDataAuthority.class);
        example.orderBy("createTime").desc();
        Example.Criteria criteria = example.createCriteria();
        if (bean.getCreateTime() != null) {
            criteria.andCondition(" DATE_FORMAT(create_time,'%Y-%m-%d')=", new SimpleDateFormat("yyyy-MM-dd").format(bean.getCreateTime()));
            bean.setCreateTime(null);
        }
        if (bean.getUpdateTime() != null) {
            criteria.andCondition(" DATE_FORMAT(update_time,'%Y-%m-%d')=", new SimpleDateFormat("yyyy-MM-dd").format(bean.getUpdateTime()));
            bean.setUpdateTime(null);
        }
        criteria.andEqualTo(bean);
        List<RoleDataAuthority> roleDataAuthoritys = roleDataAuthorityDao.selectByExample(example);
        return roleDataAuthoritys;
    }

    @Transactional(rollbackFor = Exception.class)
    public int addRoleDataAuthority(RoleDataAuthority bean) {
        RoleDataAuthority roleDataAuthority = new RoleDataAuthority();
        roleDataAuthority.setRoleId(bean.getRoleId());
        //删除当前角色对应的数据
        roleDataAuthorityDao.delete(roleDataAuthority);
        //添加当前
        int count = 0;
        if(bean.getDataAuthority() != null){
            count = roleDataAuthorityDao.insertSelective(bean);
        } else {
            count = 1;
        }
        return count;
    }


    public int updateRoleDataAuthority(RoleDataAuthority bean) {
        int count = roleDataAuthorityDao.updateByPrimaryKeySelective(bean);
        return count;
    }


    public RoleDataAuthority selectRoleDataAuthorityByKey(Integer roleDataAuthorityId) {
        return roleDataAuthorityDao.selectByPrimaryKey(roleDataAuthorityId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleDataAuthority(String roleDataAuthorityIds) {
        String[] split = roleDataAuthorityIds.split(",");
        Integer count = 0;
        for (String roleDataAuthorityId : split) {
            int nu = roleDataAuthorityDao.deleteByPrimaryKey(roleDataAuthorityId);
            count += nu;
        }
        return count;
    }

    public RoleDataAuthority selectOneByExample(RoleDataAuthority roleDataAuthority) {
        return roleDataAuthorityDao.selectOne(roleDataAuthority);
    }

}
