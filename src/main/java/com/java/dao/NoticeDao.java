package com.java.dao;

import com.java.model.entity.Notice;
import com.java.model.entity.NoticeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoticeDao {
    public List<NoticeDetail> find(Notice notice);
    public int update(Notice notice);
    public int insert(Notice notice);
    public int delete(Notice notice);
}
