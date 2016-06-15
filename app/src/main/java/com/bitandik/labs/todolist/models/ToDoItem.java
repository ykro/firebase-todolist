package com.bitandik.labs.todolist.models;

/**
 * Created by ykro on 9/14/15.
 */
public class ToDoItem {
  private String key;
  private String item;
  private String username;
  private boolean completed;

  public ToDoItem(){ }

  public ToDoItem(String item, String username) {
      this.username = username;
      this.item = item;
      this.completed = false;
  }

  public String getKey() {
      return key;
  }

  public void setKey(String key) {
      this.key = key;
  }

  public String getUsername() {
      return username;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public String getItem() {
      return item;
  }

  public void setItem(String item) {
      this.item = item;
  }

  public boolean isCompleted() {
      return completed;
  }

  public void setCompleted(boolean completed) {
      this.completed = completed;
  }
}
