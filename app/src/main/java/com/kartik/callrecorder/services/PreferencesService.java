package com.kartik.callrecorder.services;

public class PreferencesService extends IntentService {


    public PreferencesService() {
        super("PreferencesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int command = intent.getIntExtra(Const.COMMAND,-1);

        synchronized (this) {
            final SharedPreferences preferences = RecallerApp.getAppContext().
                    getSharedPreferences(getString(R.string.shared_prefs), Context.MODE_PRIVATE);

            switch (command) {
                case Const.Prefs.CALLS_LOAD:
                    loadPreferences(preferences);
                    break;
                case Const.Prefs.FILTER_LOAD:
                    loadFilterPreferences(preferences);
                    break;
                case Const.Prefs.CALLS_SAVE:
                    savePreferences(preferences, intent.getBooleanExtra(Const.Prefs.REC_IN_OPTION, true),
                            intent.getBooleanExtra(Const.Prefs.REC_OUT_OPTION, true));
                    break;
                case Const.Prefs.FILTER_SAVE:
                    saveFilterPreferences(preferences, intent.getBooleanExtra(Const.Prefs.FAV_FILTER_OPTION, false),
                            intent.getBooleanExtra(Const.Prefs.INC_FILTER_OPTION, true),intent.getBooleanExtra(Const.Prefs.OUT_FILTER_OPTION,true));
                case Const.Prefs.NOTIF_LOAD:
                    loadNotificationPreferences(preferences);
                    break;
                case Const.Prefs.NOTIF_SAVE:
                    saveNotifiationPreferences(preferences,intent.getBooleanExtra(Const.Prefs.NOTIFICATION_OPTION, true));
                    break;
            }
        }

    }

    private void saveNotifiationPreferences(SharedPreferences preferences, boolean enabled) {
        preferences.edit().putBoolean(Const.Prefs.NOTIFICATION_OPTION,enabled).apply();
        Intent intent = new Intent(Const.Prefs.BROADCAST)
                .putExtra(Const.COMMAND,Const.Prefs.NOTIF_SAVE)
                .putExtra(Const.Prefs.NOTIFICATION_OPTION,enabled);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void loadNotificationPreferences(SharedPreferences preferences) {
        Intent intent = new Intent(Const.Prefs.BROADCAST)
                .putExtra(Const.COMMAND,Const.Prefs.NOTIF_LOAD)
                .putExtra(Const.Prefs.NOTIFICATION_OPTION, preferences.getBoolean(Const.Prefs.NOTIFICATION_OPTION, true));

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void loadFilterPreferences(SharedPreferences preferences) {
        Intent intent = new Intent(Const.Prefs.BROADCAST)
                .putExtra(Const.COMMAND,Const.Prefs.FILTER_LOAD)
                .putExtra(Const.Prefs.FAV_FILTER_OPTION, preferences.getBoolean(Const.Prefs.FAV_FILTER_OPTION, false))
                .putExtra(Const.Prefs.INC_FILTER_OPTION, preferences.getBoolean(Const.Prefs.INC_FILTER_OPTION, true))
                .putExtra(Const.Prefs.OUT_FILTER_OPTION, preferences.getBoolean(Const.Prefs.OUT_FILTER_OPTION, true));

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void saveFilterPreferences(SharedPreferences preferences, boolean fav_filter,
                                       boolean inc_filter, boolean out_filter) {
        preferences.edit().putBoolean(Const.Prefs.FAV_FILTER_OPTION,fav_filter).putBoolean(Const.Prefs.INC_FILTER_OPTION,inc_filter)
                .putBoolean(Const.Prefs.OUT_FILTER_OPTION,out_filter).apply();

        Intent intent = new Intent(Const.Prefs.BROADCAST)
                .putExtra(Const.COMMAND,Const.Prefs.FILTER_SAVE)
                .putExtra(Const.Prefs.FAV_FILTER_OPTION,fav_filter)
                .putExtra(Const.Prefs.OUT_FILTER_OPTION,out_filter)
                .putExtra(Const.Prefs.INC_FILTER_OPTION,inc_filter);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        sendBroadcast(intent);
    }

    private void savePreferences(SharedPreferences preferences, boolean in_record, boolean out_record){
        preferences.edit().putBoolean(Const.Prefs.REC_IN_OPTION,in_record).putBoolean(Const.Prefs.REC_OUT_OPTION,out_record).apply();

        Intent intent = new Intent(Const.Prefs.BROADCAST)
                .putExtra(Const.COMMAND,Const.Prefs.CALLS_SAVE)
                .putExtra(Const.Prefs.REC_IN_OPTION,in_record)
                .putExtra(Const.Prefs.REC_OUT_OPTION,out_record);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        sendBroadcast(intent);

    }

    private void loadPreferences(SharedPreferences preferences){
        Intent intent = new Intent(Const.Prefs.BROADCAST)
                .putExtra(Const.COMMAND,Const.Prefs.CALLS_LOAD)
                .putExtra(Const.Prefs.REC_IN_OPTION, preferences.getBoolean(Const.Prefs.REC_IN_OPTION, true))
                .putExtra(Const.Prefs.REC_OUT_OPTION, preferences.getBoolean(Const.Prefs.REC_OUT_OPTION, true));

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}