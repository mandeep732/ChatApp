package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity  implements View.OnClickListener {

    private TextInputLayout email, pwd;
    private TextView forgetPwd;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Toolbar toolbar = findViewById(R.id.toolbar_logIn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailTextInputLayout_logIn);
        pwd =findViewById(R.id.pwdTextInputLayout_logIn);

        forgetPwd = findViewById(R.id.forgetPwd_logIn);
        forgetPwd.setText(Html.fromHtml("<u><b>Forget Password</b></u>"));

        forgetPwd.setOnClickListener(this);
        findViewById(R.id.logInBtn_logIn).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.logInBtn_logIn:
                clearErrorMessage();
                String emailStr = email.getEditText().getText().toString();
                String pwdStr = pwd.getEditText().getText().toString();


                if(emailStr.isEmpty())
                    email.setError("Email address required!");
                else
                if(pwdStr.isEmpty())
                    pwd.setError("Password required!");
                else
                    logIn(emailStr, pwdStr);


                break;
            case R.id.forgetPwd_logIn:
        }
    }

    public  void clearErrorMessage()
    {
        email.setError("");
        pwd.setError("");
    }

    private void logIn(String emailStr, String pwdStr) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        dialog.setCancelable(false);
        auth.signInWithEmailAndPassword(emailStr,pwdStr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent i = new Intent(LogInActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();
                        }else
                            Toast.makeText(LogInActivity.this,"Error !"+task.getException(),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
