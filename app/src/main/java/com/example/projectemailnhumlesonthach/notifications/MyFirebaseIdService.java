package com.example.projectemailnhumlesonthach.notifications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import androidx.annotation.NonNull;

public class MyFirebaseIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if (firebaseUser != null){
            updateToken(refreshToken);
        }
    }

    private void updateToken(String firebaseToken){
        FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(firebaseToken);
        reference.child(firebaseUser.getUid()).setValue(token);
    }
}
