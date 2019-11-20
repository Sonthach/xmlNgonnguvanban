package com.example.projectemailnhumlesonthach.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectemailnhumlesonthach.DetailEmailActivity;
import com.example.projectemailnhumlesonthach.R;
import com.example.projectemailnhumlesonthach.SendMailActivity;
import com.example.projectemailnhumlesonthach.base.ImageLoader;
import com.example.projectemailnhumlesonthach.model.Mail;
import com.example.projectemailnhumlesonthach.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReceiveMailAdapter extends RecyclerView.Adapter<ReceiveMailAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<User> users;
    private boolean isMail;
    String theLastMessage;
    String theLastTitle;
    String theLastTime;

    public ReceiveMailAdapter(Context context, ArrayList<User> users,boolean isMail) {
        this.context = context;
        this.users = users;
        this.isMail = isMail;
    }

    @NonNull
    @Override
    public ReceiveMailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_receiver, parent, false);
        return new ReceiveMailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveMailAdapter.MyViewHolder holder, int position) {
        User user = users.get(position);

        if (user.getImageURL().equals("default")){
            holder.imgChat.setImageResource(R.drawable.default_user_avatar);
        }else {
            ImageLoader.getInstance().loadImageUser(context, user.getImageURL() , holder.imgChat);
        }

        holder.txtReceiverName.setText(user.getUsername());
        if (isMail){
            lastMesssge(user.getId(),holder.txtTitle,holder.txtLastMessage,holder.txtLastTime);
        }else {
            holder.txtLastMessage.setVisibility(View.GONE);
            holder.txtTitle.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgChat;
        TextView txtReceiverName;
        TextView txtTitle;
        TextView txtLastMessage;
        TextView txtLastTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgChat = itemView.findViewById(R.id.imgChatAvatar);
            txtReceiverName = itemView.findViewById(R.id.txtReceiverName);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            txtLastTime = itemView.findViewById(R.id.txtLastTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailEmailActivity.class);
                    intent.putExtra("username",users.get(getAdapterPosition()).getUsername());
                    intent.putExtra("photoURL",users.get(getAdapterPosition()).getImageURL());
                    intent.putExtra("Receiver","Receiver");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void lastMesssge(String userid,TextView last_title,TextView last_content,TextView lastTime){
        theLastMessage = "default";
        theLastTitle = "default";
        theLastTime = "default";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mail mail = snapshot.getValue(Mail.class);
                    if (mail.getReceiver().equals(firebaseUser.getUid())){
                        theLastMessage = mail.getContent();
                        theLastTitle = mail.getTitle();
                        theLastTime = mail.getLastTime();
                    }
                }

                switch (theLastMessage){
                    case "default":
                        last_content.setText("Không có tin nhắn mail");
                        break;

                    default:
                        last_content.setText(theLastMessage);
                }

                switch (theLastTitle){
                    case "default":
                        last_title.setText("Không có tin nhắn mail");
                        break;

                    default:
                        last_title.setText(theLastTitle);
                }

                switch (theLastTime){
                    case "default":
                        lastTime.setText("Empty");
                        break;

                    default:
                        lastTime.setText(theLastTime);
                }

                theLastMessage = "default";
                theLastTitle = "default";
                theLastTime = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


