package com.windear.app.service;

import com.windear.app.model.AnalyticStat;
import com.windear.app.repository.Auth0LogRepository;
import com.windear.app.repository.BookLoanRepository;
import com.windear.app.repository.InternalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticStatServiceImpl implements AnalyticStatService {
    private final InternalBookRepository internalBookRepository;
    private final BookLoanRepository bookLoanRepository;
    private final Auth0LogRepository auth0LogRepository;

    @Autowired
    public AnalyticStatServiceImpl(InternalBookRepository internalBookRepository,
                                   BookLoanRepository bookLoanRepository,
                                   Auth0LogRepository auth0LogRepository) {
        this.internalBookRepository = internalBookRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.auth0LogRepository = auth0LogRepository;
    }

    private List<AnalyticStat> convertRawStatsToStats(List<Object[]> rawStats) {
        List<AnalyticStat> stats = new ArrayList<>();
        for (Object[] row : rawStats) {
            LocalDate time = ((java.sql.Date) row[0]).toLocalDate();
            String value = String.valueOf(row[1]);
            stats.add(new AnalyticStat(value, time));
        }
        return stats;
    }

    @Override
    public List<AnalyticStat> getStatsOfInternalBookIn30Days() {
        List<Object[]> rawStats = internalBookRepository.getStatsInLast30Days();
        return convertRawStatsToStats(rawStats);
    }

    @Override
    public List<AnalyticStat> getStatsOfBorrowRequestIn30Days() {
        List<Object[]> rawStats = bookLoanRepository.getBorrowStatsIn30Days();
        return convertRawStatsToStats(rawStats);
    }

    @Override
    public List<AnalyticStat> getStatsOfReturnRequestIn30Days() {
        List<Object[]> rawStats = bookLoanRepository.getReturnStatsIn30Days();
        return convertRawStatsToStats(rawStats);
    }

    @Override
    public List<AnalyticStat> getStatsOfLoginIn30Days() {
        List<Object[]> rawStats = auth0LogRepository.getLoginStatsIn30Days();
        return convertRawStatsToStats(rawStats);
    }
}
