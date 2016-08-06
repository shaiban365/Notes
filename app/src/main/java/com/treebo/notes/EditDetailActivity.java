package com.treebo.notes;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mohammedshaiban on 8/6/16.
 */

public class EditDetailActivity extends AppCompatActivity {

  private int position;
  private SingletonClass singletonClass;
  private NotesPojo notesPojo;
  private EditText title, description;
  private Toolbar toolbar;
  private ImageView backPressed;
  private boolean isCreateNew;
  private TextView save;
  private InputMethodManager mImm;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_edit);

    singletonClass = SingletonClass.getInstance();
    title = (EditText) findViewById(R.id.title);
    description = (EditText) findViewById(R.id.description);
    save = (TextView) findViewById(R.id.create_new);

    Intent intent = getIntent();
    isCreateNew = intent.getBooleanExtra("IS_CREATE_NEW", false);

    if (!isCreateNew) {
      position = intent.getIntExtra("POSITION", 0);
      notesPojo = singletonClass.getNotesItem(position);
      title.setText(notesPojo.title);
      description.setText(notesPojo.description);
      save.setText("UPDATE");
    }

    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!TextUtils.isEmpty(title.getText().toString())) {
          if (isCreateNew) {
            DBHelper.getInstance(EditDetailActivity.this).insertItem(title.getText().toString(),
                description.getText().toString());
            Toast.makeText(EditDetailActivity.this, "Saved", Toast.LENGTH_SHORT).show();
          } else {
            DBHelper.getInstance(EditDetailActivity.this).updateNotes(notesPojo.notesId, title.getText().toString(),
                description.getText().toString());
            Toast.makeText(EditDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
          }
        } else {
          Toast.makeText(EditDetailActivity.this, "Title, Cannot be null", Toast.LENGTH_SHORT).show();
        }
      }
    });

    backPressed = (ImageView) findViewById(R.id.back);
    backPressed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });


  }


}
