package com.example.ermile.android_sample_01;

public class Notif_item {
    String iNotif_time,iNotif_id;

    public Notif_item(String iNotif_time, String iNotif_id) {
        this.iNotif_time = iNotif_time;
        this.iNotif_id = iNotif_id;
    }

    public String getiNotif_time() {
        return iNotif_time;
    }

    public void setiNotif_time(String iNotif_time) {
        this.iNotif_time = iNotif_time;
    }

    public String getiNotif_id() {
        return iNotif_id;
    }

    public void setiNotif_id(String iNotif_id) {
        this.iNotif_id = iNotif_id;
    }
}
