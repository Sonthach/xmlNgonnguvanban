package com.example.projectemailnhumlesonthach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.andexert.library.RippleView;
import com.example.projectemailnhumlesonthach.R;
import com.example.projectemailnhumlesonthach.api.ApiService;
import com.example.projectemailnhumlesonthach.base.BaseActivity;
import com.example.projectemailnhumlesonthach.base.Functions;
import com.example.projectemailnhumlesonthach.model.User;
import com.example.projectemailnhumlesonthach.notifications.Client;
import com.example.projectemailnhumlesonthach.notifications.Data;
import com.example.projectemailnhumlesonthach.notifications.MyResponse;
import com.example.projectemailnhumlesonthach.notifications.Sender;
import com.example.projectemailnhumlesonthach.notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SendMailActivity extends BaseActivity {
    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    EditText edtEmailTo;
    EditText edtMailFrom;
    EditText edtSubject;
    EditText edtContent;
    RippleView btnLogin;

    RelativeLayout imgBack;

    String currentTimeSendMail;
    ApiService apiService;
    String userid;
    boolean notify = false;

    SharedPreferencesController sharedPreferencesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        intent = getIntent();
        String email = intent.getStringExtra("email");
        userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        apiService = Client.getClient("https://fcm.googleapis.com/").create(ApiService.class);

        sharedPreferencesController = new SharedPreferencesController(this);

        edtMailFrom = findViewById(R.id.edtEmailFrom);
        edtEmailTo = findViewById(R.id.edtEmailTo);
        edtSubject = findViewById(R.id.edtEmailSubject);
        edtContent = findViewById(R.id.edtContent);
        btnLogin = findViewById(R.id.btnLogin);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                notify = true;
                String title = edtSubject.getText().toString();
                String content = edtContent.getText().toString();

                if (!title.equals("") && !content.equals("")){
                    sendMail(fuser.getUid(),userid,title,content);
                    showToast("Gửi mail thành công !");
                    Intent intent = new Intent(SendMailActivity.this,MainActivity.class);
                    startActivity(intent);
                }else
                    showToast("Vui lòng nhập đủ thông tin");
            }
        });

        edtEmailTo.setText(email);
        edtMailFrom.setText(sharedPreferencesController.getEmailUser());
    }

    private void sendMail(String sender,String receiver, String title, String content){
        currentTimeSendMail = Functions.share.getLastTimeMessage();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("lastTime",currentTimeSendMail);
        hashMap.put("isNew",true);
        hashMap.put("title",title);
        hashMap.put("content",content);

        reference.child("Mails").push().setValue(hashMap);

        final String msg = content;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (notify){
                    sendNotification(receiver,user.getUsername(),msg);
                }
                notify = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(String receiver, String username, String msg) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(),R.mipmap.ic_launcher,username+ ": "+msg,"Mail mới",
                            userid);

                    Sender sender = new Sender(data,token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            showToast("Lỗi");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
