package com.java.model.dto;

import com.java.model.entity.Comment;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class CommentDetail{
    private Integer commentId;
    private Integer userId;
    private String userNickname;
    private String commentContent;
    private Timestamp publishTime;
}
