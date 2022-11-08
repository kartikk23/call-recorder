package com.kartik.callrecorder.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kartik.callrecorder.Const;
import com.kartik.callrecorder.MainActivity;
import com.kartik.callrecorder.model.RecordModel;
import com.kartik.callrecorder.utils.FileUtils;
import com.kartik.callrecorder.utils.SqlUtils;

import java.lang.ref.WeakReference;

public class ViewholderReceiver extends BroadcastReceiver {
    final WeakReference<Activity> activityref;

    public ViewholderReceiver(Activity activityref) {
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
            case Const.Viewholder.DELETE:
                deleteRecord(activity,intent);
                break;
            case Const.Viewholder.FAVORITE:
                favoriteRecord(activity,intent);
                break;
            case Const.Viewholder.RENAME:
                renameRecord(activity,intent);
                break;
        }
    }
    private void deleteRecord(MainActivity activity, Intent intent) {
        final RecordModel delete_record =  intent.getParcelableExtra(Const.MODEL);
        final int delete_pos = activity.getRecords().indexOf(delete_record);
        SqlUtils.deleteRecord(delete_record);
        activity.getRecords().remove(delete_pos);
        activity.getAdapter().notifyItemRemoved(delete_pos);

        FileUtils.deleteFile(delete_record);

    }

    private void favoriteRecord(MainActivity activity, Intent intent) {
        final RecordModel update_record =  intent.getParcelableExtra(Const.MODEL);
        final int update_pos = activity.getRecords().indexOf(update_record);
        SqlUtils.saveRecord(update_record);
        activity.getAdapter().notifyItemChanged(update_pos);
    }

    private void renameRecord(MainActivity activity, Intent intent) {
        final RecordModel rename_record =  intent.getParcelableExtra(Const.MODEL);
        final int update_pos = activity.getRecords().indexOf(rename_record);
        SqlUtils.saveRecord(rename_record);
        activity.getAdapter().notifyItemChanged(update_pos);

        FileUtils.renameFile(rename_record);
    }

}
