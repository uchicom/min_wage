// (C) 2024 uchicom
package com.uchicom.minwage.dto;

import java.time.LocalDate;

public class MinimumWage {
  public LocalDate publicationDate;
  public int amount;

  @Override
  public String toString() {
    return publicationDate + ":" + amount;
  }
}
