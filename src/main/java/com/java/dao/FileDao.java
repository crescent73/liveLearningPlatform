package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileDao extends BaseMapper<File> {
    public int update(File file);
    public int insert(File file);
    public int delete(File file);
}
