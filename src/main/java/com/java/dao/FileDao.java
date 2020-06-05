package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.CourseFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FileDao extends BaseMapper<CourseFile> {
    public int update(CourseFile file);
    public int insert(CourseFile file);
    public int delete(CourseFile file);
}
