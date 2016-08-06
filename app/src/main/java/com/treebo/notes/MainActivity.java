package com.treebo.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private ArrayList<NotesPojo> arrayList = new ArrayList<>();
  private HomeAdapter adapter;
  private RecyclerView recyclerView;
  private Paint p = new Paint();
  private SingletonClass singletonClass;
  private TextView createNew;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    singletonClass = SingletonClass.getInstance();

    createNew = (TextView) findViewById(R.id.create_new);
    recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
    recyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    createNew.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditDetailActivity.class);
        intent.putExtra("IS_CREATE_NEW", true);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();

    arrayList = singletonClass.getSavedList(MainActivity.this);
    adapter = new HomeAdapter(MainActivity.this, arrayList);
    recyclerView.setAdapter(adapter);
    initSwipe();

  }

  private void initSwipe() {

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
        new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

          @Override
          public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
              RecyclerView.ViewHolder target) {
            return false;
          }

          @Override
          public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {
              adapter.removeItem(position);
            } else {
              Intent intent = new Intent(MainActivity.this, EditDetailActivity.class);
              intent.putExtra("IS_CREATE_NEW", false);
              intent.putExtra("POSITION", position);
              startActivity(intent);
            }
          }

          @Override
          public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX,
              float dY, int actionState, boolean isCurrentlyActive) {

            Bitmap icon;
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

              View itemView = viewHolder.itemView;
              float height = (float) itemView.getBottom() - (float) itemView.getTop();
              float width = height / 3;

              if (dX > 0) {
                p.setColor(Color.parseColor("#388E3C"));
                RectF background =
                    new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                c.drawRect(background, p);
                icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_create_white_24dp);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width,
                    (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, p);
              } else {
                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),
                    (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, p);
                icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cancel_white_24dp);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width,
                    (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, p);
              }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
          }
        };

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);

  }
}

