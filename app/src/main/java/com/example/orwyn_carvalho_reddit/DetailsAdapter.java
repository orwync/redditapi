package com.example.orwyn_carvalho_reddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    public ArrayList<Details> mArrayList;
    public Context mContext;

    public DetailsAdapter(Context mContext, ArrayList<Details> mArrayList) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.commentdetails,parent,false);
        return new DetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        Details currentItem = mArrayList.get(position);

        holder.comment.setText(currentItem.getBody());
    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView comment;
        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            comment = itemView.findViewById(R.id.comment);
        }
    }
}
