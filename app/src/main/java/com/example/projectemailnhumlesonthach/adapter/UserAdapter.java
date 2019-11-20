package com.example.projectemailnhumlesonthach.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectemailnhumlesonthach.R;
import com.example.projectemailnhumlesonthach.SendMailActivity;
import com.example.projectemailnhumlesonthach.SharedPreferencesController;
import com.example.projectemailnhumlesonthach.base.ImageLoader;
import com.example.projectemailnhumlesonthach.model.User;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<User> users;
    private boolean isMail;

    public UserAdapter(Context context, ArrayList<User> users, boolean isMail) {
        this.context = context;
        this.users = users;
        this.isMail = isMail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = users.get(position);

        if (user.getImageURL().equals("default")){
            holder.imgChat.setImageResource(R.drawable.default_user_avatar);
        }else {
            ImageLoader.getInstance().loadImageUser(context, user.getImageURL() , holder.imgChat);

        }
        holder.txtReceiverName.setText(user.getUsername());

        if (isMail){
            if (user.getStatus().equals("online")){
                holder.imgIsOnline.setVisibility(View.VISIBLE);
                holder.imgIsOffline.setVisibility(View.GONE);
            }else {
                holder.imgIsOnline.setVisibility(View.GONE);
                holder.imgIsOffline.setVisibility(View.VISIBLE);
            }
        }else {
            holder.imgIsOnline.setVisibility(View.GONE);
            holder.imgIsOffline.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SendMailActivity.class);
                intent.putExtra("userid", user.getId());
                intent.putExtra("email",user.getEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgChat;
        ImageView imgIsOnline;
        ImageView imgIsOffline;
        TextView txtReceiverName;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgChat = itemView.findViewById(R.id.imgChatAvatar);
            imgIsOnline = itemView.findViewById(R.id.imgIsOnline);
            txtReceiverName = itemView.findViewById(R.id.txtReceiverName);
            imgIsOffline = itemView.findViewById(R.id.imgIsOffline);
        }
    }
}


