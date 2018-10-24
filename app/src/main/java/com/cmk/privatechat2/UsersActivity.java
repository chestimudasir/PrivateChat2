package com.cmk.privatechat2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity{

    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> userArrayList;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;

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
        mRef = mDb.getReference("Users");
        getAllUsers();
    }

    public void getAllUsers(){
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               User mUser = dataSnapshot.getValue(User.class);
               userArrayList.add(mUser);
               mAdapter = new UserListAdapter(userArrayList);
               mAdapter.notifyDataSetChanged();
               mRecycleView.setAdapter(mAdapter);
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
}
