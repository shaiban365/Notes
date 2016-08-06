package com.treebo.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohammedshaiban on 8/6/16.
 */
public class DBHelper extends SQLiteOpenHelper {

  private static DBHelper sInstance;
  private SQLiteDatabase mDatabase;
  private static final int DATABASE_VERSION = 105;
  private static final String DATABASE_NAME = "notes.db";

  public static final String NOTES_TABLE = "notes";
  public static final String NOTES_ID = "notes_id";
  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";


  private static final String CREATE_TABLE = "CREATE TABLE " + NOTES_TABLE + "(" + NOTES_ID
      + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + DESCRIPTION + " TEXT " + ")";

  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public static synchronized DBHelper getInstance(Context context) {
    if (sInstance == null)
      sInstance = new DBHelper(context.getApplicationContext());
    return sInstance;
  }

  private synchronized SQLiteDatabase getDatabase() {
    if (mDatabase == null)
      mDatabase = getWritableDatabase();
    return mDatabase;
  }


  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
  }

  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    database.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE);
    onCreate(database);
  }

  public Cursor fetchAllNotes() {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.query(NOTES_TABLE, null, null, null, null, null, null);
  }

  public long insertItem(String title, String desc) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(TITLE, title);
    values.put(DESCRIPTION, desc);
    return db.insert(NOTES_TABLE, null, values);
  }

  public boolean updateNotes(long notesId, String title, String desc) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(TITLE, title);
    values.put(DESCRIPTION, desc);

    if (db.update(NOTES_TABLE, values, NOTES_ID + "=" + notesId, null) > 0) {
      return true;
    } else {
      return false;
    }
  }

  public void deleteNotes(long notesId) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(NOTES_TABLE, NOTES_ID + "=" + notesId, null);
  }

}
