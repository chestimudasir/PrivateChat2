package com.cmk.privatechat2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private ArrayList<Message> messageArrayList;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    public MessageAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message currentMessage = messageArrayList.get(position);
        holder.messageTv.setText(currentMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView messageTv;
        public MyViewHolder(View view) {
            super(view);
            messageTv = view.findViewById(R.id.message_tv);
        }
    }
}
