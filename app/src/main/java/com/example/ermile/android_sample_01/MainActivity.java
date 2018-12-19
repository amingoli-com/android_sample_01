package com.example.ermile.android_sample_01;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ermile.android_sample_01.network.AppContoroler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Handler mHandler;
    public boolean continue_or_stop;
    Toolbar toolbars;


    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout tab_top , tab_bottom;

    //Fragment's
    Web_view oneFragment = new Web_view();
    Support_my_Tiket twoFragment = new Support_my_Tiket();
    Support_Notif treeFragment = new Support_Notif();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Chek net
        new NetCheck().execute();


        setContentView(R.layout.activity_main);

        // tab for framgent
        tab_bottom = findViewById(R.id.include_tabBottom);
        tab_top = findViewById(R.id.include_tabTop);




        //menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        // **






        // JSON
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/app.json", null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // new version for app
                    int app_version = response.getInt("version");
                    if (versionCode < app_version){
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

                        String appname = response.getString("name");
                            toolbars.setTitle(appname);
                    }
                    // toolbar and tab Top or Bottom?
//                    is top
                    boolean tab_pos = response.getBoolean("Tab_IsTop");
                    if (tab_pos == true){
                        tab_top.setVisibility(View.VISIBLE);
                        viewPager = findViewById(R.id.viewPager_top);
                        tabLayout = findViewById(R.id.tabLayout_top);
                        toolbars = findViewById(R.id.toolbars_top);

                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        setSupportActionBar(toolbars);
                        //menu
                        ActionBarDrawerToggle myToggle = new ActionBarDrawerToggle(MainActivity.this , drawerLayout ,toolbars , R.string.open,R.string.close);
                        drawerLayout.addDrawerListener(myToggle);
                        myToggle.syncState();

                    }else {
                        tab_bottom.setVisibility(View.VISIBLE);
                        viewPager = findViewById(R.id.viewPager_bottom);
                        tabLayout = findViewById(R.id.tabLayout_bottom);
                        toolbars = findViewById(R.id.toolbars_bottom);

                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        setSupportActionBar(toolbars);
                        //menu
                        ActionBarDrawerToggle myToggle = new ActionBarDrawerToggle(MainActivity.this , drawerLayout ,toolbars , R.string.open,R.string.close);
                        drawerLayout.addDrawerListener(myToggle);
                        myToggle.syncState();
                    }

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
        // END JSON



        int ss = 1;
        if (ss == 1){
            Notification.Builder nb = new Notification.Builder(MainActivity.this);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            nb.setContentTitle("gi gi gi gim")
                    .setContentText("jooooooooooooooooon")
                    .setTicker("ayval")
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setAutoCancel(false)
                    .setSound(alarmSound);
            Notification notif = nb.build();
            NotificationManager notifManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.notify(1, notif);
        }









    }

    //menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int mId = item.getItemId();
        switch (mId){

            case R.id.mail:
                startActivity(new Intent(MainActivity.this , SingUp.class));
                break;
            case R.id.add1:
                Toast.makeText(this, "Add one", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    // back for device
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START) )
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            super.onBackPressed();
        }
    }
    // add fragment
    private void setupViewPager(final ViewPager viewPager)
    {
        final Util.ViewPagerAdapter adapter = new Util.ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(oneFragment,"اخبار سایت");
        adapter.addFragment(twoFragment,"تیکت ها");
        adapter.addFragment(treeFragment,"پیام ها");
        viewPager.setAdapter(adapter);
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
                // Chek net every 5 seconds
                mHandler = new Handler();
                continue_or_stop = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (continue_or_stop) {
                            try {
                                Thread.sleep(5000);
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
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();

                // Chek net every 5 seconds
                mHandler = new Handler();
                continue_or_stop = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (continue_or_stop) {
                            try {
                                Thread.sleep(5000);
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
        }
    }
}
