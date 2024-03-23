// (C) 2024 uchicom
package com.uchicom.minwage.model;

import com.uchicom.minwage.dto.MinimumWage;
import com.uchicom.minwage.enumeration.Prefecture;
import java.time.LocalDate;
import java.util.List;

public class PrefectureWage {
  final Prefecture prefecture;

  final List<MinimumWage> minimumWageList;

  public PrefectureWage(Prefecture prefecture, List<MinimumWage> minimumWageList) {
    this.prefecture = prefecture;
    this.minimumWageList = minimumWageList;
  }

  public MinimumWage getMinumWage(LocalDate date) {
    return minimumWageList.reversed().stream()
        .filter(minimumWage -> !date.isBefore(minimumWage.publicationDate))
        .findFirst()
        .orElseThrow();
  }
}
