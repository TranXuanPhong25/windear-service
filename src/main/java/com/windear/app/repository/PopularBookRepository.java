package com.windear.app.repository;

import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopularBookRepository extends JpaRepository<PopularBook, Integer> {
    List<PopularBook> findTop10ByOrderByScoreDesc();
}
