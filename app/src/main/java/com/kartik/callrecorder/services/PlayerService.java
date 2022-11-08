package com.kartik.callrecorder.services;

public class PlayerService extends Service {

    private PlayerHandlerThread playerHandlerThread;

    @Override
    public void onCreate() {
        super.onCreate();
        playerHandlerThread = new PlayerHandlerThread("PlayerHandlerThread");
        playerHandlerThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = intent.getIntExtra(Const.COMMAND,-1);

        switch (command){
            case Const.PlayerService.PLAY:
                final RecordModel record = intent.getParcelableExtra(Const.MODEL);
                playerHandlerThread.startPlaying(record.getPath(),record.getId());
                break;
            case Const.PlayerService.STOP:
                playerHandlerThread.stopPlaying();
                break;

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(playerHandlerThread !=null){
            playerHandlerThread.stopPlaying();
            playerHandlerThread.shutdown();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}