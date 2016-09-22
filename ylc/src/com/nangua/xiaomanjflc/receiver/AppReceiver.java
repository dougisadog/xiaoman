package com.nangua.xiaomanjflc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nangua.xiaomanjflc.AppVariables;

public class AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            //System.out.println("屏幕已锁。");
            AppVariables.needGesture = true;
        }
    }

}
