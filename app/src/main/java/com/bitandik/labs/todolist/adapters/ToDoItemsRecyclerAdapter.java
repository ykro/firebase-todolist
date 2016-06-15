package com.bitandik.labs.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitandik.labs.todolist.R;
import com.bitandik.labs.todolist.models.ToDoItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ykro.
 */

public class ToDoItemsRecyclerAdapter extends FirebaseRecyclerAdapter<ToDoItem,
                                                      ToDoItemsRecyclerAdapter.ToDoItemViewHolder> {

  public ToDoItemsRecyclerAdapter(int modelLayout, DatabaseReference ref) {
    super(ToDoItem.class, modelLayout, ToDoItemsRecyclerAdapter.ToDoItemViewHolder.class, ref);
  }

  @Override
  public ToDoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mModelLayout, parent, false);
    return new ToDoItemViewHolder(view);
  }

  @Override
  protected void populateViewHolder(ToDoItemViewHolder holder, ToDoItem item, int position) {
    String itemDescription = item.getItem();
    String username = item.getUsername();

    holder.txtItem.setText(itemDescription);
    holder.txtUser.setText(username);

    if (item.isCompleted()) {
      holder.imgDone.setVisibility(View.VISIBLE);
    } else {
      holder.imgDone.setVisibility(View.INVISIBLE);
    }
  }

  class ToDoItemViewHolder extends RecyclerView.ViewHolder
                           implements View.OnClickListener,
                                      View.OnLongClickListener {

    @BindView(R.id.txtItem) TextView txtItem;
    @BindView(R.id.txtUser) TextView txtUser;
    @BindView(R.id.imgDone) ImageView imgDone;

    public ToDoItemViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
      ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
      int position = getAdapterPosition();
      ToDoItem currentItem = (ToDoItem)getItem(position);
      DatabaseReference reference = getRef(position);
      boolean completed = !currentItem.isCompleted();

      currentItem.setCompleted(completed);
      Map<String, Object> updates = new HashMap<String, Object>();
      updates.put("completed", completed);
      reference.updateChildren(updates);
    }

    @Override
    public boolean onLongClick(View view) {
      int position = getAdapterPosition();
      DatabaseReference reference = getRef(position);
      reference.removeValue();
      return true;
    }
  }
}

