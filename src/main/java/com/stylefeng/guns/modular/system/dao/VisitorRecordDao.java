package com.stylefeng.guns.modular.system.dao;

import tk.mybatis.mapper.common.Mapper;
import com.stylefeng.guns.modular.system.entity.VisitorRecord;

import java.util.List;

/**
 * 回访记录Dao
 *
 * @author fengshuonan
 * @Date 2018-07-17 18:22:24
 */
public interface VisitorRecordDao extends Mapper<VisitorRecord> {


    /**
     * 根据学生id获取列表
     * @param studentId
     * @return
     */
    List<VisitorRecord> selectVisitByStudentId(String studentId);
}
