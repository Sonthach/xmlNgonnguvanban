package com.example.projectemailnhumlesonthach;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectemailnhumlesonthach.base.Functions;

import androidx.annotation.NonNull;

public class SharedPreferencesController {
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesController(@NonNull Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(Functions.share.encodeBase64(context.getResources().getString(R.string.app_name)), Context.MODE_PRIVATE);
    }

    public void saveUserLogin(String phone, String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("phone"), Functions.share.encodeBase64(phone));
        editor.putString(Functions.share.encodeBase64("pass"), Functions.share.encodeBase64(pass));
        editor.apply();
    }

    public void saveRememberPassword(boolean remember) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Functions.share.encodeBase64("remember"), remember);
        editor.apply();
    }

    public void saveIdNotifications(String IdNotifications) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("IdNotifications"), IdNotifications);
        editor.apply();
    }

    public void saveRoleUser(String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("role"), Functions.share.encodeBase64(role));
        editor.apply();
    }

    public void saveIdTao(String idTao) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("idTao"), Functions.share.encodeBase64(idTao));
        editor.apply();
    }


    public void saveNameUserCreateNotifications(String nameCreateNotifications) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("nameCreateNotifications"), nameCreateNotifications);
        editor.apply();
    }

    public void saveSoHieuUserChildren(String soHieu) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("soHieu"), soHieu);
        editor.apply();
    }

    public void saveContentNotifications(String contentNotifications) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("contentNotifications"), contentNotifications);
        editor.apply();
    }

    public void saveTokenFCM(String tokenFCM) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("TokenFCM"), tokenFCM);
        editor.apply();
    }

    public void saveTitleNotifications(String titleNotifications) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("titleNotifications"), titleNotifications);
        editor.apply();
    }

    public void saveUserID(String id, String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("_id"), Functions.share.encodeBase64(id));
        editor.putString(Functions.share.encodeBase64("userType"), Functions.share.encodeBase64(userType));
        editor.apply();
    }

    public void saveUserToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("token"), Functions.share.encodeBase64(token));
        editor.apply();
    }

    public void saveIdGiaoVien(String idGiaoVien) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("IdGiaoVien"), Functions.share.encodeBase64(idGiaoVien));
        editor.apply();
    }

    public void saveIdChildren(String idGiaoVien) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("IdChildren"), Functions.share.encodeBase64(idGiaoVien));
        editor.apply();
    }

    public void saveTourGuide(String nameTour) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Functions.share.encodeBase64(nameTour), true);
        editor.apply();
    }

    public void saveAvatarUser(String avatarUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("hinh"), Functions.share.encodeBase64(avatarUser));
        editor.apply();
    }

    public void saveNumberPhoneUser(String Sodienthoai) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("Sodienthoai"), Functions.share.encodeBase64(Sodienthoai));
        editor.apply();
    }

    public void saveNameUser(String nameUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("tenNguoiDung"), Functions.share.encodeBase64(nameUser));
        editor.apply();
    }

    public void saveEmailUser(String nameUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("email"), Functions.share.encodeBase64(nameUser));
        editor.apply();
    }

    public void saveNameUserChildren(String nameUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("tenHocSinh"), Functions.share.encodeBase64(nameUser));
        editor.apply();
    }



    public boolean getTourGuide(String nameTour) {
        return sharedPreferences.getBoolean(Functions.share.encodeBase64(nameTour), false);
    }

    public boolean getRemember() {
        return sharedPreferences.getBoolean(Functions.share.encodeBase64("remember"), false);
    }

    public String getTokenFCM() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("TokenFCM"), ""));
    }

    public String getUserToken() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("token"), ""));
    }

    public String getIdGiaoVien() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("IdGiaoVien"), ""));
    }

    public String getIdTao() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("idTao"), ""));
    }

    public String getIdChildren() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("IdChildren"), ""));
    }

    public String getIdNotifications() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("IdNotifications"), ""));
    }

    public String getNameUserCreateNotifications() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("nameCreateNotifications"), ""));
    }

    public String getNameUserChildren() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("tenHocSinh"), ""));
    }

    public String getSoHieuUserChildren() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("soHieu"), ""));
    }

    public String getTitleNotifications() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("titleNotifications"), ""));
    }

    public String getContentNotifications() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("contentNotifications"), ""));
    }

    public String getUserPhoneLogin() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("phone"), ""));
    }

    public String getUserPassLogin() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("pass"), ""));
    }

    public String getIdUser()
    {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("_id"),""));
    }

    public String getAvaterUser()
    {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("hinh"),""));
    }

    public String getRole()
    {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("role"), ""));
    }

    public String getNameUser()
    {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("tenNguoiDung"),""));
    }

    public String getEmailUser()
    {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("email"),""));
    }

    public String getNumberPhoneUser()
    {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("Sodienthoai"),""));
    }
}
