package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.Adapter.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseUser currUser;
    Toolbar toolbar ;
    Intent intent;

    TextView profileName ;
    ImageView sendBtn;
    EditText messageEditText;

    MessageAdapter messageAdapter;
    List<Chat> chatList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recycleView_message);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        profileName = findViewById(R.id.usernameTextView_toolbar_message);
        sendBtn = findViewById(R.id.sendImageView_message);
        messageEditText = findViewById(R.id.messageEditText_message);

        intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User tempUser = dataSnapshot.getValue(User.class);
                profileName.setText(tempUser.getName());
                readMessage(currUser.getUid(),tempUser.getId());
                moveRecycleViewUpward(recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgStr = messageEditText.getText().toString();
                if(!msgStr.isEmpty())
                {
                    sendMessage(currUser.getUid(),userId,msgStr);
                    messageEditText.setText("");
                    moveRecycleViewUpward(recyclerView);
                }
            }
        });


    }

    public void sendMessage(String sender, String receiver ,String msg)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("msg",msg.trim());

        reference.child("Chats").push().setValue(hashMap);
    }

    public void readMessage(final String myId, final String  hisId)
    {
        chatList = new ArrayList<Chat>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Chat tempChat = snapshot.getValue(Chat.class);
                   // Log.i("log",tempChat.getReceiver()+" "+tempChat.getSender());

                    if(tempChat.getReceiver().equals(myId) && tempChat.getSender().equals(hisId) ||
                            tempChat.getReceiver().equals(hisId) && tempChat.getSender().equals(myId) )
                    {
                        chatList.add(tempChat);
                    }


                }
                messageAdapter = new MessageAdapter(MessageActivity.this,chatList);
                recyclerView.setAdapter(messageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void moveRecycleViewUpward(final RecyclerView recyclerView1)
    {
//        if (Build.VERSION.SDK_INT >= 11) {
//            recyclerView1.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                @Override
//                public void onLayoutChange(View v,
//                                           int left, int top, int right, int bottom,
//                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    if (bottom < oldBottom) {
//                         recyclerView1.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                recyclerView1.smoothScrollToPosition(
//                                        recyclerView1.getAdapter().getItemCount()-1);
//                            }
//                        }, 100);
//                    }
//                }
//            });
//        }
    }
}
