package com.windear.app.service;

import com.windear.app.model.AnalyticStat;

import java.util.List;

public interface AnalyticStatService {
    /**
     * Get add stats of internal book in 30 days
     * @return list of internal book stat
     */
    List<AnalyticStat> getStatsOfInternalBookIn30Days();

    /**
     * Get statistics of borrow requests in the last 30 days.
     * @return list of borrow request statistics
     */
    List<AnalyticStat> getStatsOfBorrowRequestIn30Days();

    /**
     * Get statistics of return requests in the last 30 days.
     * @return list of return request statistics
     */
    List<AnalyticStat> getStatsOfReturnRequestIn30Days();
    List<AnalyticStat> getStatsOfLoginIn30Days();
}
