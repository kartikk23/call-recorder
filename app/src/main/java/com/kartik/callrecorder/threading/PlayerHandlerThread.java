package com.kartik.callrecorder.threading;

public class PlayerHandlerThread extends HandlerThread {
    private Thread readBlockingThread;
    private boolean isplaying = false;
    private Handler handler;

    public PlayerHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(this.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Const.PlayerService.PLAY:
                        if(isplaying) {
                            stopTrack();
                        }
                        final String path = (String) msg.obj;
                        final int id = msg.arg1;

                        if (path != null && !path.isEmpty()) {
                            playTrack(path,id);
                        }
                        break;
                    case Const.PlayerService.STOP:
                        stopTrack();
                        break;
                }
            }
        };
    }

    private void stopTrack() {
        if(readBlockingThread!=null) {
            readBlockingThread.interrupt();
            isplaying = false;
        }

    }

    private void playTrack(String path, int id){
        readBlockingThread = new PlayBlockThread(path, id);
        readBlockingThread.start();
        isplaying = true;

    }

    public void startPlaying(String path, int id){
        handler.sendMessage(handler.obtainMessage(Const.PlayerService.PLAY,id,0,path));
    }

    public void stopPlaying(){
        handler.sendMessage(handler.obtainMessage(Const.PlayerService.STOP));
    }

    public void shutdown(){
        if(readBlockingThread!=null){
            readBlockingThread.interrupt();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            this.quitSafely();
        }else{
            this.quit();
        }

        this.interrupt();
    }

}