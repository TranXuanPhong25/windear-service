package com.windear.app.repository;

import com.windear.app.entity.InternalAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalAuthorRepository extends JpaRepository<InternalAuthor, Integer> {

}
