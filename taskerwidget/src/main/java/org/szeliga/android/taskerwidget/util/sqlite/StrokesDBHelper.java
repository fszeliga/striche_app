package org.szeliga.android.taskerwidget.util.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Filip on 7/7/2016.
 */
public class StrokesDBHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_STROKES_COUNT_TABLE =
            "CREATE TABLE " + StrokesContract.Strokes.TABLE_NAME + " (" +
                    StrokesContract.Strokes.COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY," +
                    StrokesContract.Strokes.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    StrokesContract.Strokes.COLUMN_NAME_COUNT + INT_TYPE + COMMA_SEP +
                    StrokesContract.Strokes.COLUMN_NAME_CONFIRM + INT_TYPE +
                    " )";

    private static final String SQL_CREATE_STROKES_QUOTES_TABLE =
            "CREATE TABLE " + StrokesContract.StrokesQuotes.TABLE_NAME + " (" +
                    StrokesContract.StrokesQuotes.COLUMN_NAME_QUOTE_ID + " INTEGER PRIMARY KEY," +
                    StrokesContract.StrokesQuotes.COLUMN_NAME_QUOTE + TEXT_TYPE + COMMA_SEP +
                    StrokesContract.StrokesQuotes.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    StrokesContract.StrokesQuotes.COLUMN_NAME_TIME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_STROKES = "DROP TABLE IF EXISTS " + StrokesContract.Strokes.TABLE_NAME;
    private static final String SQL_DELETE_QUOTES = "DROP TABLE IF EXISTS " + StrokesContract.StrokesQuotes.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Strokes.db";

    public StrokesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STROKES_COUNT_TABLE);
        db.execSQL(SQL_CREATE_STROKES_QUOTES_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_STROKES);
        db.execSQL(SQL_DELETE_QUOTES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
