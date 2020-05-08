package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.CourseDao;
import com.java.dao.CourseScheduleDao;
import com.java.dao.StudentCourseDao;
import com.java.dao.UserDao;
import com.java.model.entity.Course;
import com.java.model.entity.CourseDetail;
import com.java.model.entity.CourseSchedule;
import com.java.model.entity.User;
import com.java.service.intf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;
    private UserDao userDao;
    private CourseScheduleDao courseScheduleDao;
    private StudentCourseDao studentCourseDao;
    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setCourseScheduleDao(CourseScheduleDao courseScheduleDao) {
        this.courseScheduleDao = courseScheduleDao;
    }
    @Autowired
    public void setStudentCourseDao(StudentCourseDao studentCourseDao) {
        this.studentCourseDao = studentCourseDao;
    }

    /**
     * 查询课程
     * 查询条件：
     * courseId，teacherId，courseName，courseNumber
     * @param course 课程
     * @return
     */
    @Override
    public List<Course> getCourseList(Course course) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>();
        courseQueryWrapper.eq(course.getCourseId() != null,"course_id",course.getCourseId());
        courseQueryWrapper.eq(course.getCourseName()!=null,"course_name",course.getCourseName());
        courseQueryWrapper.eq(course.getCourseNumber()!=null,"course_name",course.getCourseName());
        courseQueryWrapper.eq(course.getTeacherId()!=null,"teacher_id",course.getTeacherId());
        List<Course> courseList = courseDao.selectList(courseQueryWrapper);
        if (courseList.size()>0)
            return courseList;
        else
            return null;
    }

    public List<Course> getStudentCourse(Integer studentId, Course course){
        List<Course> courses = studentCourseDao.searchStudentCourse(studentId, course);
        if (courses.size()>0){
            return courses;
        } else {
            return null;
        }
    }

    public List<CourseDetail> getCourse(Course course){
        List<CourseDetail> courses = courseDao.search1(course);
        if (courses.size()>0){
            return courses;
        } else {
            return null;
        }
    }

    /**
     * 添加课程
     * 添加内容：
     * courseName,courseNumber,teacherId
     * @param course 添加的课程
     * @return
     */
    @Override
    public int createCourse(Course course) {
        // 外键检查 teacherId
        User teacher = userDao.selectById(course.getTeacherId());
        if (teacher == null){
            return -2;
        }
        System.out.println(course);
        int result = courseDao.insert(course);
        return result;
    }

    /**
     * 删除课程
     * 支持批量删除
     * @param courseList 课程id列表
     * @return
     */
    @Override
    public int deleteCourse(List<Integer> courseList) {
        return courseDao.deleteBatchIds(courseList);
    }

    /**
     * 修改课程权限
     * 修改内容：
     * courseType
     * 1：对外展示课程
     * 2：对外不展示课程
     * @param course 修改的课程
     * @return
     */
    @Override
    public int setCoursePermission(Course course) {
        return courseDao.updateById(course);
    }

    @Override
    public int startCourse(CourseSchedule courseSchedule) {
        // 检查外键 courseId
        courseScheduleDao.insertCourseSchedule(courseSchedule);
        Integer id = courseSchedule.getCourseScheduleId();
        return id!=null?id:-1;
    }

    @Override
    public int endCourse(CourseSchedule courseSchedule) {
        courseSchedule.setEndTime(new Timestamp(System.currentTimeMillis()));
        int result = courseScheduleDao.updateById(courseSchedule);
        return result;
    }

    public Course getCourseBySchedule(Integer courseScheduleId){
        // 根据courseSchedule查询courseId
        CourseSchedule courseSchedule = courseScheduleDao.selectById(courseScheduleId);
        // 再根据courseId查询teacherId
        Course course = courseDao.selectById(courseSchedule.getCourseId());
        return course;
    }
}
