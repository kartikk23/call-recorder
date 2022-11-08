package com.kartik.callrecorder.utils;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "recaller.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "calls";
    public static final int SELECTION_LIMIT = 15;

    public static final String C_ID = "_id";
    public static final String C_FILENAME = "filename";
    public static final String C_PATH = "path";
    public static final String C_DURATION = "duration_string";
    public static final String C_DATE = "date_string";
    public static final String C_START = "start";
    public static final String C_END = "end";
    public static final String C_FAVORITE = "favorite";
    public static final String C_CUSTOM_NAME = "custom_name";
    public static final String C_PHONE = "phone";
    public static final String C_INCOMING = "is_incoming";



    public static final String SQL_READ_LIMIT =
            "select * from " + TABLE_NAME + " order by "+C_START+" DESC limit ?, ? ";

    private static SQLHelper ourInstance = new SQLHelper(RecallerApp.getAppContext());

    public  static synchronized SQLHelper getInstance() {
        return ourInstance;
    }

    private SQLHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                C_FILENAME+" TEXT, "+
                C_PATH+" TEXT, "+
                C_DURATION+" TEXT, "+
                C_DATE+" TEXT," +
                C_START+" INTEGER, "+
                C_END+" INTEGER, "+
                C_FAVORITE+" INTEGER, "+
                C_CUSTOM_NAME+" TEXT, "+
                C_PHONE+" TEXT, "+
                C_INCOMING+" INTEGER" +
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }
}