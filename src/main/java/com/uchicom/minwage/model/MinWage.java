// (C) 2024 uchicom
package com.uchicom.minwage.model;

import com.uchicom.minwage.dto.MinimumWage;
import com.uchicom.minwage.enumeration.Prefecture;
import java.time.LocalDate;
import java.util.Map;

public class MinWage {
  final Map<Prefecture, PrefectureWage> prefectureWageMap;

  public MinWage(Map<Prefecture, PrefectureWage> prefectureWageMap) {
    this.prefectureWageMap = prefectureWageMap;
  }

  public PrefectureWage getPrefectureWage(Prefecture prefecture) {
    return prefectureWageMap.get(prefecture);
  }

  public MinimumWage getMinimumWage(Prefecture prefecture, LocalDate date) {
    return getPrefectureWage(prefecture).getMinumWage(date);
  }
}
