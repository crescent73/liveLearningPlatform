package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.Assignment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AssignmentDao extends BaseMapper<Assignment> {
}
