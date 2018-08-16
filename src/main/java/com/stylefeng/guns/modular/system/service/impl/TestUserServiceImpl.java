package com.stylefeng.guns.modular.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.modular.system.dao.TestUserDao;
import com.stylefeng.guns.modular.system.entity.TestUser;
import com.stylefeng.guns.modular.system.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 用户测试Service接口实现
 *
 * @author gjb
 * @Date 2018-07-04 11:28:39
 */
@Service
public class TestUserServiceImpl implements TestUserService {

    @Autowired
    private TestUserDao testUserDao;

    public List<TestUser> selectByExample(TestUser bean, PageReq params) {
        PageHelper.offsetPage(params.getOffset(), params.getLimit());
        Example example = new Example(TestUser.class);
        example.orderBy("createTime").desc();
        Example.Criteria criteria = example.createCriteria();
        if (bean.getCreateTime() != null) {
            criteria.andCondition(" DATE_FORMAT(create_time,'%Y-%m-%d')=", new SimpleDateFormat("yyyy-MM-dd").format(bean.getCreateTime()));
            bean.setCreateTime(null);
        }
        if(bean.getUpdateTime() != null){
            criteria.andCondition(" DATE_FORMAT(update_time,'%Y-%m-%d')=", new SimpleDateFormat("yyyy-MM-dd").format(bean.getUpdateTime()));
            bean.setUpdateTime(null);
        }
        criteria.andEqualTo(bean);
        List<TestUser> testUsers = testUserDao.selectByExample(example);
        return testUsers;
    }

    public int addTestUser(TestUser bean) {
        int count = testUserDao.insertSelective(bean);
        return count;
    }


    public int updateTestUser(TestUser bean) {
        int count = testUserDao.updateByPrimaryKeySelective(bean);
        return count;
    }


    public TestUser selectTestUserByKey(Integer testUserId) {
        return testUserDao.selectByPrimaryKey(testUserId);
    }


    @Transactional(rollbackFor = Exception.class)
    public int deleteTestUser(String testUserIds) {
        String[] split = testUserIds.split(",");
        Integer count = 0;
        for (String testUserId : split) {
            int nu = testUserDao.deleteByPrimaryKey(testUserId);
            count += nu;
        }
        return count;
    }

}
