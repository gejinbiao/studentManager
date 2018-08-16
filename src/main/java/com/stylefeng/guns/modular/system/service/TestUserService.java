package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.entity.TestUser;
import com.stylefeng.guns.common.page.PageReq;
import java.util.List;

/**
 * 测试用户Service业务接口
 *
 * @author gjb
 * @Date 2018-07-04 10:23:27
 */
public interface TestUserService {

    /**
         * 获取测试用户列表
         *
         * @param bean
         * @param params
         * @return
         */
        List<TestUser> selectByExample(TestUser bean, PageReq params);

        /**
         * 新增测试用户
         *
         * @param bean
         * @return
         */
        int addTestUser(TestUser bean);

        /**
         * 修改测试用户
         *
         * @param bean
         * @return
         */
        int updateTestUser(TestUser bean);

        /**
         * 获取测试用户
         *
         * @param testUserId
         * @return
         */
        TestUser selectTestUserByKey(Integer testUserId);


        /**
         * 删除测试用户
         * @param testUserIds
         * @return
         */
        int deleteTestUser(String testUserIds);
}
