package com.example.chatapp.HomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapter.ChatAdapter;
import com.example.chatapp.Chat;
import com.example.chatapp.R;
import com.example.chatapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private ArrayList<User> userArrayList;
    private ArrayList<String> chatUserIdList;

    private FirebaseUser currUser;
    private DatabaseReference reference;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycleView_chatFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        userArrayList = new ArrayList<>();
        chatUserIdList = new ArrayList<>();


        final ArrayList<User> users = new ArrayList<>();

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatUserIdList.clear();

                for(DataSnapshot snapshot :dataSnapshot.getChildren())
                {
                    Chat tempChat = snapshot.getValue(Chat.class);

                    if(tempChat.getSender().equals(currUser.getUid()))
                    {
                       chatUserIdList.add(tempChat.getReceiver());
                    }
                    if(tempChat.getReceiver().equals(currUser.getUid()))
                    {
                        chatUserIdList.add(tempChat.getSender());
                    }

                    fetchUsers();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;
    }

    private void fetchUsers() {
    }
}
