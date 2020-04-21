package com.java.dao;

import com.java.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.List;

@Repository
@Mapper
public interface UserDao {

    @Select("select * from user where user_nickname=#{userNickname} and user_password=#{userPassword} and user_role=#{userRole}")
    List<User> select(User user);

}
