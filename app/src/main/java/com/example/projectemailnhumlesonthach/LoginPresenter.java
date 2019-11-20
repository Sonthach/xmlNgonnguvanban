package com.example.projectemailnhumlesonthach;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.projectemailnhumlesonthach.base.Functions;
import com.example.projectemailnhumlesonthach.model.EmailData;

import androidx.annotation.NonNull;

public class LoginPresenter extends AuthPresenter {

    private LoginListener listener;

    public LoginPresenter(@NonNull LoginListener listener, Context context) {
        super(context);
        this.listener = listener;
        getRememberMe();
    }

    private void getRememberMe() {
        listener.OnRememberMe(sharedPreferencesController.getRemember(), sharedPreferencesController.getUserPhoneLogin(), sharedPreferencesController.getUserPassLogin());
    }

    public void login(EditText phone, EditText pass, CheckBox checkboxRemember){
        if (!Functions.share.isPhoneValidate(phone.getText().toString())) {
            listener.OnLoginFailed("Số điện thoại không hợp lệ !");
            return;
        }

        if (!Functions.share.isPasswordCorrectRegister(pass.getText().toString())) {
            listener.OnLoginFailed("Mật khẩu ít nhất 4 kí tự !");
            return;
        }

        if (Functions.share.isEdttEmpty(phone)) {
            listener.OnLoginFailed("Vui lòng nhập số điện thoại !");
        } else if (Functions.share.isEdttEmpty(pass)) {
            listener.OnLoginFailed("Vui lòng nhập mật khẩu !");
        }
    }

    public interface LoginListener {
        void OnLoginSuccess(EmailData apiUser);

        void OnLoginFailed(String error);

        void OnRememberMe(boolean isChecked, String phone, String pass);
    }
}
