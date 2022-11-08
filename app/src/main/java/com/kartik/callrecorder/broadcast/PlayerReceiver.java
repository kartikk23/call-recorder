package com.kartik.callrecorder.broadcast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kartik.callrecorder.Const;
import com.kartik.callrecorder.MainActivity;
import com.kartik.callrecorder.model.RecordModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PlayerReceiver {
    final WeakReference<Activity> activityref;

    public PlayerReceiver(Activity activityref) {
        this.activityref = new WeakReference<>(activityref);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int command = intent.getIntExtra(Const.COMMAND, -1);
        final MainActivity activity = (MainActivity) activityref.get();
        if (activity == null) {
            return;
        }

        switch (command) {
            case Const.PlayerService.PLAY:
                updatePlayerUI(activity, intent, true);
                break;
            case Const.PlayerService.STOP:
                updatePlayerUI(activity, intent, false);
                break;
        }
    }

    private void updatePlayerUI(MainActivity activity, Intent intent, boolean isplaying) {
        final int id = intent.getIntExtra(Const.MODEL, -1);
        final ArrayList<RecordModel> records = activity.getRecords();
        for (RecordModel recordModel : records) {
            if (recordModel.getId() == id) {
                recordModel.is_playing = isplaying;
                activity.getAdapter().notifyItemChanged(records.indexOf(recordModel));
            }
        }
    }
}