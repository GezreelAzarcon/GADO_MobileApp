package com.alphabravo.gadoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList datentime_id, time_id, constamount_id, expenses_id, description_id;

    public MyAdapter(Context context, ArrayList datentime_id, ArrayList time_id, ArrayList constamount_id, ArrayList expenses_id, ArrayList description_id) {
        this.context = context;
        this.datentime_id = datentime_id;
        this.time_id = time_id;
        this.constamount_id = constamount_id;
        this.expenses_id = expenses_id;
        this.description_id = description_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.datentime_id.setText((String.valueOf((datentime_id.get(position)))));
        holder.time_id.setText((String.valueOf((time_id.get(position)))));
        holder.constamount_id.setText((String.valueOf((constamount_id.get(position)))));
        holder.expenses_id.setText((String.valueOf((expenses_id.get(position)))));
        holder.description_id.setText((String.valueOf((description_id.get(position)))));



    }

    @Override
    public int getItemCount() {
        return expenses_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView datentime_id, time_id, constamount_id, expenses_id, description_id ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datentime_id = itemView.findViewById(R.id.textdatentime);
            time_id = itemView.findViewById(R.id.texttime);
            constamount_id = itemView.findViewById(R.id.textconstamunt);
            expenses_id = itemView.findViewById(R.id.textexpenses);
            description_id = itemView.findViewById(R.id.textdescription);




        }
    }
}


