package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.Sign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SignDao extends BaseMapper<Sign> {
    @Select("select distinct user_nickname " +
            "from sign inner join user on sign.user_id = user.user_id " +
            "where course_schedule_id =#{courseScheduleId}")
    List<String> getSignList(Integer courseScheduleId);
}
