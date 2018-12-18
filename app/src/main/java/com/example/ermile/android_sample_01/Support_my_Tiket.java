package com.example.ermile.android_sample_01;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Support_my_Tiket extends Fragment {


    public Support_my_Tiket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.support_my__tiket, container, false);


        FloatingActionButton add_ticket = view.findViewById(R.id.add_tiket);
        add_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });




        final List<Tiket_item> tiket_modle = new ArrayList<>();
        final RecyclerView recyclerView = view.findViewById(R.id.recylerview_my_tiket);
        final Tiket_item_adapter adapter = new Tiket_item_adapter(tiket_modle, getContext(), new Tiket_item_adapter.ClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        //             JSON            //
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/json_app/user_1000.json", null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                            array
                    JSONArray contact2Array = response.getJSONArray("tic");
                    for (int i = 0; i < contact2Array.length(); i++) {
                        JSONObject obj = contact2Array.getJSONObject(i);
                        String title = obj.getString("title");
                        String answer = obj.getString("answer");
                        String stuts = obj.getString("stuts");
                        String user_answer = obj.getString("user_answer");
                        String ticket = obj.getString("ticket");
                        String date = obj.getString("date");
                        String telegram = obj.getString("telegram");
                        tiket_modle.add(new Tiket_item(title,answer,stuts,user_answer,ticket,date));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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


        return view;
    }

}
