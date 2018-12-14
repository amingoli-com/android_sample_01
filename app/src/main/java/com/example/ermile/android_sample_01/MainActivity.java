package com.example.ermile.android_sample_01;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    public Handler mHandler;
    public boolean continue_or_stop;
    Toolbar toolbars;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    ViewPager viewPager;
    TabLayout tabLayout;

    Web_view oneFragment = new Web_view();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Chek net
        new NetCheck().execute();
        // Chek net every 5 seconds
        mHandler = new Handler(); continue_or_stop = true; new Thread(new Runnable() { @Override public void run() { while (continue_or_stop) { try { Thread.sleep(5000);mHandler.post(new Runnable() {@Override public void run() { new NetCheck().execute(); }}); } catch (Exception e) {} } }}).start();
        setContentView(R.layout.activity_main);

        viewPager =  findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        toolbars = (Toolbar) findViewById(R.id.toolbars);
        toolbars.setVisibility(View.GONE);


        // JSON
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/app.json", null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int app_version = response.getInt("version");
                    if (app_version > versionCode){
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

                        Boolean toolbar = response.getBoolean("toolbar");
                        String appname = response.getString("name");
                        if (toolbar == true){
                            toolbars.setVisibility(View.VISIBLE);
                            toolbars.setTitle(appname);
                            setSupportActionBar(toolbars);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }else {
                            Toast.makeText(MainActivity.this, "نشد", Toast.LENGTH_SHORT).show();
                            toolbars.setVisibility(View.GONE);
                        }



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





    }
    private void setupViewPager(final ViewPager viewPager)
    {
        final Util.ViewPagerAdapter adapter = new Util.ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(oneFragment,"one");
        viewPager.setAdapter(adapter);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Check Network
     */
    public void ShowAlertDialog() {
        final Dialog alertDialog = new Dialog(MainActivity.this);
        alertDialog.setContentView(R.layout.net_chek);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView title_dialog = alertDialog.findViewById(R.id.title_dialog_chnet);
        title_dialog.setText("در اتصال به اینترنت مشکلی پیش آمده است!");
        title_dialog.setTextSize(18);
        Button btn_try = alertDialog.findViewById(R.id.try_dialog_chnet);
        btn_try.setText("تلاش کن");
        btn_try.setTextSize(12);
        btn_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                new MainActivity.NetCheck().execute();
            }
        });

        Button btn_cancel = alertDialog.findViewById(R.id.finish_dialog_chnet);
        btn_cancel.setText("ولش کن");
        btn_cancel.setTextSize(12);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
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
                Toast.makeText(getApplicationContext(), "دستگاه شما به اینترنت متصل شد :)", Toast.LENGTH_SHORT).show();
                // new GetData().execute();
            }
            else{
                ShowAlertDialog();
            }
        }
    }
}
