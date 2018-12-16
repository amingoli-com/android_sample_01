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
public class Ticket extends Fragment {


    public Ticket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.ticket, container, false);


        List<item_test> item_testss = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recylerview_ticket);
        final adapter_test adapter = new adapter_test(item_testss, getContext(), new adapter_test.ClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("جواد عوض زاده","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));
        item_testss.add(new item_test("amin","admin","1397/05/13"));

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        return view;
    }

}
