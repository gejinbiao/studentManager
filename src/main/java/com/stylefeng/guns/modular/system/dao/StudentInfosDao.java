package com.stylefeng.guns.modular.system.dao;

import tk.mybatis.mapper.common.Mapper;
import com.stylefeng.guns.modular.system.entity.StudentInfos;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;
import java.util.Map;

/**
 * 学生信息Dao
 *
 * @author fengshuonan
 * @Date 2018-07-04 14:38:58
 */
public interface StudentInfosDao extends Mapper<StudentInfos>, InsertListMapper<StudentInfos> {

    /**
     * 根据userId 权限进行查询
     *
     * @param studentInfos
     * @return
     */
    List<StudentInfos> selectStudentsByUserId(StudentInfos studentInfos);


    /**
     * 当月上门每人
     * @return
     */
    List<Map<String, Object>> selectCountVisit();

}
