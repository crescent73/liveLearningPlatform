package com.java.dao;

import com.java.model.entity.LiveUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LiveUserDao {

    @Select("select * from live_user where ip=#{ip}")
    LiveUser findOne(String ip);

    @Insert("insert into live_user values(#{ip},#{randomName})")
    int insert(LiveUser liveUser);
}
