package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.CourseSchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CourseScheduleDao extends BaseMapper<CourseSchedule> {
    @Insert("insert into course_schedule(course_id) values(#{courseId})")
    @Options(useGeneratedKeys = true,keyProperty = "courseScheduleId",keyColumn = "course_schedule_id")
    Integer insertCourseSchedule(CourseSchedule courseSchedule);
    public int delete(CourseSchedule courseSchedule);
}
