package com.windear.app.service;

import com.windear.app.model.AnalyticStat;

import java.util.List;

public interface AnalyticStatService {
    List<AnalyticStat> getStatsOfInternalBookIn30Days();
    List<AnalyticStat> getStatsOfBorrowRequestIn30Days();
    List<AnalyticStat> getStatsOfReturnRequestIn30Days();
    List<AnalyticStat> getStatsOfLoginIn30Days();
}
