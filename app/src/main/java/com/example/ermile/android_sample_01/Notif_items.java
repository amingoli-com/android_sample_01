package com.example.ermile.android_sample_01;

public class Notif_items {
    String titileid,dates,telegram;

    public Notif_items(String titileid, String dates, String telegram) {
        this.titileid = titileid;
        this.dates = dates;
        this.telegram = telegram;
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

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }
}
