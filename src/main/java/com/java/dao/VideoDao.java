package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideoDao extends BaseMapper<Video> {
}
