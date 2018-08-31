package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.entity.StudentInfos;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.modular.system.entity.VisitorRecord;

import java.util.List;
import java.util.Map;

/**
 * 学生信息Service业务接口
 *
 * @author gjb
 * @Date 2018-07-04 14:38:58
 */
public interface StudentInfosService {

    /**
     * 获取学生信息列表
     *
     * @param bean
     * @param params
     * @return
     */
    List<StudentInfos> selectByExample(StudentInfos bean, PageReq params);

    /**
     * 新增学生信息
     *
     * @param bean
     * @return
     */
    int addStudentInfos(StudentInfos bean);

    /**
     * 修改学生信息
     *
     * @param bean
     * @return
     */
    int updateStudentInfos(StudentInfos bean);

    /**
     * 修改学生信息并增加回访记录
     * @param bean
     * @param visitorRecord
     * @return
     */
    int updateStudentAndVisit(StudentInfos bean, VisitorRecord visitorRecord);

    /**
     * 批量修改学生细腻
     * @param userId
     * @param studentInfosIds
     * @return
     */
    int updateStudentInfos(String userId, String studentInfosIds);

    /**
     * 获取学生信息
     *
     * @param studentInfosId
     * @return
     */
    StudentInfos selectStudentInfosByKey(Integer studentInfosId);


    /**
     * 删除学生信息
     *
     * @param studentInfosIds
     * @return
     */
    int deleteStudentInfos(String studentInfosIds);

    /**
     * 批量插入
     * @param studentInfoses
     * @return
     */
    int addBathStudents(List<StudentInfos> studentInfoses);

    /**
     * 根据userId 权限进行查询
     * @param studentInfos
     * @param params
     * @return
     */
    List<StudentInfos> selectStudentsByUserId(StudentInfos studentInfos, PageReq params);

    /**
     * 每月上门每人
     * @return
     */
    List<Map<String, Object>> selectCountVisit();
}
