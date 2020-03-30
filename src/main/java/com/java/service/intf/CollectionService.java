package com.java.service.intf;

import com.java.model.entity.Collection;

import java.util.List;

public interface CollectionService {
    // 获取收藏课程列表
    List<Collection> collectCourseList();
    // 添加收藏课程
    int addCollectCourse();
    // 删除收藏课程
    int deleteCollectCourse();
}
