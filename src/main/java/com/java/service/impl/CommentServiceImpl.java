package com.java.service.impl;

import com.java.dao.CommentDao;
import com.java.model.dto.CommentDetail;
import com.java.model.entity.Comment;
import com.java.service.intf.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<CommentDetail> getCommentList(Integer courseId) {
        List<CommentDetail> commentList = commentDao.getCommentList(courseId);
        if (commentList.size()>0)
            return commentList;
        return null;
    }

    @Override
    public int addComment(Comment comment) {
        int insert = commentDao.insert(comment);
        return insert;
    }

    @Override
    public int deleteComment(Integer commentId) {
        return commentDao.deleteById(commentId);
    }
}
