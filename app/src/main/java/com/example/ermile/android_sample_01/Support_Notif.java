package com.example.ermile.android_sample_01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        List<Notif_items> notif_items = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recylerview_my_notif);
        Notif_item_adapter adapter = new Notif_item_adapter(notif_items , getContext() , new  Notif_item_adapter.ClickListener(){
            @Override
            public void onClick(View v, int pos) {

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        notif_items.add(new Notif_items("3212","2018/12/15"));
        notif_items.add(new Notif_items("4532","2018/12/14"));
        notif_items.add(new Notif_items("4523","2018/12/14"));
        notif_items.add(new Notif_items("3225","2018/12/12"));
        notif_items.add(new Notif_items("1235","2018/12/11"));


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

}
