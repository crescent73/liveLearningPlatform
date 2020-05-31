package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Guest {
    private LiveUser liveUser;
    private long accessTime;
}
