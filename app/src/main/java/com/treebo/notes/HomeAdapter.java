package com.treebo.notes;

/**
 * Created by mohammedshaiban on 8/6/16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
  private ArrayList<NotesPojo> arrayList;
  private Context context;

  public HomeAdapter(Context context, ArrayList<NotesPojo> arrayList) {
    this.context = context;
    this.arrayList = arrayList;
  }

  @Override
  public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.title.setText("" + arrayList.get(position).title);
    holder.description.setText("" + arrayList.get(position).description);
    holder.clickView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(context, EditDetailActivity.class);
        intent.putExtra("IS_CREATE_NEW", false);
        intent.putExtra("POSITION", position);
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return (arrayList != null) ? arrayList.size() : 0;
  }

  public void removeItem(int position) {
    DBHelper.getInstance(context).deleteNotes(arrayList.get(position).notesId);
    arrayList.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, arrayList.size());
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title, description;
    FrameLayout clickView;

    public ViewHolder(View view) {
      super(view);
      title = (TextView) view.findViewById(R.id.title);
      description = (TextView) view.findViewById(R.id.description);
      clickView = (FrameLayout) view.findViewById(R.id.click_view);
    }
  }
}
