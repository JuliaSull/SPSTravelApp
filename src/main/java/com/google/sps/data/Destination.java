package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.Value;

/** An item on a todo list. */
public final class Destination {

  private final String name;
  private final String overallExpense;
  private final String type;
  private final List<Value<String>> keywords;

  public Destination(String name, String overallExpense, String type, List<Value<String>> keywords) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.keywords = keywords;
  }

  public Destination(String name, String overallExpense, String type) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.keywords = new ArrayList<>();
  }

  public List<Value<String>> getKeywords() {
      return this.keywords;
  }

  public String getName() {
      return this.name;
  }

  public String getOverallExpense() {
      return this.overallExpense;
  }

  public String getType() {
      return this.type;
  }
}