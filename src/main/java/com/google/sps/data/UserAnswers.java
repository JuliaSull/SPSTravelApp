package com.google.sps.data;

import java.util.List;

import com.google.cloud.datastore.Value;

/** An item on a todo list. */
public final class UserAnswers {

  private final long id;
  private final List<Value<String>> allAnswers;

  public UserAnswers(long id, List<Value<String>> allAnswers) {
    this.id = id;
    this.allAnswers = allAnswers;
  }

  public List<Value<String>>getAllAnswers() {
    return this.allAnswers;
  }

  public long getId() {
      return this.id;
  }
}