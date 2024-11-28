package com.windear.app.model;

import java.time.LocalDate;

public class AnalyticStat {
    private String value;
    private LocalDate time;

    public AnalyticStat(String value, LocalDate time) {
        this.value = value;
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}