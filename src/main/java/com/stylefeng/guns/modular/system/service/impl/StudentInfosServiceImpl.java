package com.stylefeng.guns.modular.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.dao.StudentInfosDao;
import com.stylefeng.guns.modular.system.dao.VisitorRecordDao;
import com.stylefeng.guns.modular.system.entity.StudentInfos;
import com.stylefeng.guns.modular.system.entity.VisitorRecord;
import com.stylefeng.guns.modular.system.service.StudentInfosService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 学生信息Service接口实现
 *
 * @author gjb
 * @Date 2018-07-04 14:41:16
 */
@Service
public class StudentInfosServiceImpl implements StudentInfosService {

    @Autowired
    private StudentInfosDao studentInfosDao;

    @Autowired
    private VisitorRecordDao visitorRecordDao;

    public List<StudentInfos> selectByExample(StudentInfos bean, PageReq params) {

        Example example = new Example(StudentInfos.class);
        example.orderBy("createTime").desc();
        Example.Criteria criteria = example.createCriteria();
        if (bean.getCreateTime() != null) {
            criteria.andCondition(" DATE_FORMAT(create_time,'%Y-%m-%d')=", new SimpleDateFormat("yyyy-MM-dd").format(bean.getCreateTime()));
            bean.setCreateTime(null);
        }
        if (bean.getUpdateTime() != null) {
            criteria.andCondition(" DATE_FORMAT(update_time,'%Y-%m-%d')=", new SimpleDateFormat("yyyy-MM-dd").format(bean.getUpdateTime()));
            bean.setUpdateTime(null);
        }
        criteria.andEqualTo(bean);
        PageHelper.offsetPage(params.getOffset(), params.getLimit());
        List<StudentInfos> studentInfoss = studentInfosDao.selectByExample(example);
        return studentInfoss;
    }

    public int addStudentInfos(StudentInfos bean) {
        int count = studentInfosDao.insertSelective(bean);
        return count;
    }


    public int updateStudentInfos(StudentInfos bean) {
        int count = studentInfosDao.updateByPrimaryKeySelective(bean);
        return count;
    }

    public int updateStudentInfos(String userId, String studentInfosIds) {
        String[] split = studentInfosIds.split(",");
        List<String> studentInfosList = Arrays.asList(split);
        Example example = new Example(StudentInfos.class);
        StudentInfos studentInfos = new StudentInfos();
        studentInfos.setCurrentOperator(Integer.valueOf(userId));
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", studentInfosList);
        int count = studentInfosDao.updateByExampleSelective(studentInfos, example);
        return count;
    }

    /**
     * 修改学生信息并增加回访记录
     *
     * @param bean
     * @param visitorRecord
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateStudentAndVisit(StudentInfos bean, VisitorRecord visitorRecord) {

        bean.setUpdateTime(new Date());
        int i = studentInfosDao.updateByPrimaryKeySelective(bean);
        if (i > 0) {
            visitorRecord.setUpdateTime(new Date());
            if(StringUtils.isNotBlank(visitorRecord.getDescription())){
                int insert = visitorRecordDao.insertSelective(visitorRecord);
                if (insert == 0) {
                    throw new BussinessException("保存回访记录失败");
                }
            }

        }

        return i;
    }


    public StudentInfos selectStudentInfosByKey(Integer studentInfosId) {
        return studentInfosDao.selectByPrimaryKey(studentInfosId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteStudentInfos(String studentInfosIds) {
        String[] split = studentInfosIds.split(",");
        Integer count = 0;
        for (String studentInfosId : split) {
            int nu = studentInfosDao.deleteByPrimaryKey(studentInfosId);
            count += nu;
        }
        return count;
    }

    public int addBathStudents(List<StudentInfos> studentInfoses) {

        List<StudentInfos> list = new ArrayList<>();
        //获取当前登录用户
        Integer userId = ShiroKit.getUser().getId();
        int count = 0;
        if (studentInfoses != null && studentInfoses.size() > 0) {
            for (int i = 0; i < studentInfoses.size(); i++) {
                StudentInfos studentInfos = studentInfoses.get(i);
                studentInfos.setOperator(userId);
                list.add(studentInfoses.get(i));
                if ((i > 0 && i % 1000 == 0) || i == studentInfoses.size() - 1) {
                    int no = studentInfosDao.insertList(list);
                    count += no;
                    //当超过1000后清楚list
                    list.clear();
                }
            }
        }
        return count;
    }

    /**
     * 根据userId 权限进行查询
     *
     * @param studentInfos
     * @param pageReq
     * @return
     */
    public List<StudentInfos> selectStudentsByUserId(StudentInfos studentInfos, PageReq pageReq) {
        PageHelper.offsetPage(pageReq.getOffset(), pageReq.getLimit());

        return studentInfosDao.selectStudentsByUserId(studentInfos);
    }

}
