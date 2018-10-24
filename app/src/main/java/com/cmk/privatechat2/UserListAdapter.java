package com.cmk.privatechat2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private ArrayList<User> mUserList;

    public UserListAdapter(ArrayList<User> mUserList) {
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User currentUserItem  = mUserList.get(position);
        holder.userNameTv.setText(currentUserItem.getEmail());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView userNameTv;
        public MyViewHolder(View view) {
            super(view);
            userNameTv = view.findViewById(R.id.user_list_tv);
        }
    }
}
