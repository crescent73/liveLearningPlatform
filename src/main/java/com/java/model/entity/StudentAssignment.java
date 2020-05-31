package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@ToString
public class StudentAssignment {
    private Integer studentAssignmentId;
    private Integer assignmentId;
    private Integer userId;
    private String assignmentSubmission;
    private Timestamp uploadTime;
    private String teacherReply;
    private Double score;
}
