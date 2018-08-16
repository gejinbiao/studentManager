package com.stylefeng.guns.modular.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.modular.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjb
 * @Title
 * @Description:
 * @Created 2018-07-10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUsers(User user, PageReq pageReq) {

        user.setStatus(1);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        List<String> list = new ArrayList<>();
        list.add("admin");
        criteria.andNotIn("account", list);
        criteria.andEqualTo(user);
        PageHelper.offsetPage(pageReq.getOffset(), pageReq.getLimit());
        List<User> users = userMapper.selectByExample(example);
        return users;
    }
}