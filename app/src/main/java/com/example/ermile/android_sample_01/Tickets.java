package com.example.ermile.android_sample_01;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class Tickets extends AppCompatActivity {
    CoordinatorLayout crdLayout;

    ImageView send,chosefile;
    EditText massages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);

        crdLayout = findViewById(R.id.crd_layout);

        send = findViewById(R.id.tickets_send);
        chosefile = findViewById(R.id.tickets_chosefile);
        massages = findViewById(R.id.tickets_massages);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (massages.length()>= 10) {
                    Toast.makeText(Tickets.this, "پیام شما با موفقیت ارسال شد", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Tickets.this, "متن ارسالی شما:"+massages.getText().toString(), Toast.LENGTH_LONG).show();
                }else {
                    Snackbar snackbar = Snackbar.make(crdLayout, "پیام خودرا به صورت صحیح وارد کنید!", Snackbar.LENGTH_SHORT).setDuration(1500);
                    snackbar.show();
                }
            }
        });

        chosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(crdLayout, "صبور باشید ما توسعه دهنده هستیم!", Snackbar.LENGTH_SHORT).setDuration(1500);
                snackbar.show();
            }
        });





        // get form count adn title
        Bundle getinfo_form = getIntent ().getExtras ();
        if (getinfo_form != null)
        {
            String get_idTicket="";

            if (getinfo_form.containsKey ( "post_idTicket" )){
                get_idTicket = getinfo_form.getString ( "post_idTicket" );
            }
            final String ids = get_idTicket;

            final List<item_test> item_testss = new ArrayList<>();
            final RecyclerView recyclerView = findViewById(R.id.recylerview_ticket);
            final adapter_test adapter = new adapter_test(item_testss, getApplicationContext(), new adapter_test.ClickListener() {
                @Override
                public void onClick(View v, int pos) {

                }
            });

            LinearLayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(LayoutManager);
            //             JSON            //
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/json_app/user_1000.json", null, new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    try {
//                            array
                        JSONArray home_ticket = response.getJSONArray(ids);
                        for (int i = 0; i < home_ticket.length(); i++) {
                            JSONObject obj = home_ticket.getJSONObject(i);
                            String name = obj.getString("name");
                            String des = obj.getString("des");
                            String date = obj.getString("date");
                            item_testss.add(new item_test(name,des,date));
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
        }
    }
}
