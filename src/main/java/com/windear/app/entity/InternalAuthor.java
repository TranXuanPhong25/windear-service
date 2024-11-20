package com.windear.app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "internal_author", schema = "public")
public class InternalAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "day_of_birth")
    private LocalDate dayOfBirth;

    public InternalAuthor() {
    }

    public InternalAuthor(Integer authorId, String name, String description, LocalDate dayOfBirth) {
        this.authorId = authorId;
        this.name = name;
        this.description = description;
        this.dayOfBirth = dayOfBirth;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }
}
