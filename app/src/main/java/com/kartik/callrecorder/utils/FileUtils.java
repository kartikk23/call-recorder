package com.kartik.callrecorder.utils;

public class FileUtils public static void renameFile(RecordModel record){
    Intent intent = new Intent(RecallerApp.getAppContext(), FileService.class);
    intent.putExtra(Const.COMMAND, Const.FileService.RENAME);
    intent.putExtra(Const.FileService.FILE_PATH, record.getPath());
    intent.putExtra(Const.FileService.CUSTOM_FILE_NAME, record.getCustom_name() );
    RecallerApp.getAppContext().startService(intent);
}

        public static void deleteFile(RecordModel record){
            Intent intent = new Intent(RecallerApp.getAppContext(), FileService.class);
            intent.putExtra(Const.COMMAND, Const.FileService.DELETE);
            intent.putExtra(Const.FileService.FILE_PATH, record.getPath());
            intent.putExtra(Const.FileService.CUSTOM_FILE_NAME, record.getCustom_name() );
            RecallerApp.getAppContext().startService(intent);
        }
}