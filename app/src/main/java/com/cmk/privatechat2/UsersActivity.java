package com.cmk.privatechat2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity{

    private RecyclerView mRecycleView;
    private UserListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> userArrayList;
    private FirebaseDatabase mDb;
    private DatabaseReference mRefUsers;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mRecycleView = findViewById(R.id.recyclerView2);
        userArrayList = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        //mAdapter = new UserListAdapter(userList);


        mDb = FirebaseDatabase.getInstance();
        mRefUsers = mDb.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        getAllUsers();

    }

    public void getAllUsers(){
        mRefUsers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               User mUser = dataSnapshot.getValue(User.class);
               userArrayList.add(mUser);
               mAdapter = new UserListAdapter(userArrayList);
               mAdapter.notifyDataSetChanged();
               mRecycleView.setAdapter(mAdapter);
                startChat(mAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("user", "OIoioi error");
            }
        });
    }
    public void startChat(UserListAdapter mAdapter){
        mAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                String receiversUid = userArrayList.get(position).getUid();
                if(mAuth != null){
                    Intent intent = new Intent(UsersActivity.this, MessagingActivity.class);
                    intent.putExtra("RECEIVER_UID", receiversUid);
                    startActivity(intent);
                }

            }
        });
    }
}
