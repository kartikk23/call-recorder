package com.kartik.callrecorder.utils;

public class SqlUtils {
    public static final int DESIRED_LENGTH = 15 * 1000;

    public static void deleteRecord(RecordModel record){
        Intent remove_sql_intent = new Intent(RecallerApp.getAppContext(), SqlService.class);
        remove_sql_intent.putExtra(Const.COMMAND, Const.SqlService.DELETE);
        remove_sql_intent.putExtra(Const.MODEL, record.getId());
        RecallerApp.getAppContext().startService(remove_sql_intent);
    }

    public static void saveRecord(RecordModel record){
        Intent intent = new Intent(RecallerApp.getAppContext(),SqlService.class);
        intent.putExtra(Const.COMMAND,Const.SqlService.SAVE);
        intent.putExtra(Const.MODEL,record);
        RecallerApp.getAppContext().startService(intent);
    }

    public static void removeShortRecords(ArrayList<RecordModel> records) {
        ListIterator<RecordModel> iter = records.listIterator();
        while (iter.hasNext()) {
            final RecordModel record = iter.next();
            final long duration = record.getTime_end() - record.getTime_start();

            if (duration <= DESIRED_LENGTH) {
                deleteRecord(record);
                iter.remove();
            }
        }
    }

    public static void loadRecords(int size) {
        Intent intent = new Intent(RecallerApp.getAppContext(), SqlService.class);
        intent.putExtra(Const.COMMAND, Const.SqlService.LOAD);
        intent.putExtra(Const.SqlService.LOAD_CHUNK,size);
        RecallerApp.getAppContext().startService(intent);
    }
}