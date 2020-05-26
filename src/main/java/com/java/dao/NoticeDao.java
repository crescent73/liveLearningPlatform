package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoticeDao extends BaseMapper<Notice> {
    public int update(Notice notice);
    public int insert(Notice notice);
    public int delete(Notice notice);
}
