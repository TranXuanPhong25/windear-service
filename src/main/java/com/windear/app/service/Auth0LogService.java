package com.windear.app.service;

import com.windear.app.entity.Auth0Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface Auth0LogService {
    void addLog(Auth0Log auth0Log);

    Auth0Log getLatestLog();

    Page<Auth0Log> getLogsPage(int pageNumber, int pageSize);
}