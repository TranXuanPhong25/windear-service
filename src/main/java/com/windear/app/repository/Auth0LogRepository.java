package com.windear.app.repository;

import com.windear.app.entity.Auth0Log;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface Auth0LogRepository extends JpaRepository<Auth0Log, String> {
    Auth0Log findFirstByOrderByDateDesc();
    Page<Auth0Log> findAllByOrderByDateDesc(Pageable pageable);

    @Query("SELECT DATE(u.date) AS time, COUNT(u) AS value " +
            "FROM Auth0Log u WHERE u.type = 's' " +
            "GROUP BY time " +
            "ORDER BY time DESC " +
            "LIMIT 30")
    List<Object[]> getLoginStatsIn30Days();
}
