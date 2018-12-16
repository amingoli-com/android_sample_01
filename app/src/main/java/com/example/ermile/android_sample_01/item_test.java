package com.example.ermile.android_sample_01;

public class item_test {
    String users,des,dates;

    public item_test(String users, String des, String dates) {
        this.users = users;
        this.des = des;
        this.dates = dates;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getdes() {
        return des;
    }

    public void setdes(String des) {
        this.des = des;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
