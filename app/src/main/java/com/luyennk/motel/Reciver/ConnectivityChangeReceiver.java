package com.luyennk.motel.Reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            checkConnectivity(context);
        }
    }

    private void checkConnectivity(final Context context) {
        if (!isNetworkInterfaceAvailable(context)) {
            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean isConnected = isAbleToConnect("http://www.google.com", 1000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isConnected)
                            Toast.makeText(context, "Đã kết nối", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();

    }

    //This only checks if the network interface is available, doesn't guarantee a particular network service is available, for example, there could be low signal or server downtime
    private boolean isNetworkInterfaceAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //This makes a real connection to an url and checks if you can connect to this url, this needs to be wrapped in a background thread
    private boolean isAbleToConnect(String url, int timeout) {
        try {
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
