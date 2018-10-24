package com.cmk.privatechat2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Button sendButton;
    private EditText messageEditText;
    private String messageString;
    private String currentUserUid;
    private ArrayList<Message> messageArrayList;
    private String uniqueNodeName;

    //While Launching this Activity make sure to Bundle the receiver User's email and uid
    //private String receiverUserEmail;
    //private String receiverUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Intent intent = getIntent();
        String receiverUid = intent.getStringExtra("RECEIVER_UID");
        messageArrayList = new ArrayList<>();

        mDb = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            currentUserUid = mAuth.getCurrentUser().getUid();
        }else{
            Toast.makeText(this, "No User", Toast.LENGTH_LONG)
                    .show();
        }
        uniqueNodeName = receiverUid + currentUserUid;
        mRef = mDb.getReference("Messages").child(uniqueNodeName);
        mRecyclerView = findViewById(R.id.recyclerView_messages);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        sendButton = findViewById(R.id.send_button);
        messageEditText = findViewById(R.id.message_editText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageString = messageEditText.getText().toString();
                Message message = new Message(mAuth.getCurrentUser().getEmail(),messageString);
                mRef.push().setValue(message);
                messageEditText.setText("");
            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message newMessage = dataSnapshot.getValue(Message.class);
                messageArrayList.add(newMessage);
                mAdapter = new MessageAdapter(messageArrayList);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
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
                Log.d("MESSAGE","Errororor");
            }
        });
    }
}
