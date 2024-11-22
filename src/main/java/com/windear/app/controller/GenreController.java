package com.windear.app.controller;

import com.windear.app.entity.Genre;
import com.windear.app.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/genre")
    public Genre add(@RequestBody Genre genre) {
        return genreService.add(genre);
    }

    @GetMapping("/genre/{id}")
    public Genre findById(@PathVariable Integer id) {
        return genreService.findById(id);
    }

    @GetMapping("/genres")
    public List<Genre> fidAll() {
        return genreService.findAll();
    }

    @DeleteMapping("/genre/{id}")
    public void delete(@PathVariable Integer id) {
        genreService.delete(id);
    }
}
