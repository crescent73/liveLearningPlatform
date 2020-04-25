package com.java.model.dto;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class SignDetail {
    private Integer signId;
    private Integer userId;
    private Timestamp signTime;
    private String userNickname;
    private String userNumber;
    private  String userRole;
}
