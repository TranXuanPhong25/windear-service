package com.windear.app.service;

import com.windear.app.entity.Auth0Log;
import com.windear.app.repository.Auth0LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Auth0LogServiceImpl implements Auth0LogService {
    private final Auth0LogRepository auth0LogRepository;

    @Autowired
    public Auth0LogServiceImpl(Auth0LogRepository auth0LogRepository) {
        this.auth0LogRepository = auth0LogRepository;
    }

    @Override
    public void addLog(Auth0Log auth0Log) {
        auth0LogRepository.save(auth0Log);
    }

    @Override
    public Auth0Log getLatestLog() {
        return auth0LogRepository.findFirstByOrderByDateDesc();
    }

    @Override
    public Page<Auth0Log> getLogsPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return auth0LogRepository.findAllByOrderByDateDesc(pageRequest);
    }
}
