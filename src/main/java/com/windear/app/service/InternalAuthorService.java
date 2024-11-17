package com.windear.app.service;

import com.windear.app.entity.InternalAuthor;

import java.util.List;

public interface InternalAuthorService {
    InternalAuthor add(InternalAuthor author);
    InternalAuthor findById(Integer id);
    List<InternalAuthor> findAll();
    void delete(Integer id);
}
