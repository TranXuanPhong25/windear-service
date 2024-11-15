package com.windear.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Shelves {
    @MongoId(targetType = FieldType.STRING)
    String userId;

    List<Shelf> shelves = new ArrayList<>();

    public Shelves() {

    }

    public Shelves(String userId) {
        this.userId = userId;
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
        if (shelves == null || shelves.isEmpty()) {
            Shelf shelf = new Shelf(name);
            shelves.add(shelf);
            return shelf;
        }
        return shelves.stream()
                .filter(shelf -> shelf.getName().equals(name))
                .findFirst()
                .orElse(new Shelf(name));
    }
}
