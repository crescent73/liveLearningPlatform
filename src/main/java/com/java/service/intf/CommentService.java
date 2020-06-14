package com.java.service.intf;

import com.java.model.dto.CommentDetail;
import com.java.model.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDetail> getCommentList(Integer courseId);
    int addComment(Comment comment);
    int deleteComment(Integer commentId);
}
