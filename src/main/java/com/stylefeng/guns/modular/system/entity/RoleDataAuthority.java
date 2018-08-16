package com.stylefeng.guns.modular.system.entity;

import java.io.Serializable;
import javax.persistence.Id;

/**
 * 角色数据权限实体类
 *
 * @author gjb
 * @Date 2018-07-06 16:44:36
 */
public class RoleDataAuthority implements Serializable{
    	// 主键	@Id	private Integer id;	// 角色id	private Integer roleId;	// 数据权限	private Integer dataAuthority;	// 	private java.util.Date createTime;	// 	private java.util.Date updateTime;	// 	private Integer opreator;		/**	 *	 * @return the id	 */	public Integer getId() {	    return this.id;}	/**	 *	 * @param id the id to set	 */	public void setId(Integer id) {	    this.id=id;	}	/**	 *	 * @return the roleId	 */	public Integer getRoleId() {	    return this.roleId;}	/**	 *	 * @param roleId the roleId to set	 */	public void setRoleId(Integer roleId) {	    this.roleId=roleId;	}	/**	 *	 * @return the dataAuthority	 */	public Integer getDataAuthority() {	    return this.dataAuthority;}	/**	 *	 * @param dataAuthority the dataAuthority to set	 */	public void setDataAuthority(Integer dataAuthority) {	    this.dataAuthority=dataAuthority;	}	/**	 *	 * @return the createTime	 */	public java.util.Date getCreateTime() {	    return this.createTime;}	/**	 *	 * @param createTime the createTime to set	 */	public void setCreateTime(java.util.Date createTime) {	    this.createTime=createTime;	}	/**	 *	 * @return the updateTime	 */	public java.util.Date getUpdateTime() {	    return this.updateTime;}	/**	 *	 * @param updateTime the updateTime to set	 */	public void setUpdateTime(java.util.Date updateTime) {	    this.updateTime=updateTime;	}	/**	 *	 * @return the opreator	 */	public Integer getOpreator() {	    return this.opreator;}	/**	 *	 * @param opreator the opreator to set	 */	public void setOpreator(Integer opreator) {	    this.opreator=opreator;	}
}
