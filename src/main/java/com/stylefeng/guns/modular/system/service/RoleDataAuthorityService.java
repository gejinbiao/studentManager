package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.entity.RoleDataAuthority;
import com.stylefeng.guns.common.page.PageReq;

import java.util.List;

/**
 * 角色数据权限Service业务接口
 *
 * @author gjb
 * @Date 2018-07-06 16:44:36
 */
public interface RoleDataAuthorityService {

    /**
     * 获取角色数据权限列表
     *
     * @param bean
     * @param params
     * @return
     */
    List<RoleDataAuthority> selectByExample(RoleDataAuthority bean, PageReq params);

    /**
     * 新增角色数据权限
     *
     * @param bean
     * @return
     */
    int addRoleDataAuthority(RoleDataAuthority bean);

    /**
     * 修改角色数据权限
     *
     * @param bean
     * @return
     */
    int updateRoleDataAuthority(RoleDataAuthority bean);

    /**
     * 获取角色数据权限
     *
     * @param roleDataAuthorityId
     * @return
     */
    RoleDataAuthority selectRoleDataAuthorityByKey(Integer roleDataAuthorityId);


    /**
     * 删除角色数据权限
     *
     * @param roleDataAuthorityIds
     * @return
     */
    int deleteRoleDataAuthority(String roleDataAuthorityIds);

    /**
     * 查询角色数据明细
     * @param roleDataAuthority
     * @return
     */
    RoleDataAuthority selectOneByExample(RoleDataAuthority roleDataAuthority);
}
