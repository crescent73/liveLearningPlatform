package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class SignStudent {
    private Integer studentSignId;
    private Integer signId;
    private Integer studentId;
    private Timestamp signTime;
}
