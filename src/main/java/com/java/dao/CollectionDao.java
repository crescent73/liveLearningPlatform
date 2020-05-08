package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.Collections;
import com.java.model.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CollectionDao extends BaseMapper<Collections> {

    List<Course> findUserCollection(Integer userId, Course course);


}
