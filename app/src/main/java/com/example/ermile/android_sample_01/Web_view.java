package com.example.ermile.android_sample_01;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Web_view extends Fragment {


    public Web_view() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View web_view_fragment = inflater.inflate(R.layout.web_view, container, false);











        final WebView webView = web_view_fragment.findViewById(R.id.web_view);
        final SwipeRefreshLayout swipe = web_view_fragment.findViewById(R.id.swipeContainer);
        //------------------------------------------------------------
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
            }});
        // JSON
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/app.json", null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
                 final String url = response.getString("web");
                 webView.loadUrl(url);

                 swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                     @Override
                     public void onRefresh() {
                         webView.loadUrl(url);
                     }
                 });

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




        return web_view_fragment;

    }


}
