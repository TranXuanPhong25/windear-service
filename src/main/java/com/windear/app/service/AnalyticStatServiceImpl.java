package com.windear.app.service;

import com.windear.app.model.AnalyticStat;
import com.windear.app.repository.BookLoanRepository;
import com.windear.app.repository.InternalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticStatServiceImpl implements AnalyticStatService {
    private final InternalBookRepository internalBookRepository;
    private final BookLoanRepository bookLoanRepository;

    @Autowired
    public AnalyticStatServiceImpl(InternalBookRepository internalBookRepository, BookLoanRepository bookLoanRepository) {
        this.internalBookRepository = internalBookRepository;
        this.bookLoanRepository = bookLoanRepository;
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
}
