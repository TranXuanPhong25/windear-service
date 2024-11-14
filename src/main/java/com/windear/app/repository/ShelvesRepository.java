package com.windear.app.repository;

import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelvesRepository extends MongoRepository<Shelves, String> {

}
