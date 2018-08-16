package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.entity.VisitorRecord;
import com.stylefeng.guns.common.page.PageReq;
import java.util.List;

/**
 * 回访记录Service业务接口
 *
 * @author gjb
 * @Date 2018-07-17 18:22:24
 */
public interface VisitorRecordService {

    /**
         * 获取回访记录列表
         *
         * @param bean
         * @param params
         * @return
         */
        List<VisitorRecord> selectByExample(VisitorRecord bean, PageReq params);

        /**
         * 新增回访记录
         *
         * @param bean
         * @return
         */
        int addVisitorRecord(VisitorRecord bean);

        /**
         * 修改回访记录
         *
         * @param bean
         * @return
         */
        int updateVisitorRecord(VisitorRecord bean);

        /**
         * 获取回访记录
         *
         * @param visitorRecordId
         * @return
         */
        VisitorRecord selectVisitorRecordByKey(Integer visitorRecordId);


        /**
         * 删除回访记录
         * @param visitorRecordIds
         * @return
         */
        int deleteVisitorRecord(String visitorRecordIds);


    /**
     * 根据学生id获取列表
     * @param bean
     * @return
     */
    List<VisitorRecord> selectVisitByStudentId(VisitorRecord bean, PageReq params);
}
