package com.example.orwyn_carvalho_reddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompatExtras;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context mContext;
    private ArrayList<Posts> mPosts;
    private OnitemClickListner mListner;

    public interface OnitemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnitemClickListner listner){
        mListner = listner;
    }

    public PostAdapter(Context mContext, ArrayList<Posts> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.postview,parent,false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Posts currentItem = mPosts.get(position);

        String titletext = currentItem.getTitle();
        String picurl = currentItem.getPic();
        String postpoints = currentItem.getPoints();
        String postText = currentItem.getSelftext();

        holder.postTitle.setText(titletext);
        holder.points.setText(postpoints);
        holder.postText.setText(postText);
        Picasso.get().load(picurl).fit().centerInside().into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView postTitle;
        public ImageView postImage;
        public TextView points;
        public TextView postText;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.titletext);
            postImage = itemView.findViewById(R.id.postimage);
            points = itemView.findViewById(R.id.points);
            postText = itemView.findViewById(R.id.selftext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListner!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
