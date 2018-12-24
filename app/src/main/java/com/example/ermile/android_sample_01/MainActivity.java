package com.example.ermile.android_sample_01;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // show device info
    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    private int checkedPermission = PackageManager.PERMISSION_DENIED;
    //end device info

    public Handler mHandler;
    public boolean continue_or_stop;
    Toolbar toolbars;

    // user is login and user & pass is: 519
    int ids = 519;

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


        // show device info

        checkedPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= 23 && checkedPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else
            checkedPermission = PackageManager.PERMISSION_GRANTED;

        // end show device info


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


        Menu menu = navigationView.getMenu();

        MenuItem email = menu.findItem(R.id.mail);
        MenuItem ad = menu.add("aaa");

        email.setTitle("hello");
        email.setVisible(true);


        // JSON
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/app.json", null, new Response.Listener<JSONObject>() {
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

                        String appname = response.getString("name");
                        toolbars.setTitle(appname);
                    }
                    // toolbar and tab Top or Bottom?
//                    is top
                    boolean tab_pos = response.getBoolean("Tab_IsTop");
                    if (tab_pos == true) {
                        tab_top.setVisibility(View.VISIBLE);
                        viewPager = findViewById(R.id.viewPager_top);
                        tabLayout = findViewById(R.id.tabLayout_top);
                        toolbars = findViewById(R.id.toolbars_top);

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


        int ss = 1;
        if (ss == 1) {
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
        int menuId = item.getItemId();
        switch (menuId) {

            case R.id.mail:
                startActivity(new Intent(MainActivity.this, SingUp.class));
                break;
            case R.id.star:
                startActivity(new Intent(MainActivity.this, Login.class));
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

        if (ids == 519) {
            adapter.addFragment(oneFragment, "اخبار سایت");
            adapter.addFragment(twoFragment, "تیکت ها");
            adapter.addFragment(treeFragment, "پیام ها");
        } else {
            adapter.addFragment(oneFragment, "اخبار سایت");
            startActivity(new Intent(this, Login.class));
        }
        viewPager.setAdapter(adapter);
    }

    /////////////////////////////////////////////////////////////
    private void requestPermission() {
        Toast.makeText(MainActivity.this, "Requesting permission", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE_PHONE_STATE_READ);
        }
    }


    /**
     * Method will be use to show device info
     *
     * @param v
     */
    public void showDeviceInfo(View v) {
        TelephonyManager manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(this);
        StringBuilder stringBuilder = new StringBuilder();

        if (checkedPermission != PackageManager.PERMISSION_DENIED) {
            dBuilder.setTitle("Device Info");
            // Name of underlying board like "GoldFish"
            stringBuilder.append("Board : " + Build.BOARD + "\n");
            // The consumer-visible brand with which the product/hardware will be associated, if any.
            stringBuilder.append("Brand : " + Build.BRAND + "\n");
            // The name of the industrial design.
            stringBuilder.append("DEVICE : " + Build.DEVICE + "\n");
            // A build ID string meant for displaying to the user
            stringBuilder.append("Display : " + Build.DISPLAY + "\n");
            // A string that uniquely identifies this build.
            stringBuilder.append("FINGERPRINT : " + Build.FINGERPRINT + "\n");
            // The name of the hardware
            stringBuilder.append("HARDWARE : " + Build.HARDWARE + "\n");
            // either a changelist number, or a label like "M4-rc20".
            stringBuilder.append("ID : " + Build.ID + "\n");
            // The manufacturer of the product/hardware.
            stringBuilder.append("Manufacturer : " + Build.MANUFACTURER + "\n");
            // The end-user-visible name for the end product.
            stringBuilder.append("MODEL : " + Build.MODEL + "\n");
            // A hardware serial number, if available.
            stringBuilder.append("SERIAL : " + Build.SERIAL + "\n");
            // The user-visible SDK version of the framework; its possible values are defined in Build.VERSION_CODES.
            stringBuilder.append("VERSION : " + Build.VERSION.SDK_INT + "\n");

            // Returns the phone number string for line 1, for example, the MSISDN for a GSM phone.
            // Return null if it is unavailable
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            stringBuilder.append("Line 1 : " + manager.getLine1Number() + "\n");
            // Returns the unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
            // Return null if device ID is not available.
            stringBuilder.append("Device ID/IMEI : " + manager.getDeviceId() + "\n");
            // Returns the unique subscriber ID, for example,
            // the IMSI for a GSM phone. Return null if it is unavailable.
            stringBuilder.append("IMSI : " + manager.getSubscriberId());
        } else {
            dBuilder.setTitle("Permission denied");
            stringBuilder.append("Can't access device info !");
        }
        dBuilder.setMessage(stringBuilder);
        dBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_PHONE_STATE_READ:
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ) {
                    checkedPermission = PackageManager.PERMISSION_GRANTED;
                }
                break;

        }
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
