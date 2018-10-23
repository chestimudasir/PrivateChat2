package com.cmk.privatechat2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    EditText emailEt;
    EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference("Users");
        emailEt = findViewById(R.id.editText_email);
        passwordEt = findViewById(R.id.editText2_password);

        Button signUpButton = findViewById(R.id.button_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creatAccount();
            }
        });
    }
    private void creatAccount(){
        final String emailString = emailEt.getText().toString();
        String passwordString = passwordEt.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        User mUser = new User(emailString, user.getUid());
                        startActivity(new Intent(RegisterActivity.this, UsersActivity.class));
                        mRef.push().setValue(mUser);
                    }else{
                        Log.d("SignUp", "Unsuccessfull");
                    }
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(RegisterActivity.this, UsersActivity.class);
            startActivity(intent);
        }
    }
}
