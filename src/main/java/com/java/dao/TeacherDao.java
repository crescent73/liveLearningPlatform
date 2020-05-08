package com.java.dao;

import com.java.model.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherDao {
    public List<Teacher> find(Teacher teacher);

    public  int update(Teacher teacher) ;

    public int delete(Teacher teacher);
    public int insert(Teacher teacher);

    /**
     * 搜索字段 教师名，教师工号
     * @param key 搜索词
     * @return 结果
     */
    public List<Teacher> search(@Param("teacher") Teacher teacher, @Param("key") String key);
}