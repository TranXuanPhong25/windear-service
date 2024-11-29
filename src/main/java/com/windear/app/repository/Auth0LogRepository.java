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

    @Query(
            value = "SELECT DATE(date) AS time, COUNT(_id) AS value\n" +
                    "FROM auth0_log\n" +
                    "WHERE type = 's' AND\n" +
                    "DATE(date) >= (NOW() - INTERVAL '30 days')\n" +
                    "GROUP BY time\n" +
                    "ORDER BY time",
            nativeQuery = true
    )
    List<Object[]> getLoginStatsIn30Days();
}
