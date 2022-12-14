package com.kartik.callrecorder;

public class Const {
    public static final String COMMAND = "command";
    public static final String MODEL = "model";


    public static class Prefs {
        public static final String BROADCAST = RecallerApp.PACKAGE.concat(".PREFS_BROADCAST");

        public static final String INC_FILTER_OPTION = "incoming_filter";
        public static final String OUT_FILTER_OPTION = "outgoing_filter";
        public static final String FAV_FILTER_OPTION = "favorites_filter";
        public static final String REC_OUT_OPTION = "rec_out";
        public static final String REC_IN_OPTION = "rec_in";
        public static final String NOTIFICATION_OPTION = "notification_enabled";


        public static final int CALLS_LOAD = 0;
        public static final int CALLS_SAVE = 1;
        public static final int FILTER_SAVE = 2;
        public static final int FILTER_LOAD = 3;
        public static final int NOTIF_SAVE = 4;
        public static final int NOTIF_LOAD = 5;
    }

    public static class CallReceiver {
        public static final String TIME_MARK = "time_mark";
        public static final String PHONE_NUM = "phone_number";
        public static final String WAS_INCOMING = "is_incoming";
    }

    public static class SqlService {
        public static final String BROADCAST = RecallerApp.PACKAGE.concat(".SQL_BROADCAST");
        public static final String LOAD_CHUNK = "chunk";

        public static final int LOAD = 0;
        public static final int SAVE = 1;
        public static final int DELETE = 2;
    }

    public static class RecordService {
        public static final String BROADCAST = RecallerApp.PACKAGE.concat(".RECORD_BROADCAST");

        public static final int START = 0;
        public static final int STOP = 1;
    }

    public static class FileService {
        public static final String BROADCAST = RecallerApp.PACKAGE.concat(".FILE_BROADCAST");

        public static final String CUSTOM_FILE_NAME = "new_file_name";
        public static final String FILE_PATH = "filepath";

        public static final int DELETE = 0;
        public static final int RENAME = 1;
    }

    public static class PlayerService {
        public static final String BROADCAST = RecallerApp.PACKAGE.concat(".PLAYER_BROADCAST");

        public static final int PLAY = 0;
        public static final int STOP = 1;
    }

    public static class Viewholder {
        public static final String BROADCAST = RecallerApp.PACKAGE.concat(".VIEWHOLDER_BROADCAST");

        public static final int DELETE = 0;
        public static final int RENAME = 1;
        public static final int FAVORITE = 2;
    }
}


