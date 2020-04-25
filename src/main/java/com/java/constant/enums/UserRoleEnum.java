package com.java.constant.enums;

import lombok.Data;

public enum UserRoleEnum {
    ADMIN(1),
    TEACHER(2),
    STUDENT(3);

    private Integer role;

    UserRoleEnum(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
