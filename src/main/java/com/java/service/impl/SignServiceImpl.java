package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.*;
import com.java.model.dto.SignDetail;
import com.java.model.entity.*;
import com.java.service.intf.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignServiceImpl implements SignService {
    private SignDao signDao;
    private SignStudentDao signStudentDao;
    private UserDao userDao;
    private CourseDao courseDao;
    private CourseScheduleDao courseScheduleDao;

    @Autowired
    public void setSignDao(SignDao signDao) {
        this.signDao = signDao;
    }
    @Autowired
    public void setSignStudentDao(SignStudentDao signStudentDao) {
        this.signStudentDao = signStudentDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    @Autowired
    public void setCourseScheduleDao(CourseScheduleDao courseScheduleDao) {
        this.courseScheduleDao = courseScheduleDao;
    }

    /**
     * 发布签到
     * 在签到表中插入签到信息
     * @param sign sign
     * @return
     */
    @Override
    public int publishSign(Sign sign) {
        // 外键 课程id
        Course course = courseDao.selectById(sign.getCourseId());
        if (course==null)
            return -2;
        // 外键 userId
        User user = userDao.selectById(sign.getUserId());
        if (user==null)
            return -3;
        // 外键 courseScheduleId
        CourseSchedule courseSchedule = courseScheduleDao.selectById(sign.getCourseScheduleId());
        if (courseSchedule==null)
            return -4;
        int result = signDao.insert(sign);
        return result;
    }

    /**
     * 获取签到列表
     * 查询条件：
     * courseID,teacherId,courseScheduleId
     * @param sign
     * @return
     */
    @Override
    public List<Sign> getSignList(Sign sign) {
        QueryWrapper<Sign> signQueryWrapper = new QueryWrapper<Sign>();
        signQueryWrapper.eq("course_id",sign.getCourseId());
        signQueryWrapper.eq("user_id",sign.getUserId());
        signQueryWrapper.eq(sign.getCourseScheduleId()!=null,"course_schedule_id",sign.getCourseScheduleId());
        List<Sign> signList = signDao.selectList(signQueryWrapper);
        if (signList.size()>0){
            return signList;
        } else {
            return null;
        }
    }

    /**
     * 获取签到详情
     * 参数：signId
     * @param signStudent signStudent
     * @return
     */
    @Override
    public List<SignDetail> getSignDetail(SignStudent signStudent) {
        List<SignDetail> signDetail = signStudentDao.getSignDetail(signStudent.getSignId());
        if (signDetail.size()>0){
            return signDetail;
        } else {
            return null;
        }
    }

    @Override
    public int sign(SignStudent signStudent) {
        // 外键 signIds
        Sign sign = signDao.selectById(signStudent.getSignId());
        if (sign==null)
            return -2;
        // 外键 studentId
        User student = userDao.selectById(signStudent.getStudentId());
        if (student==null)
            return -3;
        int result = signStudentDao.insert(signStudent);
        return result;
    }
}
