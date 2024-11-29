package com.windear.app.repository;

import com.windear.app.entity.BookGenre;
import com.windear.app.primarykey.BookGenreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, BookGenreId> {
    @Query("SELECT b FROM BookGenre b WHERE b.bookGenreId.bookId = :bookId")
    List<BookGenre> findAllByBookId(Integer bookId);
}
