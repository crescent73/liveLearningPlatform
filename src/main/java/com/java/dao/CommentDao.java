package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.dto.CommentDetail;
import com.java.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentDao extends BaseMapper<Comment> {
    List<CommentDetail> getCommentList(Integer courseId);
}
