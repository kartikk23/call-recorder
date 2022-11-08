package com.kartik.callrecorder.utils;

public class PlayerUtils {
    public static void startPlayerService() {
        RecallerApp.getAppContext().startService(new Intent(RecallerApp.getAppContext(), PlayerService.class));
    }

    public static void stopPlayerService() {
        RecallerApp.getAppContext().stopService(new Intent(RecallerApp.getAppContext(), PlayerService.class));
    }

    public static void startPlayingTrack(RecordModel record){
        Intent intent = new Intent(RecallerApp.getAppContext(), PlayerService.class);
        intent.putExtra(Const.COMMAND, Const.PlayerService.PLAY).putExtra(Const.MODEL, record);
        RecallerApp.getAppContext().startService(intent);
    }

    public static void stopPlayingTrack() {
        Intent stop_play = new Intent(RecallerApp.getAppContext(), PlayerService.class);
        stop_play.putExtra(Const.COMMAND, Const.PlayerService.STOP);
        RecallerApp.getAppContext().startService(stop_play);
    }
}


