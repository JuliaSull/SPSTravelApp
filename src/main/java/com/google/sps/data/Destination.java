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
  private final List<String> keywords;
  private final List<String> food;

  public Destination(String name, String overallExpense, String type, String language, String currency, List<Value<String>> keywords, List<Value<String>> food) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.language = language;
    this.currency = currency;
    this.food = new ArrayList<>();
    for (Value<String> foodStr : food) {
      this.food.add(foodStr.get());
    }

    List<String> stringKeywords = new ArrayList<>();
    for(Value<String> k : keywords) {
        stringKeywords.add(k.get());
    }
    this.keywords = stringKeywords;
  }

  public Destination(String name, String overallExpense, String type, String language, String currency, List<Value<String>> food) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = type;
    this.language = language;
    this.currency = currency;
    this.food = new ArrayList<>();
    for (Value<String> foodStr : food) {
      this.food.add(foodStr.get());
    }
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

  public List<String> getKeywords() {
      return this.keywords;
  }

  public List<String> getFood() {
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