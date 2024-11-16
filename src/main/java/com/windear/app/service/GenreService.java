package com.windear.app.service;

import com.windear.app.entity.Genre;

import java.util.List;

public interface GenreService {
    Genre add(Genre genre);
    Genre findById(Integer id);
    List<Genre> findAll();
    void delete(Integer id);
}
