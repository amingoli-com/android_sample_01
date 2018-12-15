package com.example.ermile.android_sample_01;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        h.titles.setText(itemModles.get(position).getTiket_title());
        h.answer.setText(itemModles.get(position).getTiket_answer());
        h.ends.setText(itemModles.get(position).getTiket_end());
        h.users.setText(itemModles.get(position).getTiket_user());
        h.ids.setText(itemModles.get(position).getTiket_id());
        h.times.setText(itemModles.get(position).getTiket_time());


    }

    @Override
    public int getItemCount() {
        return itemModles.size();
    }


    class MyListViewHolder extends RecyclerView.ViewHolder {


        TextView titles,answer,ends,users,ids,times;


        public MyListViewHolder(View itemView) {
            super(itemView);


            titles = itemView.findViewById(R.id.Xtiket_title_one);
            answer = itemView.findViewById(R.id.Xtiket_answer_one);
            ends = itemView.findViewById(R.id.Xtiket_end_one);
            users = itemView.findViewById(R.id.Xtiket_user_one);
            ids = itemView.findViewById(R.id.Xtiket_id_one);
            times = itemView.findViewById(R.id.Xtiket_time_one);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    clickListener.onClick(view,getAdapterPosition());
//
//                    String namee = itemModles.get(getAdapterPosition()).getName().toString();
//                    String telle = itemModles.get(getAdapterPosition()).gettel().toString();
//
//                    Intent post_info_form = new Intent(view.getContext (), admin.class);
//                    post_info_form.putExtra("post_name" , namee);
//                    post_info_form.putExtra ( "post_tel" , telle );
//                    view.getContext ().startActivity ( post_info_form );
                }
            });


        }
    }

    public interface ClickListener {
        void onClick(View v, int pos);


    }
}
