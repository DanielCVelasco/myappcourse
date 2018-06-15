package com.example.admin.navigatordrawer.BroadcastRecivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.admin.navigatordrawer.MainActivity;

public class MyBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("App", "Device Boot Completed");
        Intent intent1= new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);
    }
}
