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

        List<Notif_item> notif_items = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recylerview_my_notif);
        final Notif_item_adapter adapter = new Notif_item_adapter(tiket_modle, getContext(), new Notif_item_adapter.ClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        tiket_modle.add(new Tiket_item("سلام مشکلم حل نشده","پاسخ داده شده","بسته","عوض زاده","3125","2018/12/15"));


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

}
