package com.kartik.callrecorder;

public class RecallerApp extends Application {
    public static final String PACKAGE = "zlyh.dmitry.recaller";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Dexter.initialize(context);


    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onTerminate() {
        SQLHelper.getInstance().close();
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        SQLHelper.getInstance().close();
        super.onLowMemory();
    }
}