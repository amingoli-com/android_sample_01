package com.example.ermile.android_sample_01;

import android.support.v7.widget.CardView;

public class Tiket_item {
    String tiket_title,
            tiket_answer,
            tiket_end,
            tiket_user,
            tiket_id,
            tiket_time;

    public Tiket_item(String tiket_title, String tiket_answer, String tiket_end, String tiket_user, String tiket_id, String tiket_time) {
        this.tiket_title = tiket_title;
        this.tiket_answer = tiket_answer;
        this.tiket_end = tiket_end;
        this.tiket_user = tiket_user;
        this.tiket_id = tiket_id;
        this.tiket_time = tiket_time;
    }

    public String getTiket_title() {
        return tiket_title;
    }

    public void setTiket_title(String tiket_title) {
        this.tiket_title = tiket_title;
    }

    public String getTiket_answer() {
        return tiket_answer;
    }

    public void setTiket_answer(String tiket_answer) {
        this.tiket_answer = tiket_answer;
    }

    public String getTiket_end() {
        return tiket_end;
    }

    public void setTiket_end(String tiket_end) {
        this.tiket_end = tiket_end;
    }

    public String getTiket_user() {
        return tiket_user;
    }

    public void setTiket_user(String tiket_user) {
        this.tiket_user = tiket_user;
    }

    public String getTiket_id() {
        return tiket_id;
    }

    public void setTiket_id(String tiket_id) {
        this.tiket_id = tiket_id;
    }

    public String getTiket_time() {
        return tiket_time;
    }

    public void setTiket_time(String tiket_time) {
        this.tiket_time = tiket_time;
    }

}
