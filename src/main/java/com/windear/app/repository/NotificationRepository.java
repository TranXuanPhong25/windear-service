package com.windear.app.repository;

import com.windear.app.entity.BookLoan;
import com.windear.app.entity.Notification;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.userId= :userId ORDER BY n.timestamp DESC")
    List<Notification> findAllByUserId(String userId);
}
