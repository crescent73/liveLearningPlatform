package com.java.dao;

import com.java.model.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AttachmentDao {
    public int insert(Attachment attachment);

    public int update(Attachment attachment);

    public int delete(Attachment attachment);

    public List<Attachment> find(Attachment attachment);
}
