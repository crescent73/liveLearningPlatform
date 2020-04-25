package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    @TableId(type = IdType.INPUT)
    private Integer userId;
    private String userNickname;
    private String userGender;
    private String userPassword;
    private String userEmail;
    private  String userRole;
    private Integer schoolId;
    private String userNumber;
    private String userName;

}
