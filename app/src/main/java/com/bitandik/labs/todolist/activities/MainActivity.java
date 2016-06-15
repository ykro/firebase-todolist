package com.bitandik.labs.todolist.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bitandik.labs.todolist.R;
import com.bitandik.labs.todolist.ToDoApplication;
import com.bitandik.labs.todolist.adapters.ToDoItemsRecyclerAdapter;
import com.bitandik.labs.todolist.models.ToDoItem;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.recycler_view_items) RecyclerView recyclerView;
  @BindView(R.id.editTextItem) EditText editTextItem;

  private DatabaseReference databaseReference;
  private FirebaseRecyclerAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    //setupUsername();
    SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
    String username = prefs.getString("username", null);
    setTitle(username);

    ToDoApplication app = (ToDoApplication)getApplicationContext();
    databaseReference = app.getItemsReference();
    adapter = new ToDoItemsRecyclerAdapter(R.layout.row, databaseReference);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_logout) {
      AuthUI.getInstance()
          .signOut(this)
          .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
              prefs.edit().clear().commit();

              Intent i = new Intent(MainActivity.this, LoginActivity.class);
              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                  | Intent.FLAG_ACTIVITY_NEW_TASK
                  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(i);

              finish();
            }
          });
    }

    return super.onOptionsItemSelected(item);
  }

  /*
  private void setupUsername() {
    SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
    String username = prefs.getString("username", null);
    if (username == null) {
        Random r = new Random();
        username = "AndroidUser" + r.nextInt(100000);
        prefs.edit().putString("username", username).commit();
    }
  }
  */

  @OnClick(R.id.fab)
  public void addToDoItem() {
    SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
    String username = prefs.getString("username", null);

    String itemText = editTextItem.getText().toString();
    editTextItem.setText("");

    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    if (!itemText.isEmpty()) {
        ToDoItem toDoItem = new ToDoItem(itemText.trim(), username);
        databaseReference.push().setValue(toDoItem);
    }
  }
}