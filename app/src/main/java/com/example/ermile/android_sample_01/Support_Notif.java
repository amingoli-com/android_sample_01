package com.example.ermile.android_sample_01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ermile.android_sample_01.network.AppContoroler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Support_Notif extends Fragment {


    public Support_Notif() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.support__notif, container, false);

        final List<Notif_items> notif_items = new ArrayList<>();
        final RecyclerView recyclerView = view.findViewById(R.id.recylerview_my_notif);
        final Notif_item_adapter adapter = new Notif_item_adapter(notif_items , getContext() , new  Notif_item_adapter.ClickListener(){
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
                        String ticket = obj.getString("ticket");
                        String date = obj.getString("date");
                        String telegram = obj.getString("telegram");
                        String title = obj.getString("title");
                        String answer = obj.getString("answer");
                        String user_answer = obj.getString("user_answer");
                        String stuts = obj.getString("stuts");
                        notif_items.add(new Notif_items(ticket,date,telegram));
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
