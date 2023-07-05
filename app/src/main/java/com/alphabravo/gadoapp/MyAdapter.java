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
    private ArrayList lifepoints_id, datentime_id;

    public MyAdapter(Context context, ArrayList lifepoints_id, ArrayList datentime_id) {
        this.context = context;
        this.lifepoints_id = lifepoints_id;
        this.datentime_id = datentime_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.lifepoints_id.setText((String.valueOf((lifepoints_id.get(position)))));
        holder.datentime_id.setText((String.valueOf((datentime_id.get(position)))));


    }

    @Override
    public int getItemCount() {
        return lifepoints_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lifepoints_id, datentime_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lifepoints_id = itemView.findViewById(R.id.textlifepoints);
            datentime_id = itemView.findViewById(R.id.textdatentime);

        }
    }
}


