package com.java.dao;

import com.java.model.entity.File;
import com.java.model.entity.FileDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileDao {
    public List <FileDetail> find(File file);
    public int update(File file);
    public int insert(File file);
    public int delete(File file);
}
