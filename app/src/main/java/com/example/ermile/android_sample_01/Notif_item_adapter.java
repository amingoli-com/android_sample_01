package com.example.ermile.android_sample_01;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Notif_item_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Notif_item> itemModles
            ;
    Context context;
    private Tiket_item_adapter.ClickListener clickListener;

    public Notif_item_adapter(List<Notif_item> itemModles, Context context, Tiket_item_adapter.ClickListener clickListener) {
        this.itemModles = itemModles;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Notif_item_adapter.MyListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_modle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Tiket_item_adapter.MyListViewHolder h = (Tiket_item_adapter.MyListViewHolder) holder;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

//        h.titles.setText(itemModles.get(position).getTiket_title());
        String id = itemModles.get(position).iNotif_id;
        h.titles.setText("با احترام تیکت شماره" + id + "پاسخ داده شد");
        h.timeis.setText(itemModles.get(position).iNotif_time);



    }

    @Override
    public int getItemCount() {
        return itemModles.size();
    }


    class MyListViewHolder extends RecyclerView.ViewHolder {


        TextView titles,times,ids;
        CardView cardView;


        public MyListViewHolder(View itemView) {
            super(itemView);

            titles = itemView.findViewById(R.id.notif_title);
            times = itemView.findViewById(R.id.notif_time);
//            ids = itemView.findViewById(R.id.);




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
