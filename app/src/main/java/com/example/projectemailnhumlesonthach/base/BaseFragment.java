package com.example.projectemailnhumlesonthach.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected boolean isDestroy;
    protected BaseActivity parentActivity;

    public void showToast(String str) {
        parentActivity.showToast(str);
    }

    public void showProgressDialog() {
        parentActivity.showProgressDialog();
    }

    public void hideProgressDialog() {
        parentActivity.hideProgressDialog();
    }

    public void showTourGuide(View view, String keyTourGuide, String text, int gravity, @Nullable BaseActivity.TourGuideListener listener) {
        if (isDestroy) return;
        parentActivity.showTourGuide(view, keyTourGuide, text, gravity, listener);
    }

    public void showTourGuide(View view, String keyTourGuide, String text, int gravity, boolean isShowPointer, @Nullable BaseActivity.TourGuideListener listener) {
        if (isDestroy) return;
        parentActivity.showTourGuide(view, keyTourGuide, text, gravity, isShowPointer, listener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroy = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isDestroy = false;
    }
}
