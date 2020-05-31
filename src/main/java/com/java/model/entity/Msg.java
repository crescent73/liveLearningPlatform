package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Calendar;

@Data
@ToString
public class Msg {
    private String creator;
    private String msgBody;
    private Calendar sTime;
}
