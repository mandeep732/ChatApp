package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LaunchActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button logIn, signUp;
    private ProgressBar loading;
    private FirebaseUser currUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        currUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currUser!=null)
        {

            Intent i = new Intent(LaunchActivity.this,HomeActivity.class);
            startActivity(i);
            finish();
            return;
        }

        logIn = findViewById(R.id.logInBtn_launch);
        signUp = findViewById(R.id.signUpBtn_launch);
        loading = findViewById(R.id.loading_launch);

        logIn.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);


        logIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.logInBtn_launch:
                findViewById(R.id.signUpBtn_launch).setClickable(false);
                Intent i = new Intent(LaunchActivity.this,LogInActivity.class);
                startActivity(i);

                break;
            case R.id.signUpBtn_launch:
                findViewById(R.id.logInBtn_launch).setClickable(false);
                i = new Intent(LaunchActivity.this,SignUpActivity.class);
                startActivity(i);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.logInBtn_launch).setClickable(true);
        findViewById(R.id.signUpBtn_launch).setClickable(true);
    }
}
