package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.R;
import com.example.chatapp.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.viewHolder> {
    private Context context;
    private ArrayList<User> arrayList;

    public ChatAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_recycle_view,parent,false);
        return new ChatAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final User user = arrayList.get(position);

        holder.fullName.setText(user.getName());
        if(user.getImageURL().equals("default"))
        {
            holder.profileImage.setImageResource(R.drawable.default_profile_image);
        }else
        {
            Glide.with(context).load(user.getImageURL()).into(holder.profileImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("userId",user.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView fullName;
        public CircleImageView profileImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.name_chat_recycleView);
            profileImage = itemView.findViewById(R.id.profileImage_chat_recycleView);
        }
    }
}
