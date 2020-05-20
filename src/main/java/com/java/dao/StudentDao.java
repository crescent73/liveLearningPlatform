package com.java.dao;
import com.java.model.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentDao {
    public List<Student> find(Student student);

    public static int update(Student student) {
        return 0;
    }

    public int delete(Student student);
    public int insert(Student student);

    /**
     * 搜索字段： 学生姓名 学号 班级
     * @param key 搜索词
     * @return 结果
     */
    public List<Student> search(@Param("student") Student student, @Param("key") String key, @Param("courseId") Long courseId);
}