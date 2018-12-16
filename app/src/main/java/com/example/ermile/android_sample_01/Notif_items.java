package com.example.ermile.android_sample_01;

public class Notif_items {
    String titileid,dates;

    public Notif_items(String titileid, String dates) {
        this.titileid = titileid;
        this.dates = dates;
    }

    public String getTitileid() {
        return titileid;
    }

    public void setTitileid(String titileid) {
        this.titileid = titileid;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
