package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.common.persistence.model.User;

import java.util.List;

/**
 * @author gjb
 * @Title
 * @Description:
 * @Created 2018-07-10
 */
public interface UserService {

    List<User> selectUsers(User user, PageReq pageReq);
}