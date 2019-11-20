package com.example.projectemailnhumlesonthach;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.projectemailnhumlesonthach.fragment.PeopleFragment;
import com.example.projectemailnhumlesonthach.fragment.ProfileFragment;
import com.example.projectemailnhumlesonthach.fragment.ReceiverFragment;
import com.example.projectemailnhumlesonthach.fragment.SendFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawer;
    FrameLayout fragment_container;
    FragmentTransaction fragmentTransaction;
    ReceiverFragment receiverFragment;
    PeopleFragment peopleFragment;
    SendFragment sendFragment;
    ProfileFragment profileFragment;
    CircleImageView user_profile;
    TextView txtName;
    TextView txtEmail;

    LinearLayout profileUser;
    LinearLayout lmReceiveremail;
    LinearLayout lnSendmail;
    LinearLayout lnPeople;
    RippleView fab;

    SharedPreferencesController sharedPreferencesController;
    DatabaseReference reference;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferencesController = new SharedPreferencesController(this);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        fragment_container = findViewById(R.id.fragment_container);
        profileUser = findViewById(R.id.profileUser);
        lmReceiveremail = findViewById(R.id.lmReceiveremail);
        lnSendmail = findViewById(R.id.lnSendmail);
        lnPeople = findViewById(R.id.lnPeople);
        fab = findViewById(R.id.fab);
        user_profile = findViewById(R.id.user_profile);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);

        fab.setVisibility(View.VISIBLE);

        txtName.setText(sharedPreferencesController.getNameUser());
        txtEmail.setText(sharedPreferencesController.getEmailUser());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_open,R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        defaultFragment();
        onClickViews();
    }

    private void defaultFragment(){
        if (receiverFragment == null) receiverFragment = new ReceiverFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, receiverFragment);
        fragmentTransaction.commit();
    }

    private void onClickViews(){
        profileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                if (profileFragment == null) profileFragment = new ProfileFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                fragmentTransaction.commit();
                drawer.closeDrawers();
            }
        });

        fab.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(MainActivity.this,SendMailActivity.class);
                startActivity(intent);
            }
        });

        lnPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.VISIBLE);
                if (peopleFragment == null) peopleFragment = new PeopleFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, peopleFragment);
                fragmentTransaction.commit();
                drawer.closeDrawers();
            }
        });

        lnSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.VISIBLE);
                if (sendFragment == null) sendFragment = new SendFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, sendFragment);
                fragmentTransaction.commit();
                drawer.closeDrawers();
            }
        });

        lmReceiveremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.VISIBLE);
                if (receiverFragment == null) receiverFragment = new ReceiverFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, receiverFragment);
                fragmentTransaction.commit();
                drawer.closeDrawers();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
