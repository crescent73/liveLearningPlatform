package com.java.service.impl;

import com.java.dao.CourseDao;
import com.java.dao.CourseScheduleDao;
import com.java.dao.StudentCourseDao;
import com.java.dao.UserDao;
import com.java.model.entity.Course;
import com.java.model.dto.CourseDetail;
import com.java.model.entity.CourseSchedule;
import com.java.model.entity.User;
import com.java.service.intf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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
    public List<CourseDetail> getCourseList(Course course) {
        List<CourseDetail> courses = courseDao.searchCourse(course);
        if (courses.size()>0){
            return courses;
        } else {
            return null;
        }
    }
    @Override
    public Course getCourseById(Integer courseId) {
        Course course = courseDao.selectById(courseId);
        return course;
    }

    public List<CourseDetail> getStudentCourse(Integer studentId, Course course){
        List<CourseDetail> courses = studentCourseDao.searchStudentCourse(studentId, course);
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
    public int createCourse(Course course,MultipartFile picture) {
        java.io.File filePath = new java.io.File(course.getCoursePicturePath());
        if(!filePath.exists()) {
            filePath.mkdirs();
        }
        String newFileName = UUID.randomUUID()+ "_" + course.getCoursePictureName();
        System.out.println(newFileName);
        try {
            System.out.println(course.getCoursePicturePath());
            picture.transferTo(new java.io.File(course.getCoursePicturePath()+"\\"+newFileName));
            course.setCoursePicturePath(course.getCoursePicturePath()+"\\"+newFileName);
            System.out.println(course.getCoursePicturePath());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
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
