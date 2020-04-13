package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.HomeActivity;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.R;
import com.example.chatapp.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> usersList;

    public UserAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_list_recycle_view,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user = usersList.get(position);
        holder.fullName.setText(user.getName());
        holder.emailId.setText(user.getEmail());
        if(user.getImageURL().equals("default"))
        {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }else
        {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher_round);
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
        return usersList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView fullName, emailId;
        public ImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.name_users_recycleView);
            emailId = itemView.findViewById(R.id.emailId_users_recycleView);
            profileImage = itemView.findViewById(R.id.profileImage_users_recycleView);
        }
    }
}
