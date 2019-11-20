package com.example.projectemailnhumlesonthach.base;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectemailnhumlesonthach.R;
import com.example.projectemailnhumlesonthach.SharedPreferencesController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public abstract class BaseActivity extends AppCompatActivity implements NetworkChangeReceiver.ReceiverCallback {

    public static BaseActivity sBaseActivity;

    private Toast mToast;
    private Dialog mProgressDialog;
    private static String token;

    private ToolTip mToolTip;
    private NetworkChangeReceiver broadcastReceiver;
    private DialogNoConnection dialogNoConnection;
    private PermissionListener permissionListener;
    private static final int PERMISSIONS_REQUEST = 2251;
    private boolean showTooltip = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBaseActivity = this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void selectEditText(EditText editText) {
        editText.requestFocusFromTouch();
        editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
        editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
        editText.setSelection(editText.getText().toString().length());
    }

    public void checkUserPermission(PermissionListener permissionListener, String[] permissions) {
        this.permissionListener = permissionListener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // >= android 6.0
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    PERMISSIONS_REQUEST);
        } else {
            if (permissionListener != null) permissionListener.OnAcceptedAllPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (!shouldShowRequestPermissionRationale(permissions[i]) && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            if (this.permissionListener != null) {
                                permissionListener.OnNeverRequestPermission();
                                return;
                            }
                        } else {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                if (this.permissionListener != null) {
                                    permissionListener.OnCancelPermission();
                                }
                                return;
                            }
                        }

                    }
                    if (this.permissionListener != null) {
                        permissionListener.OnAcceptedAllPermission();
                    }
                } else {
//                    checkUserPermission(this.permissionListener);
                }
            }
        }
    }

    /**
     * @param str
     */
    public void showToast(String str, @Nullable ToastClickListener listener) {
        if (mToast == null) {
            mToast = new Toast(this);
            mToast.setGravity(Gravity.BOTTOM, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                findViewById(R.id.container), false);
        TextView text = layout.findViewById(R.id.tvTitle);
        text.setText(str);
        layout.setOnClickListener(v -> {
            if (listener != null) listener.OnToastClicked();
        });
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(layout);
        mToast.show();

    }

    /**
     * @param str
     */
    public void showToast(String str) {
        showToast(str, null);
    }

    public void showTourGuide(View view, String keyTourGuide, String text, int gravity, @Nullable TourGuideListener listener) {
        showTourGuide(view, keyTourGuide, text, gravity, true, listener);
    }

    public static String getToken() {
        if (token == null)
            token = new SharedPreferencesController(SChoolApplication.context).getUserToken();
        return token;
    }

    public static void setToken(String toke) {
        token = toke;
    }


    public void showTourGuide(View view, String keyTourGuide, String text, int gravity, boolean isShowPointer, @Nullable TourGuideListener listener) {
        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(this);

        // Return if used to show tour guid
        if (sharedPreferencesController.getTourGuide(keyTourGuide)) return;
        if (showTooltip == false) return;
        if (mToolTip == null) mToolTip = new ToolTip()
                .setBackgroundColor(getResources().getColor(R.color.colorChart4))
                .setShadow(true)
                .setWidth(new Functions().getScreenWidth() / 2);

        // the return handler is used to manipulate the cleanup of all the tutorial elements
        TourGuide mTutorialHandler = TourGuide.init(this).with(TourGuide.Technique.CLICK);
        mTutorialHandler
                .setOverlay(new Overlay()
                        .setEnterAnimation(AnimationController.share.enterAnimation())
                        .setExitAnimation(AnimationController.share.exitAnimation())
                        .setBackgroundColor(getResources().getColor(R.color.colorTourGuideBackground))
                        .setOnClickListener(v -> {
                            mTutorialHandler.cleanUp();
                            if (listener != null) {
                                new Handler().postDelayed(listener::OnFinished, 500);
                            }
                        }));

        mToolTip.setGravity(gravity)
                .setDescription(text);

        if (isShowPointer) mTutorialHandler
                .setPointer(new Pointer().setColor(getResources().getColor(R.color.colorChart4)));
        mTutorialHandler.setToolTip(mToolTip)
                .playOn(view);

        sharedPreferencesController.saveTourGuide(keyTourGuide);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(this, R.style.AlertDialogCustom);
            //mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setContentView(R.layout.layout_progress_dialog);
            mProgressDialog.setCancelable(false);
        }
        try {
            mProgressDialog.show();

            new Handler().postDelayed(() -> hideProgressDialog(), 3000);
        } catch (Exception ignored) {
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new NetworkChangeReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            this.registerReceiver(broadcastReceiver, filter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void OnShowInfoNetWork(boolean b) {
        if (dialogNoConnection == null)
            dialogNoConnection = new DialogNoConnection(BaseActivity.this);
        if (b && !dialogNoConnection.isShowing()) dialogNoConnection.show();
        else dialogNoConnection.cancel();
    }

    public interface ToastClickListener {
        void OnToastClicked();
    }

    public interface TourGuideListener {
        void OnFinished();
    }

    public interface PermissionListener {
        void OnAcceptedAllPermission();

        void OnCancelPermission();

        void OnNeverRequestPermission();
    }
}

