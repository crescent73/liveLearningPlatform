package com.java.service.impl;

import com.java.constant.enums.ResultCodeEnum;
import com.java.dao.CourseDao;
import com.java.dao.CourseScheduleDao;
import com.java.dao.StudentDao;
import com.java.dao.TeacherDao;
import com.java.model.entity.*;
import com.java.model.vo.Data;
import com.java.model.vo.ResultData;
import com.java.service.intf.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class AdminServiceImpl implements SystemService {
    private ResultData <Data> resultData;
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseScheduleDao courseScheduleDao;

    @Transactional
    public ResultData addCourse(Course course) {
        resultData = new ResultData <Data>();
        if(course != null) {
            // 插入前进行校验工作，判断约束
            // 课程号唯一
            if(isCourseExist(null,course.getCourseNumber())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_COURSE_ALREADY_EXIST);//课程已存在
                return resultData;
            }
            if(course.getTeacherId()!= null) {
                if(!isTeacherExist(course.getTeacherId(),null)) {
                    resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_TEACHER_NOT_EXIST);//添加失败，老师不存在
                    return resultData;
                }
            }
            int result = courseDao.insert(course);
            if(result >0) {
                resultData.setResult(ResultCodeEnum.DB_ADD_SUCCESS);//添加成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);//添加失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //id直接返回数据必要参数为空
        }

        System.out.println("service:"+resultData);
        return resultData;
    }

    @Transactional
    public ResultData deleteCourseSchedule(Integer courseId, Long[] studentList) {
        resultData = new ResultData <Data>();
        if(courseId != null && studentList.length != 0){
            for(Long studentId:studentList) {
                if(!isCourseExist(Math.toIntExact(courseId),null)){
                    resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE_COURSE_NOT_EXIST);//课程不存在
                    return resultData;
                }
                if(!isStudentExist(Math.toIntExact(studentId),null)){
                    resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE_STUDENT_NOT_EXIST);//学生不存在
                    return resultData;
                }
//                System.out.println("studentID:"+studentId+"courseId:"+courseId);
                int result = courseScheduleDao.delete(new CourseSchedule(Math.toIntExact(studentId),courseId));
//                System.out.println("result"+result);
                if(result <= 0) {
                    resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
                    return resultData;
                }
            }
            resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS);
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //重要请求参数为空
        }
        return resultData;
    }

    /**
     * 判断课程是否存在
     * 外键约束条件，若课程不存在插入修改报错
     * @param courseId
     * @return 存在 true 不存在 false
     */
    private boolean isCourseExist(Integer courseId,String courseNumber) {
        List<CourseDetail> courses = null;
        Course course = new Course();
        if(courseId != null) {
            course.setId(courseId);
            courses = courseDao.find(course);
        } else if(courseNumber != null){
            course.setCourseNumber(courseNumber);
            courses = courseDao.find(course);
        }
        return courses != null && courses.size() == 1;
    }

    /**
     * 判断老师是否存在
     * 外键约束条件，若老师不存在插入修改报错
     * @param teacherId 教师id
     * @return 存在 true 不存在 false
     */
    private boolean isTeacherExist(Integer teacherId,String teacherNumber) {
        resultData = new ResultData <Data>();
        List<Teacher> teachers = null;
        Teacher teacher = new Teacher();
        if(teacherId != null) {
            teacher.setId(teacherId);
            teachers = teacherDao.find(teacher);
        }else if(teacherNumber != null) {
            teacher.setTeacherNumber(teacherNumber);
            teachers = teacherDao.find(teacher);
        }
        return teachers != null && teachers.size() == 1;
    }

    /**
     * 判断学生是否存在
     * 外键约束条件
     * @param studentId 学生id
     * @return
     */
    private boolean isStudentExist(Integer studentId,String studentNumber) {
        List<Student> students = null;
        Student student = new Student();
        if(studentId != null) {
            student.setId(studentId);
            students = studentDao.find(student);
        } else if(studentNumber != null) {
            student.setStudentNumber(studentNumber);
            students = studentDao.find(student);
        }
        return students != null && students.size() == 1;
    }
}
