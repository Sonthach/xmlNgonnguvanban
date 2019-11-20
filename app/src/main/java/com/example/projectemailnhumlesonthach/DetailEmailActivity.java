package com.example.projectemailnhumlesonthach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectemailnhumlesonthach.api.ApiService;
import com.example.projectemailnhumlesonthach.base.ImageLoader;
import com.example.projectemailnhumlesonthach.model.Mail;
import com.example.projectemailnhumlesonthach.notifications.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailEmailActivity extends AppCompatActivity {
    TextView user_name,timeCreate,tvTitle,tvContent;
    ImageView Back;
    CircleImageView user_photo;
    String theLastMessage = "default";
    String theLastTitle = "default";
    String theLastTime = "default";
    String receiver = "";
    String send = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_email);

        initViews();
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            receiver = extras.getString("Receiver");
            send = extras.getString("Send");
        }

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mail mail = snapshot.getValue(Mail.class);
                    if (receiver != null){
                        if (mail.getReceiver().equals(firebaseUser.getUid())){
                            theLastMessage = mail.getContent();
                            theLastTitle = mail.getTitle();
                            theLastTime = mail.getLastTime();
                        }
                    }else{
                        if (mail.getSender().equals(firebaseUser.getUid())){
                            theLastMessage = mail.getContent();
                            theLastTitle = mail.getTitle();
                            theLastTime = mail.getLastTime();
                        }
                    }

                }
                switch (theLastMessage){
                    case "default":
                        tvContent.setText("Không có tin nhắn mail");
                        break;

                    default:
                        tvContent.setText(theLastMessage);
                }

                switch (theLastTitle){
                    case "default":
                        tvTitle.setText("Không có tin nhắn mail");
                        break;

                    default:
                        tvTitle.setText(theLastTitle);
                }

                switch (theLastTime){
                    case "default":
                        timeCreate.setText("Empty");
                        break;

                    default:
                        timeCreate.setText(theLastTime);
                }

                theLastMessage = "default";
                theLastTitle = "default";
                theLastTime = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        user_name.setText(getIntent().getExtras().getString("username"));
        ImageLoader.getInstance().loadImageUser(getApplicationContext(), getIntent().getExtras().getString("photoURL") , user_photo);
    }

    private void initViews(){
        user_name = findViewById(R.id.user_name);
        timeCreate = findViewById(R.id.timeCreate);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        user_photo = findViewById(R.id.user_photo);
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
