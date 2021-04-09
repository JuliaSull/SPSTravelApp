package com.google.sps.data;

/** An item on a todo list. */
public final class Destination {

  private final String name;
  private final String overallExpense;
  private final String type;

  public Destination(String name, String overallExpense, String type) {
    this.name = name;
    this.overallExpense = overallExpense;
    this.type = overallExpense;
  }
}