package com.example.ermile.android_sample_01;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class adapter_test extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



        List<item_test> itemModles;
        Context context;
private ClickListener clickListener;

public adapter_test(List<item_test> itemModles, Context context, ClickListener clickListener) {
        this.itemModles = itemModles;
        this.context = context;
        this.clickListener = clickListener;
        }

@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adapter_test.MyListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.modle, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
final adapter_test.MyListViewHolder h = (adapter_test.MyListViewHolder) holder;



    String users = itemModles.get(position).getUsers();
    String dess = itemModles.get(position).getdes();
    String date = itemModles.get(position).getDates();

    h.users.setText(users);
    h.dese.setText(dess);
    h.dates.setText(date);



        }

@Override
public int getItemCount() {
        return itemModles.size();
        }


class MyListViewHolder extends RecyclerView.ViewHolder {

    TextView users,dese,dates;

    public MyListViewHolder(View itemView) {
        super(itemView);

        users = itemView.findViewById(R.id.t1);
        dese = itemView.findViewById(R.id.t2);
        dates = itemView.findViewById(R.id.t3);

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
