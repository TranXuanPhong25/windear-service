package com.windear.app.repository;

import com.windear.app.entity.InternalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalBookRepository extends JpaRepository<InternalBook, Integer> {
    List<InternalBook> findTop10ByOrderByReleaseDateDesc();
}