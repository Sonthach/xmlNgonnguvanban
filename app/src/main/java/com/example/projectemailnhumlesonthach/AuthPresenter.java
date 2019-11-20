package com.example.projectemailnhumlesonthach;

import android.content.Context;

public class AuthPresenter {
    SharedPreferencesController sharedPreferencesController;

    public AuthPresenter(Context context){
        sharedPreferencesController = new SharedPreferencesController(context);
    }
}
