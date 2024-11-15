package com.windear.app.service;


import com.windear.app.entity.Genre;
import com.windear.app.entity.InternalAuthor;
import com.windear.app.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional
    public Genre add(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(Integer id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            return genre.get();
        } else {
            throw new RuntimeException("Genre with title not found: " + id);
        }
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) != null) {
            genreRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Genre with title not found: " + id);
        }
    }
}
