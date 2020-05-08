package com.java.service.impl;

import com.java.dao.CollectionDao;
import com.java.dao.CourseDao;
import com.java.dao.UserDao;
import com.java.model.entity.Collections;
import com.java.model.entity.Course;
import com.java.model.entity.User;
import com.java.service.intf.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private CollectionDao collectionDao;
    private UserDao userDao;
    private CourseDao courseDao;
    @Autowired
    public void setCollectionDao(CollectionDao collectionDao) {
        this.collectionDao = collectionDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    /**
     * 获取收藏课程列表
     * 查询参数：
     * courseId,teacherId,courseName,courseNumber
     * @return
     */
    @Override
    public List<Course> collectCourseList(Course course, Integer userId) {
        List<Course> courseList = collectionDao.findUserCollection(userId, course);
        if (courseList.size()>0){
            return courseList;
        } else {
            return null;
        }
    }

    /**
     * 添加收藏课程
     * 参数：
     * courseNumber,teacherId
     * @param collections collections
     * @return
     */
    @Override
    public int addCollectCourse(Collections collections) {
        // 外键 课程id
        Course course = courseDao.selectById(collections.getCourseId());
        if (course==null)
            return -2;
        // 外键 userId
        User user = userDao.selectById(collections.getUserId());
        if (user==null)
            return -3;
        // 检查是否已经存在
        int result = collectionDao.insert(collections);
        return result;
    }

    /**
     * 删除
     * @param collectionIdList
     * @return
     */
    @Override
    public int deleteCollectCourse(List<Integer> collectionIdList) {
        int result = collectionDao.deleteBatchIds(collectionIdList);
        return result;
    }
}
