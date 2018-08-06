package com.example.rus.meet25project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> uriList;
    private Context context;
    CustomItemClickListener listener;

    MyAdapter(List<String> uriList, Context context, CustomItemClickListener listener) {
        this.uriList = uriList;
        this.context = context;
        this.listener = listener;
    }

    public void setAdapter(List<String> list){
        uriList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.v("tag", uriList.get(position));
        holder.bind(uriList.get(position));
        Glide.with(context).load(uriList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        String uri;
        ImageView imageView;
        MyViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,uri);
                }
            });
        }
        private void bind(String uri){
            this.uri = uri;
        }
    }
}
