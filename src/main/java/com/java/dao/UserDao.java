package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Mapper

public interface UserDao extends BaseMapper<User> {
    JdbcTemplate jdbcTemplate =null;

     static void addUser(User user) {
         ;
         //插入数据库
        String sql = "INSERT INTO users VALUES(null,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate
                .update(sql,user.getUserId(),user.getUserNickname(), user.getUserGender(), user.getUserPassword(), user.getUserEmail()
                ,user.getUserRole(),user.getSchoolId(),user.getUserNumber(),user.getUserName());
    }

}
