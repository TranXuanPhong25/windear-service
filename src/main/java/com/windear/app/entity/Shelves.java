package com.windear.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Shelves {
    @MongoId(targetType = FieldType.STRING)
    private String userId;

    private List<Shelf> shelves = new ArrayList<>();

    private void addDefaultShelf() {
        shelves.add(new Shelf("Want to read"));
        shelves.add(new Shelf("Currently reading"));
        shelves.add(new Shelf("Read"));
    }

    public Shelves() {
        addDefaultShelf();
    }

    public Shelves(String userId) {
        addDefaultShelf();
        this.userId = userId;
    }

    public Shelves(String userId, List<Shelf> shelves) {
        this.userId = userId;
        this.shelves = shelves;
        addDefaultShelf();
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
                .orElseGet(() -> {
                    Shelf newShelf = new Shelf(name);
                    shelves.add(newShelf);
                    return newShelf;
                });
    }
}
