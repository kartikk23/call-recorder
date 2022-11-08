package com.kartik.callrecorder.services;

import android.app.IntentService;

public class SqlService extends IntentService {
    public SqlService() {
        super("SqlService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int command = intent.getIntExtra(Const.COMMAND,-1);

        switch (command){
            case Const.SqlService.LOAD:
                load(intent.getIntExtra(Const.SqlService.LOAD_CHUNK,-1));
                break;
            case Const.SqlService.SAVE:
                save((RecordModel) intent.getParcelableExtra(Const.MODEL));
                break;
            case Const.SqlService.DELETE:
                delete(intent.getIntExtra(Const.MODEL, -1));

        }
    }

    private synchronized void delete(int id) {
        if(id!=-1) {
            SQLHelper.getInstance().getWritableDatabase().delete(SQLHelper.TABLE_NAME,
                    SQLHelper.C_ID+ " = ?",
                    new String[]{String.valueOf(id)});

            Intent delete_broadcast = new Intent(Const.SqlService.BROADCAST).putExtra(Const.COMMAND, Const.SqlService.DELETE);
            LocalBroadcastManager.getInstance(this).sendBroadcast(delete_broadcast);
        }
    }

    private synchronized void load(int from){
        if(from == -1){
            return;
        }

        String args[] = {String.valueOf(from),String.valueOf(SQLHelper.SELECTION_LIMIT)};
        Cursor cursor = SQLHelper.getInstance().getReadableDatabase().rawQuery(SQLHelper.SQL_READ_LIMIT,args);

        ArrayList<RecordModel> records = new ArrayList<>();

        while (cursor.moveToNext()) {
            RecordModel recordModel = new RecordModel();
            recordModel.setId(cursor.getInt(0));
            recordModel.setFile_name(cursor.getString(1));
            recordModel.setPath(cursor.getString(2));
            recordModel.setDuration(cursor.getString(3));
            recordModel.setReadable_time(cursor.getString(4));
            recordModel.setTime_start(cursor.getInt(5));
            recordModel.setTime_end(cursor.getInt(6));
            recordModel.setFavorite(cursor.getInt(7));
            recordModel.setCustom_name(cursor.getString(8));
            recordModel.setPhone(cursor.getString(9));
            recordModel.setIncoming(cursor.getInt(10));

            records.add(recordModel);
        }

        cursor.close();
        Log.e("1","records " + records.size());
        Intent model_broadcast = new Intent(Const.SqlService.BROADCAST).putExtra(Const.COMMAND,Const.SqlService.LOAD)
                .putParcelableArrayListExtra(Const.MODEL,records);
        Log.e("1","newlist " +model_broadcast.getExtras().toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(model_broadcast);


    }

    private synchronized void save(RecordModel model){
        // -1 means no id yet, new record
        if(model.getId()==-1) {
            ContentValues values = new ContentValues();
            values.put(SQLHelper.C_FILENAME,model.getFile_name());
            values.put(SQLHelper.C_PATH,model.getPath());
            values.put(SQLHelper.C_DURATION,model.getDuration());
            values.put(SQLHelper.C_DATE,model.getReadable_time());
            values.put(SQLHelper.C_START,model.getTime_start());
            values.put(SQLHelper.C_END,model.getTime_end());
            values.put(SQLHelper.C_FAVORITE,model.isFavorite());
            values.put(SQLHelper.C_CUSTOM_NAME,model.getCustom_name());
            values.put(SQLHelper.C_PHONE,model.getPhone());
            values.put(SQLHelper.C_INCOMING,model.isIncoming());

            long id = SQLHelper.getInstance().getWritableDatabase().insert(SQLHelper.TABLE_NAME,null,
                    values);
            if(id!=-1) {
                model.setId((int)id);
            }

            Intent model_broadcast = new Intent(Const.SqlService.BROADCAST).putExtra(Const.COMMAND,Const.SqlService.SAVE)
                    .putExtra(Const.MODEL,model);
            LocalBroadcastManager.getInstance(this).sendBroadcast(model_broadcast);
        }else{
            //existing record
            ContentValues values = new ContentValues();
            values.put(SQLHelper.C_FAVORITE,model.isFavorite());
            values.put(SQLHelper.C_CUSTOM_NAME,model.getCustom_name());

            SQLHelper.getInstance().getWritableDatabase().updateWithOnConflict(SQLHelper.TABLE_NAME,
                    values,
                    SQLHelper.C_ID + " = ?",
                    new String[]{String.valueOf(model.getId())},
                    SQLiteDatabase.CONFLICT_REPLACE);

        }

    }
}
