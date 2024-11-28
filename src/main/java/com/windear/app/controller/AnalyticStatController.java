package com.windear.app.controller;

import com.windear.app.model.AnalyticStat;
import com.windear.app.service.AnalyticStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytic")
public class AnalyticStatController {
    private final AnalyticStatService analyticStatService;

    @Autowired
    public AnalyticStatController(AnalyticStatService analyticStatService) {
        this.analyticStatService = analyticStatService;
    }

    @GetMapping("/db/add-book")
    public List<AnalyticStat> getAddBookStatsIn30Days() {
        return analyticStatService.getStatsOfInternalBookIn30Days();
    }

    @GetMapping("/bookloan/borrow")
    public List<AnalyticStat> getBorrowBookStatsIn30Days() {
        return analyticStatService.getStatsOfBorrowRequestIn30Days();
    }

    @GetMapping("/bookloan/return")
    public List<AnalyticStat> getReturnBookStatsIn30Days() {
        return analyticStatService.getStatsOfReturnRequestIn30Days();
    }
}
