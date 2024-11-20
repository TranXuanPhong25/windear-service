package com.windear.app.repository;

import com.windear.app.entity.Auth0Log;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface Auth0LogRepository extends JpaRepository<Auth0Log, String> {
    Auth0Log findFirstByOrderByDateDesc();
    Page<Auth0Log> findAllByOrderByDateDesc(Pageable pageable);
}
