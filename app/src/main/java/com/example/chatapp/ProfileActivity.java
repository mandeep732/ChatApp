package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    TextView fullNameTextView, emailTextView;
    FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullNameTextView =findViewById(R.id.fullNameTextView_Profile);
        emailTextView = findViewById(R.id.emailTextView_Profile);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        emailTextView.setText(currUser.getEmail());

    }
}
