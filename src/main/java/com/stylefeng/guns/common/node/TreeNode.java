package com.stylefeng.guns.common.node;

import java.util.List;

/**
 * @author gejinbiao@ucfgroup.com
 * @Title
 * @Description:
 * @Company: ucfgroup.com
 * @Created 2018-08-29
 */
public class TreeNode {

    // id
    private String id;
    // 编码
    private String code;
    // 父id
    private String pid;
    // 对应code
    private String num;
    // 名称
    private String name;
    // 子菜单
    private List<TreeNode> childMenus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<TreeNode> childMenus) {
        this.childMenus = childMenus;
    }
}
