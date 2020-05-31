package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LiveUser {
    Integer id;
    String ip;
    String randomName;
}
