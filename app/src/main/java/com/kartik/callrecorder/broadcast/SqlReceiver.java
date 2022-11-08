package com.kartik.callrecorder.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kartik.callrecorder.Const;
import com.kartik.callrecorder.MainActivity;
import com.kartik.callrecorder.model.RecordModel;
import com.kartik.callrecorder.utils.SQLHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SqlReceiver extends BroadcastReceiver {
    final WeakReference<Activity> activityref;

    public SqlReceiver(Activity activityref) {
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
            case Const.SqlService.LOAD:
                updateRecordsList(activity, intent);
                break;
            case Const.SqlService.SAVE:
                addNewRecord(activity, intent);
                break;
            case Const.SqlService.DELETE:
                break;
        }

    }

    private void updateRecordsList(MainActivity activity, Intent intent) {
        final ArrayList<RecordModel> newlist = intent.getParcelableArrayListExtra(Const.MODEL);
        activity.getScrollListener().setHasMore(!(newlist.size() < SQLHelper.SELECTION_LIMIT));
        activity.getScrollListener().setLoading(false);

        final int old_size = activity.getRecords().size();
        activity.getRecords().addAll(newlist);
        final int new_size = activity.getRecords().size();

        activity.getAdapter().notifyItemRangeInserted(old_size, new_size - old_size);

        if(activity.getRecords().isEmpty()){
            activity.getNoCallsText().setVisibility(View.VISIBLE);
        }
    }

    private void addNewRecord(MainActivity activity, Intent intent) {
        activity.getRecords().add(0, (RecordModel) intent.getParcelableExtra(Const.MODEL));
        activity.getAdapter().notifyItemInserted(0);
        activity.getList().scrollToPosition(0);
    }
}