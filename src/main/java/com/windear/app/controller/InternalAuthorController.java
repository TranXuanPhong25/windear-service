package com.windear.app.controller;

import com.windear.app.entity.InternalAuthor;
import com.windear.app.entity.InternalBook;
import com.windear.app.service.InternalAuthorService;
import com.windear.app.service.InternalAuthorServiceImpl;
import com.windear.app.service.InternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class InternalAuthorController {
    private final InternalAuthorService authorService;

    @Autowired
    public InternalAuthorController(InternalAuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/author")
    public InternalAuthor add(@RequestBody InternalAuthor author) {
        return authorService.add(author);
    }

    @GetMapping("/author/{id}")
    public InternalAuthor findById(@PathVariable String id) {
        return authorService.findById(Integer.parseInt(id));
    }

    @GetMapping("/author")
    public List<InternalAuthor> findAll() {
        return authorService.findAll();
    }

    @DeleteMapping("/author/{id}")
    public void delete(@PathVariable String id) {
        authorService.delete(Integer.parseInt(id));
    }

}
