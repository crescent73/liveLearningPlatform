package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.dto.CommentDetail;
import com.java.model.entity.Comment;
import com.java.model.vo.ResultData;
import com.java.service.intf.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommentController {
    @Autowired
    private CommentService commentService;

    public ResultData getCommentList(Integer courseId){
        ResultData<List<CommentDetail>> resultData = new ResultData<>();
        if (courseId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<CommentDetail> commentList = commentService.getCommentList(courseId);
        if (commentList!=null) {
            resultData.setData(commentList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    public ResultData addComment(Comment comment){
        ResultData resultData = new ResultData();
        if (comment.getCourseId()==null
                ||comment.getUserId()==null
                ||comment.getCommentContent()==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = commentService.addComment(comment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    public ResultData deleteComment(Integer commentId){
        ResultData resultData = new ResultData();
        if (commentId==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = commentService.deleteComment(commentId);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
        }
        return resultData;
    }

}
