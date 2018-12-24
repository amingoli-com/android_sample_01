package com.example.ermile.android_sample_01;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ermile.android_sample_01.network.AppContoroler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Handler mHandler;
    public boolean continue_or_stop;
    Toolbar toolbars;
    TextView netview;

    // user is login and user & pass is: 519
    int ids = 195191378;

    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout tab_top, tab_bottom;

    //Fragment's
    Web_view oneFragment = new Web_view();
    Support_my_Tiket twoFragment = new Support_my_Tiket();
    Support_Notif treeFragment = new Support_Notif();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        // Chek net
        new NetCheck().execute();

        if (netview == null){
            // Chek net every 5 seconds
            mHandler = new Handler();
            continue_or_stop = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (continue_or_stop) {
                        try {
                            Thread.sleep(11000);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new NetCheck().execute();

                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();
        }

        // Chek net every 5 seconds
        mHandler = new Handler();
        continue_or_stop = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (continue_or_stop) {
                    try {
                        Thread.sleep(10000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                boolean connected = true;
                                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                                    //we are connected to a network
                                    connected = true;
                                } else{ connected = false; }

                                if (connected == false){
                                    new NetCheck().execute();
                                }

                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        }).start();





        setContentView(R.layout.activity_main);


        // tab for framgent
        tab_bottom = findViewById(R.id.include_tabBottom);
        tab_top = findViewById(R.id.include_tabTop);


        //menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem ad = menu.add("شما");
        MenuItem login = menu.findItem(R.id.MU_login);
        login.setTitle("ورود");



        // JSON
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/json_app/app.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // new version for app
                    int app_version = response.getInt("version");
                    if (versionCode < app_version) {
                        Notification.Builder nb = new Notification.Builder(MainActivity.this);
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        nb.setContentTitle("بروزرسانی")
                                .setContentText("نسخه جدید رو دانلود کنید!")
                                .setTicker("برو دانلود کن دیگه")
                                .setSmallIcon(android.R.drawable.stat_sys_download)
                                .setAutoCancel(false)
                                .setSound(alarmSound);
                        Notification notif = nb.build();
                        NotificationManager notifManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        notifManager.notify(0, notif);
                    }
                    // notif
                    boolean notif_bol = response.getBoolean("notif");
                    String notif_title = response.getString("title_notif");
                    String notif_des = response.getString("des_notif");
                    if (notif_bol == true) {
                        Notification.Builder nb = new Notification.Builder(MainActivity.this);
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        nb.setContentTitle(notif_title)
                                .setContentText(notif_des)
                                .setTicker("برو دانلود کن دیگه")
                                .setSmallIcon(android.R.drawable.stat_sys_download)
                                .setAutoCancel(false)
                                .setSound(alarmSound);
                        Notification notif = nb.build();
                        NotificationManager notifManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        notifManager.notify(1, notif);
                    }
                    // toolbar and tab Top or Bottom?
//                    is top
                    boolean tab_pos = response.getBoolean("Tab_IsTop");
                    if (tab_pos == true) {
                        tab_top.setVisibility(View.VISIBLE);
                        viewPager = findViewById(R.id.viewPager_top);
                        tabLayout = findViewById(R.id.tabLayout_top);
                        toolbars = findViewById(R.id.toolbars_top);

                        String appname = response.getString("name");
                        toolbars.setTitle(appname);

                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        setSupportActionBar(toolbars);
                        //menu
                        ActionBarDrawerToggle myToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbars, R.string.open, R.string.close);
                        drawerLayout.addDrawerListener(myToggle);
                        myToggle.syncState();

                    } else {
                        tab_bottom.setVisibility(View.VISIBLE);
                        viewPager = findViewById(R.id.viewPager_bottom);
                        tabLayout = findViewById(R.id.tabLayout_bottom);
                        toolbars = findViewById(R.id.toolbars_bottom);

                        String appname = response.getString("name");
                        toolbars.setTitle(appname);

                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        setSupportActionBar(toolbars);
                        //menu
                        ActionBarDrawerToggle myToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbars, R.string.open, R.string.close);
                        drawerLayout.addDrawerListener(myToggle);
                        myToggle.syncState();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppContoroler.getInstance().addToRequestQueue(req);
        // END JSON





    }

    //menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {

            case R.id.MU_login:
                startActivity(new Intent(MainActivity.this, Login.class));
                break;
            case R.id.MU_singup:
                startActivity(new Intent(MainActivity.this, SingUp.class));
                break;
            case R.id.MU_deviceinfo:
                startActivity(new Intent(MainActivity.this, DeviceInfo.class));
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    // back for device
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // add fragment
    private void setupViewPager(final ViewPager viewPager) {
        final Util.ViewPagerAdapter adapter = new Util.ViewPagerAdapter(getSupportFragmentManager());

        //             JSON            //
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/json_app/user_1000.json", null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                            array
                    int id_json = response.getInt("id_user");

                    if (id_json == ids) {
                        adapter.addFragment(oneFragment, "اخبار سایت");
                        adapter.addFragment(twoFragment, "تیکت ها");
                        adapter.addFragment(treeFragment, "پیام ها");
                    } else {
                        adapter.addFragment(oneFragment, "اخبار سایت");
                        startActivity(new Intent(MainActivity.this, Login.class));
                    }
                    viewPager.setAdapter(adapter);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });AppContoroler.getInstance().addToRequestQueue(req);


    }



////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Check Network
     */

    /**
     * Async Task to check whether internet connection is working.
     **/
    public class NetCheck extends AsyncTask<String,String,Boolean>
    {

        @Override
        protected void onPreExecute(){
        }
        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
        @Override
        protected Boolean doInBackground(String... args){
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean th){
            if(th == true){


                netview = findViewById(R.id.net);
                netview.setText("HI");


                if (th == true && netview == null){
                    finish();
                    startActivity(getIntent());
                }
                

            }
            else{
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout, "به اینترنت متصل شوید", Snackbar.LENGTH_INDEFINITE)
                        .setAction("تلاش مجدد", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new MainActivity.NetCheck().execute();
                                finish();
                                startActivity(getIntent());
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.setDuration(9000);
                snackbar.show();
            }
        }
    }
}
