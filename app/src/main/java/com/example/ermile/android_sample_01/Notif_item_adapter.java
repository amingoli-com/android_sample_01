package com.example.ermile.android_sample_01;

import android.content.Context;
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

    }

    @Override
    public int getItemCount() {
        return notif_items.size();
    }


    class MyListViewHolder extends RecyclerView.ViewHolder {

        TextView idtitle,dates;

        public MyListViewHolder(View itemView) {
            super(itemView);
            idtitle = itemView.findViewById(R.id.notif_title);
            dates = itemView.findViewById(R.id.notif_time);



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
