package com.windear.app.service;

import com.windear.app.entity.InternalAuthor;
import com.windear.app.entity.News;
import com.windear.app.repository.InternalAuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternalAuthorServiceImpl implements InternalAuthorService {
    private final InternalAuthorRepository authorRepository;

    @Autowired
    public InternalAuthorServiceImpl(InternalAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public InternalAuthor add(InternalAuthor author) {
        return authorRepository.save(author);
    }

    @Override
    public List<InternalAuthor> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public InternalAuthor findById(Integer id) {
        Optional<InternalAuthor> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        } else {
            throw new RuntimeException("Author with title not found: " + id);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (findById(id) != null) {
            authorRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Author with title not found: " + id);
        }
    }
}
