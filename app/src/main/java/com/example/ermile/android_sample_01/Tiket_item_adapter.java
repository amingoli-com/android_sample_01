package com.example.ermile.android_sample_01;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tiket_item_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    List<Tiket_item> itemModles;
    Context context;
    private ClickListener clickListener;

    public Tiket_item_adapter(List<Tiket_item> itemModles, Context context, ClickListener clickListener) {
        this.itemModles = itemModles;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Tiket_item_adapter.MyListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tiket_modle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Tiket_item_adapter.MyListViewHolder h = (Tiket_item_adapter.MyListViewHolder) holder;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        h.titles.setText(itemModles.get(position).getTiket_title());
        h.answer.setText(itemModles.get(position).getTiket_answer());
        h.ends.setText(itemModles.get(position).getTiket_end());
        h.users.setText(itemModles.get(position).getTiket_user());
        h.ids.setText(itemModles.get(position).getTiket_id());
        h.times.setText(itemModles.get(position).getTiket_time());
        String time = "2018/12/15";
        if (h.times.getText() == time) {
            h.newtiket.setVisibility(View.VISIBLE);
        }else {h.newtiket.setVisibility(View.GONE);}


    }

    @Override
    public int getItemCount() {
        return itemModles.size();
    }


    class MyListViewHolder extends RecyclerView.ViewHolder {


        TextView titles,answer,ends,users,ids,times,newtiket;


        public MyListViewHolder(View itemView) {
            super(itemView);
            titles = itemView.findViewById(R.id.Xtiket_title_one);
            answer = itemView.findViewById(R.id.Xtiket_answer_one);
            ends = itemView.findViewById(R.id.Xtiket_end_one);
            users = itemView.findViewById(R.id.Xtiket_user_one);
            ids = itemView.findViewById(R.id.Xtiket_id_one);
            times = itemView.findViewById(R.id.Xtiket_time_one);
            newtiket = itemView.findViewById(R.id.newtiket);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    clickListener.onClick(view,getAdapterPosition());

                    String sendid = itemModles.get(getAdapterPosition()).getTiket_id().toString();
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
