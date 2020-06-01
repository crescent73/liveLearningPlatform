package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseDao extends BaseMapper<Course> {

    /**
     * 搜索字段 课程名 课程号
     * @param key 搜索词
     * @return 结果
     */
    public List<CourseDetail> search(@Param("course") Course course, @Param("key") String key, @Param("studentId") Long studentId);

    public List<CourseDetail> search1(@Param("course") Course course);
}
