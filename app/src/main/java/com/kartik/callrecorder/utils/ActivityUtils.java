package com.kartik.callrecorder.utils;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.kartik.callrecorder.Const;
import com.kartik.callrecorder.MainActivity;
import com.kartik.callrecorder.broadcast.PlayerReceiver;
import com.kartik.callrecorder.broadcast.PreferencesReceiver;
import com.kartik.callrecorder.broadcast.SqlReceiver;
import com.kartik.callrecorder.broadcast.ViewholderReceiver;

import java.util.ArrayList;

public class ActivityUtils{
    public static void registerReceivers(MainActivity activity) {
    final ArrayList<BroadcastReceiver> receivers = activity.getReceivers();
    BroadcastReceiver receiver;
    IntentFilter filter;

    receiver = new PlayerReceiver(activity);
    filter = new IntentFilter(Const.PlayerService.BROADCAST);
    receivers.add(receiver);
    LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);

    receiver = new PreferencesReceiver(activity);
    filter = new IntentFilter(Const.Prefs.BROADCAST);
    receivers.add(receiver);
    LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);

    receiver = new SqlReceiver(activity);
    filter = new IntentFilter(Const.SqlService.BROADCAST);
    receivers.add(receiver);
    LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);

    receiver = new ViewholderReceiver(activity);
    filter = new IntentFilter(Const.Viewholder.BROADCAST);
    receivers.add(receiver);
    LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);


}

        public static void unregisterReceivers(MainActivity activity){
            final ArrayList<BroadcastReceiver> receivers = activity.getReceivers();
            for(BroadcastReceiver receiver : receivers) {
                if(receiver!=null){
                    LocalBroadcastManager.getInstance(activity).unregisterReceiver(receiver);
                }
            }
        }

}