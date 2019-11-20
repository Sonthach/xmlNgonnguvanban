package com.example.projectemailnhumlesonthach.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectemailnhumlesonthach.R;
import com.example.projectemailnhumlesonthach.adapter.ReceiveMailAdapter;
import com.example.projectemailnhumlesonthach.adapter.SendMailAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {
    private RecyclerView lvChats;
    private SendMailAdapter adapter;
    private ArrayList<String> usersList;
    private ArrayList<User> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;


    public SendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receiver, container, false);

        lvChats = view.findViewById(R.id.lvChats);
        lvChats.setHasFixedSize(true);
        lvChats.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Mails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mail chat = snapshot.getValue(Mail.class);
                    if (chat.getSender().equals(fuser.getUid())){
                        usersList.add(chat.getReceiver());
                    }
                    /*if (chat.getReceiver().equals(fuser.getUid())){
                        usersList.add(chat.getSender());
                    }*/
                }

                readMails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void readMails() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    for (String id : usersList){
                        if (user.getId().equals(id)){
                            if (mUsers.size() > 0){
                                for (User user1 : mUsers){
                                    if (!user.getId().equals(user1.getId())){
                                        mUsers.add(user);
                                    }
                                }
                            }else {
                                mUsers.add(user);
                            }
                        }
                    }
                }

                adapter = new SendMailAdapter(getContext(),mUsers,true);
                lvChats.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

