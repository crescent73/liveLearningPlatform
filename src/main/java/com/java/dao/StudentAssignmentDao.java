package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.StudentAssignment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentAssignmentDao extends BaseMapper<StudentAssignment> {
}
