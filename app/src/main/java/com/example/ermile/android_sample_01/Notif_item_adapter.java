package com.example.ermile.android_sample_01;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Notif_item_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Notif_items> notif_items;
    Context context;
    private ClickListener clickListener;

    public Notif_item_adapter(List<Notif_items> notif_items, Context context, ClickListener clickListener) {
        this.notif_items = notif_items;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Notif_item_adapter.MyListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_modle,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Notif_item_adapter.MyListViewHolder h = (Notif_item_adapter.MyListViewHolder) holder;

        String id = notif_items.get(position).getTitileid();
        h.idtitle.setText(" تیکت شماره " + id + " پاسخ داده شد ");
        h.dates.setText(notif_items.get(position).getDates());
        String send_by_telegram = notif_items.get(position).getTelegram();

        if (send_by_telegram == "yes"){
            h.telegram.setVisibility(View.VISIBLE);
        }else {h.telegram.setVisibility(View.GONE);}

    }

    @Override
    public int getItemCount() {
        return notif_items.size();
    }


    class MyListViewHolder extends RecyclerView.ViewHolder {

        TextView idtitle,dates;
        LinearLayout telegram;

        public MyListViewHolder(View itemView) {
            super(itemView);
            idtitle = itemView.findViewById(R.id.notif_title);
            dates = itemView.findViewById(R.id.notif_time);
            telegram = itemView.findViewById(R.id.send_by_telegram);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    clickListener.onClick(view,getAdapterPosition());

                    String sendid = notif_items.get(getAdapterPosition()).getTitileid().toString();
                    Toast.makeText(context, sendid + "-"+ "ok", Toast.LENGTH_SHORT).show();

                    Intent post_idTooTicket = new Intent(view.getContext (), Tickets.class);
                    post_idTooTicket.putExtra("post_idTicket" , sendid);
                    view.getContext ().startActivity ( post_idTooTicket );

                }
            });


        }
    }



    public interface ClickListener {
        void onClick(View v, int pos);


    }
}
