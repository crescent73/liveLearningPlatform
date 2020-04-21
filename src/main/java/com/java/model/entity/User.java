package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    Integer userId;
    String userNickname;
    String userGender;
    String userPassword;
    String userEmail;
    String userRole;
    Integer schoolId;
    String userNumber;
    String userName;

}
