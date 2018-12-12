package com.example.ermile.android_sample_01;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public Handler mHandler;
    public boolean continue_or_stop;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Chek net
        new NetCheck().execute();
        // Chek net every 5 seconds
        mHandler = new Handler(); continue_or_stop = true; new Thread(new Runnable() { @Override public void run() { while (continue_or_stop) { try { Thread.sleep(5000);mHandler.post(new Runnable() {@Override public void run() { new NetCheck().execute(); }}); } catch (Exception e) {} } }}).start();

        setContentView(R.layout.activity_main);



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
