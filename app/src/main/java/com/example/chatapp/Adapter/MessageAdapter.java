package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Chat;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT=0,MSG_TYPE_RIGHT=1;

    Context context;
    List<Chat> chatList;
    FirebaseUser currUser;

    public MessageAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_right_recycle_view,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_left_recycle_view,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
      //  holder.message.setText(chat.getMsg());
        //if(chatList.get(position).getSender().equals(currUser.getUid()))
            holder.message.setText(chat.getMsg());
      //  else
          //  holder.messageR.setText(chat.getMsg());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message,messageR,seen,time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_message_RecycleView);
            //messageR = itemView.findViewById(R.id.message_messageRight_RecycleView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(currUser.getUid()))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }
}
