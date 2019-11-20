package com.example.projectemailnhumlesonthach;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.andexert.library.RippleView;
import com.example.projectemailnhumlesonthach.base.BaseActivity;
import com.example.projectemailnhumlesonthach.base.Functions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {
    EditText txtEmail,txtPassword,txtConfirmPassword,txtDisplayName;
    RippleView rpSignUp,rpLogin,rpBack;

    SharedPreferencesController sharedPreferencesController;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        sharedPreferencesController = new SharedPreferencesController(this);
        initData();
    }

    private void initData(){
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtDisplayName = findViewById(R.id.txtDisplayName);
        rpSignUp = findViewById(R.id.rpSignUp);
        rpLogin = findViewById(R.id.rpLogin);
        rpBack = findViewById(R.id.rpBack);


        rpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = txtDisplayName.getText().toString();
                String txt_email = txtEmail.getText().toString();
                String txt_password = txtPassword.getText().toString();
                String txt_confirmpassword = txtConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    showToast("Vui lòng nhập đủ thông tin");
                }else if (!Functions.share.isPasswordCorrectRegister(txtPassword.getText().toString())){
                    showToast("Mật khẩu ít nhấu 4 kí tự");
                }else if (!Functions.share.isEmailValidate(txt_email)){
                    showToast("Email không đúng định dạng");
                }else if (!txt_confirmpassword.equals(txt_password)){
                    showToast("Xác nhận lại mật khẩu không đúng");
                }else {
                    showProgressDialog();
                    register(txt_username,txt_email,txt_password);
                    hideProgressDialog();
                }
            }
        });

        rpLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register(String username, String email,String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("status","online");
                            hashMap.put("username",username);
                            hashMap.put("email",email);
                            hashMap.put("imageURL","default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        showToast("Đăng ký thành công");
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        sharedPreferencesController.saveNameUser(username);
                                        sharedPreferencesController.saveEmailUser(email);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
