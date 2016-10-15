package com.toxoidandroid.friends;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FriendsDatabase extends SQLiteOpenHelper{

    public static final String LOG_TAG = FriendsDatabase.class.getSimpleName();
    public static final String DATABASE_NAME = "friends.db";
    public static final int DATABASE_VERSION = 2;
    private final Context mContext;

    public FriendsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + FriendsContract.Friends.FRIENDS + " ("
        + FriendsContract.Friends._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + FriendsContract.Friends.FRIENDS_NAME + " TEXT NOT NULL, "
        + FriendsContract.Friends.FRIENDS_PHONE + " TEXT NOT NULL, "
        + FriendsContract.Friends.FRIENDS_EMAIL + " TEXT NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        int version = oldVersion;
        if (version == 1){
            //add extra fields without deleting existing data
            version = 2;
        }
        if (version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + FriendsContract.Friends.FRIENDS);
            onCreate(db);
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

}
