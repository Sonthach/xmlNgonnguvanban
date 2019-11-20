package com.example.projectemailnhumlesonthach.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private ReceiverCallback callback;
    private boolean isShow;

    public NetworkChangeReceiver(ReceiverCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatus(context);
        if (intent.getAction().contains("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED && !isShow) {
                Log.e("NetworkChangeReceiver", "NETWORK_STATUS_NOT_CONNECTED");
                isShow = true;
                callback.OnShowInfoNetWork(true);
            } else {
                isShow = false;
                Log.e("NetworkChangeReceiver", "NETWORK_STATUS_IS_CONNECTING ");
                callback.OnShowInfoNetWork(false);
            }
        }
    }

    public interface ReceiverCallback {
        void OnShowInfoNetWork(boolean b);
    }
}


