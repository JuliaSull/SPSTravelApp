package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.Value;

/** An item on a todo list. */
public final class Destination {

  private final String name;
  private final String overallExpense;
  private final String type;
  private final String language;
  private final String currency;
  private final List<Value<String>> keywords;
  private final List<Value<String>> food;

  public Destination(String name, String overallExpense, String type, String language, String currency, List<Value<String>> keywords, List<Value<String>> food) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.language = language;
    this.currency = currency;
    this.food = food;
    this.keywords = keywords;
  }

  public Destination(String name, String overallExpense, String type, String language, String currency, List<Value<String>> food) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.language = language;
    this.currency = currency;
    this.food = food;
    this.keywords = new ArrayList<>();
  }

  public Destination(String name, String overallExpense, String type, String language, String currency) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.language = language;
    this.currency = currency;
    this.food = new ArrayList<>();
    this.keywords = new ArrayList<>();
  }

  public List<Value<String>> getKeywords() {
      return this.keywords;
  }

  public List<Value<String>> getFood() {
      return this.food;
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

  public String getLanguage() {
      return this.language;
  }

  public String getCurrency() {
      return this.currency;
  }
}