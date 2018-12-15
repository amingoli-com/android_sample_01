package com.example.ermile.android_sample_01;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = inflater.inflate(R.layout.support_my__tiket, container, false);




        List<Tiket_item> tiket_modle = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recylerview_my_tiket);
        final Tiket_item_adapter adapter = new Tiket_item_adapter(tiket_modle, getContext(), new Tiket_item_adapter.ClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        tiket_modle.add(new Tiket_item("سلام مشکلم حل نشده","پاسخ داده شده","بسته","عوض زاده","3125","2018/12/15"));
        tiket_modle.add(new Tiket_item("تغییر دامنه","پاسخ داده شده","بسته","عوض زاده","3425","2018/12/15"));
        tiket_modle.add(new Tiket_item("فوری فوری","پاسخ داده شده","بسته","عوض زاده","3555","2018/12/15"));
        tiket_modle.add(new Tiket_item("خرید هاست","پاسخ داده شده","بسته","عوض زاده","3925","2018/12/14"));
        tiket_modle.add(new Tiket_item("چطور پرداخت کنم؟","پاسخ داده شده","بسته","عوض زاده","3825","2018/12/13"));
        tiket_modle.add(new Tiket_item("سلام چطوری اعتماد کنم؟","پاسخ داده شده","بسته","عوض زاده","3325","2018/12/12"));

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }

}
