package com.gaoli.apptimes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by gaoli on 2017/10/20.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    public static final String action_boot="android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context,Intent intent){
        if(intent.getAction().equals(action_boot)){
            Toast.makeText(context,"收到开机广播",Toast.LENGTH_SHORT).show();
            Intent ootStartIntent=new Intent(context,MainActivity.class);
            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent[] ootstart={ootStartIntent};
            context.startActivities(ootstart);

        }
    }
}
