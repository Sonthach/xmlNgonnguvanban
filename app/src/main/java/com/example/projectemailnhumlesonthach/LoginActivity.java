package com.example.projectemailnhumlesonthach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;

import com.andexert.library.RippleView;
import com.example.projectemailnhumlesonthach.base.BaseActivity;
import com.example.projectemailnhumlesonthach.base.Functions;
import com.example.projectemailnhumlesonthach.model.EmailData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;

public class LoginActivity extends BaseActivity implements LoginPresenter.LoginListener{
    EditText edtPhone,edtPassword;
    RippleView btnRegister,btnLogin;
    CheckBox checkboxRemember;

    SharedPreferencesController sharedPreferencesController;
    DatabaseReference reference;
    LoginPresenter presenter;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initViews();
        init();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                String txt_email = edtPhone.getText().toString();
                String txt_password = edtPassword.getText().toString();

                if (!Functions.share.isEmailValidate(txt_email)){
                    showToast("Email không đúng định dạng");
                }else if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    showToast("Vui lòng nhập đầy đủ thông tin");
                }else {
                    auth.signInWithEmailAndPassword(txt_email,txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        sharedPreferencesController.saveUserLogin(edtPhone.getText().toString(), edtPassword.getText().toString());
                                        sharedPreferencesController.saveRememberPassword(checkboxRemember.isChecked());
                                        sharedPreferencesController.saveEmailUser(txt_email);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        showToast("Email hoặc mật khẩu không đúng");
                                    }
                                }
                            });
                }
            }
        });

        btnRegister.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        auth = FirebaseAuth.getInstance();
        sharedPreferencesController = new SharedPreferencesController(this);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        checkboxRemember = findViewById(R.id.checkboxRemember);
    }

    private void init() {
        presenter = new LoginPresenter(this,this);
        edtPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (v.getId() == R.id.edtPassword && hasFocus) {
                edtPassword.setText("");
            }
        });
    }

    @Override
    public void OnLoginSuccess(EmailData apiUser) {
        hideProgressDialog();
        loginDone();
    }

    @Override
    public void OnLoginFailed(String error) {

    }

    @Override
    public void OnRememberMe(boolean isChecked, String phone, String pass) {
       edtPhone.setText(String.valueOf(""));
        edtPassword.setText(String.valueOf(""));

        if (isChecked) {
            edtPhone.setText(phone);
            edtPassword.setText(pass);
        }

        if (phone.length() > 0)
            edtPhone.setSelection(edtPhone.getText().toString().length());
    }


    private void loginDone(){
        setResult(Activity.RESULT_OK);
        finish();
    }
}
