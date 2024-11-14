package com.windear.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Shelves {
    @MongoId(targetType = FieldType.STRING)
    String userId;

    List<Shelf> shelves;

    public Shelves() {

    }

    public Shelves(String userId, List<Shelf> shelves) {
        this.userId = userId;
        this.shelves = shelves;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }

    public Shelf getShelfByName(String name) {
        for (Shelf shelf : shelves) {
            if (shelf.getName().equals(name)) return shelf;
        }
        return null;
    }
}
