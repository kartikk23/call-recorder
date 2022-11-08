package com.kartik.callrecorder.threading;

public class FileRenameRunnable implements Runnable {

    private final FileService fileService;
    private final String path;
    private final String newpath;

    public FileRenameRunnable(FileService fileService, String path, String newpath) {
        this.fileService = fileService;
        this.path = path;
        this.newpath = newpath;
    }

    @Override
    public void run() {
        try {
            File f = new File(path);
            File newfile = new File(newpath);
            f.renameTo(newfile);//ignore if failed

            fileService.decrement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}