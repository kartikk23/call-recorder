package com.kartik.callrecorder.threading;

public class FileDeleteRunnable implements Runnable {

    private final FileService fileService;
    private final String path;

    public FileDeleteRunnable(FileService fileService, String path) {
        this.fileService = fileService;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            File f = new File(path);
            if (f.exists()) {
                boolean del = f.delete();
                Log.e("1","del " +del + " path "+path);
            }

            fileService.decrement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}