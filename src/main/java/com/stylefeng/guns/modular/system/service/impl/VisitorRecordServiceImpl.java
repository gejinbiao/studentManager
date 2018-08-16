package com.stylefeng.guns.modular.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.common.page.PageReq;
import com.stylefeng.guns.modular.system.dao.VisitorRecordDao;
import com.stylefeng.guns.modular.system.entity.VisitorRecord;
import com.stylefeng.guns.modular.system.service.VisitorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 回访记录Service接口实现
 *
 * @author gjb
 * @Date 2018-07-17 18:22:24
 */
@Service
public class VisitorRecordServiceImpl implements VisitorRecordService {

    @Autowired
    private VisitorRecordDao visitorRecordDao;

    public List<VisitorRecord> selectByExample(VisitorRecord bean, PageReq params) {
        PageHelper.offsetPage(params.getOffset(), params.getLimit());
        Example example = new Example(VisitorRecord.class);
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
        List<VisitorRecord> visitorRecords = visitorRecordDao.selectByExample(example);
        return visitorRecords;
    }

    public int addVisitorRecord(VisitorRecord bean) {
        int count = visitorRecordDao.insertSelective(bean);
        return count;
    }


    public int updateVisitorRecord(VisitorRecord bean) {
        int count = visitorRecordDao.updateByPrimaryKeySelective(bean);
        return count;
    }


    public VisitorRecord selectVisitorRecordByKey(Integer visitorRecordId) {
        return visitorRecordDao.selectByPrimaryKey(visitorRecordId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteVisitorRecord(String visitorRecordIds) {
        String[] split = visitorRecordIds.split(",");
        Integer count = 0;
        for (String visitorRecordId : split) {
            int nu = visitorRecordDao.deleteByPrimaryKey(visitorRecordId);
            count += nu;
        }
        return count;
    }

    /**
     * 根据学生id获取列表
     *
     * @param bean
     * @return
     */
    public List<VisitorRecord> selectVisitByStudentId(VisitorRecord bean, PageReq pageReq) {
        PageHelper.offsetPage(pageReq.getOffset(), pageReq.getLimit());
        List<VisitorRecord> visitorRecords = visitorRecordDao.selectVisitByStudentId(String.valueOf(bean.getStudentId()));
        return visitorRecords;
    }
}
