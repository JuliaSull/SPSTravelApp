package com.google.sps.data;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.google.cloud.datastore.Value;

/** An item on a todo list. */
public final class UserAnswers {

  private final long id;
  private final List<String> allAnswers;

  public UserAnswers(long id, List<Value<String>> allAnswers) {
    this.id = id;
    String values = !allAnswers.isEmpty() ? allAnswers.get(0).get() : "[\"\"]";
    values = values.replaceAll("/[|]|\"","");
    List<String> allAnswersList= Arrays.asList(values.split(","));
    this.allAnswers = allAnswersList;
  }

  public UserAnswers(List<Value<String>> allAnswers) {
    this.id = 0;
    this.allAnswers = new ArrayList<String>();
    for (Value<String> answer : allAnswers) {
      this.allAnswers.add(answer.get());
    }
  }

  public List<String> getAllAnswers() {
    return this.allAnswers;
  }

  public long getId() {
      return this.id;
  }
}