package com.treebo.notes;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by mohammedshaiban on 8/6/16.
 */
public class SingletonClass {

  private static final SingletonClass instance = new SingletonClass();

  public static SingletonClass getInstance() {
    return instance;
  }

  private ArrayList<NotesPojo> arrayList = new ArrayList<>();

  public ArrayList<NotesPojo> getSavedList(Context context) {

    Cursor cursor = DBHelper.getInstance(context).fetchAllNotes();
    arrayList.clear();
    if ((cursor != null) && (cursor.moveToFirst()))
      do {
        arrayList.add(new NotesPojo(cursor.getLong(cursor.getColumnIndex("notes_id")),
            cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("description"))));
      } while (cursor.moveToNext());
    if (cursor != null)
      cursor.close();

    return arrayList;
  }

  public NotesPojo getNotesItem(int position) {
    return arrayList.get(position);
  }


}
