package com.bitandik.labs.todolist;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToDoApplication extends Application {
  private String ITEMS_CHILD_NAME = "items";
  private DatabaseReference itemsReference;
  private FirebaseAuth auth;

  @Override
  public void onCreate() {
    super.onCreate();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    database.setPersistenceEnabled(true);
    itemsReference = database.getReference(ITEMS_CHILD_NAME);
    auth = FirebaseAuth.getInstance();
  }
  //getters
  public DatabaseReference getItemsReference() {
    return itemsReference;
  }

  public FirebaseAuth getAuth() {
    return auth;
  }
}
