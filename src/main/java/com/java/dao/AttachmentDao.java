package com.java.dao;

import com.java.model.entity.Attachment;

import java.util.List;

public interface AttachmentDao {
    public int insert(Attachment attachment);

    public int update(Attachment attachment);

    public int delete(Attachment attachment);

    public List<Attachment> find(Attachment attachment);
}
