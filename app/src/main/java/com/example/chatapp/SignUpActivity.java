package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout email,name,pwd;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar_SignUp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailTextInputLayout_SignUp);
        pwd = findViewById(R.id.pwdTextInputLayout_SignUp);
        name = findViewById(R.id.nameTextInputLayout_SignUp);

        findViewById(R.id.signUpBtn_SignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpBtn_SignUp:

                clearErrorMessage();
                String nameStr = name.getEditText().getText().toString();
                String emailStr = email.getEditText().getText().toString();
                String pwdStr = pwd.getEditText().getText().toString();


                if(emailStr.isEmpty())
                    email.setError("Email address required!");
                else
                if(nameStr.isEmpty())
                    name.setError("Field required!");
                else
                if(pwdStr.isEmpty())
                    pwd.setError("Password required!");
                else
                registerAccount(nameStr,emailStr,pwdStr);
        }
    }

    public  void clearErrorMessage()
    {
       email.setError("");
       name.setError("");
       pwd.setError("");
    }
    private void registerAccount(final String nameStr, String emailStr, String pwdStr) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Creating account...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        dialog.setCancelable(false);

        auth.createUserWithEmailAndPassword(emailStr,pwdStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            final ProgressDialog dialog2 = new ProgressDialog(SignUpActivity.this);
                            dialog2.setMessage("Loading profile...");
                            dialog2.show();
                            dialog2.setCancelable(false);


                            FirebaseUser currUser = auth.getCurrentUser();
                            String userId = currUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                            HashMap<String,String> hash = new HashMap<>();
                            hash.put("id",userId);
                            hash.put("name",nameStr);
                            hash.put("image","default");


                            reference.setValue(hash).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {

                                        //Log.i("all done ", Calendar.getInstance().getTime().toString()+" dialog is :"+dialog.isShowing());
                                        Intent i = new Intent(SignUpActivity.this,HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                        dialog2.dismiss();
                                    }
                                }
                            });

                        }else
                        {
                            Toast.makeText(SignUpActivity.this, "Error! "+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
