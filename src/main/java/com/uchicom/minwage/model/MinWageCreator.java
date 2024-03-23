// (C) 2024 uchicom
package com.uchicom.minwage.model;

import com.uchicom.minwage.dto.MinimumWage;
import com.uchicom.minwage.enumeration.Prefecture;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class MinWageCreator {
  Pattern pattern =
      Pattern.compile(
          "^([^0-9]+)([0-9]+)([^日]+日)([0-9]+)?([^日]+日)?([0-9]+)?([^日]+日)?([0-9]+)?([^日]+日)?([0-9]+)?([^日]+日)?$");
  DateTimeFormatter japaneseFormatter =
      DateTimeFormatter.ofPattern("GGGGy年M月d日", Locale.JAPAN)
          .withChronology(JapaneseChronology.INSTANCE)
          .withResolverStyle(ResolverStyle.LENIENT);

  @Inject
  public MinWageCreator() {}

  public MinWage createMinWage(List<String> cleanList) {

    Map<Prefecture, List<MinimumWage>> prefectureWageMap = new HashMap<>();
    for (var line : cleanList) {
      var match = pattern.matcher(line);
      if (!match.find()) {
        continue;
      }
      var prefecture = Prefecture.getByKanji(match.group(1));
      var minimumWageList = prefectureWageMap.get(prefecture);
      if (minimumWageList == null) {
        minimumWageList = new ArrayList<>(40);
        prefectureWageMap.put(prefecture, minimumWageList);
      }
      for (var i = 2; i < 12; i += 2) {
        var amount = match.group(i);
        if (amount == null) {
          break;
        }
        minimumWageList.add(createMinimumWage(amount, match.group(i + 1)));
      }
    }
    return new MinWage(
        prefectureWageMap.entrySet().stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> new PrefectureWage(entry.getKey(), entry.getValue()))));
  }

  MinimumWage createMinimumWage(String amount, String publicationDateString) {
    var minimumWage = new MinimumWage();
    minimumWage.amount = Integer.parseInt(amount);
    minimumWage.publicationDate = LocalDate.parse(publicationDateString, japaneseFormatter);
    return minimumWage;
  }
}
