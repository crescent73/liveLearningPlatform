package com.java.service.intf;

import com.java.model.entity.Collections;
import com.java.model.entity.Course;

import java.util.List;

public interface CollectionService {
    // 获取收藏课程列表
    List<Course> collectCourseList(Course course, Integer userId);
    // 添加收藏课程
    int addCollectCourse(Collections collections);
    // 删除收藏课程
    int deleteCollectCourse(List<Integer> collectionIdList);
}
