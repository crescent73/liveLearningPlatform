package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.dto.SignDetail;
import com.java.model.entity.SignStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SignStudentDao extends BaseMapper<SignStudent> {
    List<SignDetail> getSignDetail(Integer signId);
}
