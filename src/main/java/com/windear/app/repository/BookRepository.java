package com.windear.app.repository;

import com.windear.app.entity.Book;
import com.windear.app.entity.BookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, BookId> {
    @Query("SELECT b FROM Book b WHERE b.bookId.id = :id")
    List<Book> findById(String id);
}
