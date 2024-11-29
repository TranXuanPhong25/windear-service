package com.windear.app.service;

import com.windear.app.entity.Auth0Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface Auth0LogService {
    /**
     * Adds a new Auth0 log entry.
     * @param auth0Log the Auth0 log entry to add
     */
    void addLog(Auth0Log auth0Log);

    /**
     * Retrieves the latest Auth0 log entry.
     * @return the latest Auth0 log entry
     */
    Auth0Log getLatestLog();


    /**
     * Retrieves a paginated list of Auth0 log entries.
     * @param pageNumber the page number to retrieve
     * @param pageSize the number of entries per page
     * @return a page of Auth0 log entries
     */
    Page<Auth0Log> getLogsPage(int pageNumber, int pageSize);}