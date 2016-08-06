package com.treebo.notes;

/**
 * Created by mohammedshaiban on 8/6/16.
 */
public class NotesPojo {

  String title;
  String description;
  long notesId;

  public NotesPojo(long notesId, String title, String description) {
    this.notesId = notesId;
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getNotesId() {
    return notesId;
  }

  public void setNotesId(long notesId) {
    this.notesId = notesId;
  }
}
