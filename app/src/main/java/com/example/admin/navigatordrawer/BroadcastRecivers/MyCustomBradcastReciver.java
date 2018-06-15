package com.example.admin.navigatordrawer.BroadcastRecivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.navigatordrawer.MainActivity;

public class MyCustomBradcastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Custom Broadcast Receiver", Toast.LENGTH_SHORT).show();
        Log.d("App","Custom Broadcast Receiver");
    }
}
