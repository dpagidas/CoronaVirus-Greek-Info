package com.CoronavirusInfo.myapplication;

public class CasesPerDay {
    String Date;
    int Cases;


    public CasesPerDay(String date, int cases) {
        this.Date = date;
        this.Cases = cases;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCases() {
        return Cases;
    }

    public void setCases(int cases) {
        Cases = cases;
    }
}
