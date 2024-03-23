// (C) 2024 uchicom
package com.uchicom.minwage.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.uchicom.minwage.AbstractTest;
import com.uchicom.minwage.dto.MinimumWage;
import com.uchicom.minwage.enumeration.Prefecture;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Spy;

public class MinWageCreatorTest extends AbstractTest {

  @InjectMocks @Spy MinWageCreator creator;

  @Captor ArgumentCaptor<String> amountCaptor;
  @Captor ArgumentCaptor<String> publicationDateCaptor;

  @Test
  public void createMinWage() {
    // mock
    var minimumWage = new MinimumWage();
    doReturn(minimumWage)
        .when(creator)
        .createMinimumWage(amountCaptor.capture(), publicationDateCaptor.capture());

    // test
    var result =
        creator.createMinWage(
            List.of(
                "北海道637平成14年10月1日637平成14年10月1日638平成16年10月1日641平成17年10月1日644平成18年10月1日",
                "北海道654平成19年10月19日667平成20年10月19日678平成21年10月10日691平成22年10月15日",
                "北海道705平成23年10月6日719平成24年10月18日734平成25年10月18日",
                "北海道748平成26年10月8日764平成27年10月8日",
                "北海道786平成28年10月1日",
                "北海道"));

    // assert
    var prefectureWage = result.getPrefectureWage(Prefecture.HOKKAIDO);
    assertThat(prefectureWage.prefecture).isEqualTo(Prefecture.HOKKAIDO);
    var minimumWageList = prefectureWage.minimumWageList;
    assertThat(minimumWageList).hasSize(15);
    assertThat(minimumWageList.get(0)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(1)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(2)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(3)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(4)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(5)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(6)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(7)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(8)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(9)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(10)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(11)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(12)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(13)).isEqualTo(minimumWage);
    assertThat(minimumWageList.get(14)).isEqualTo(minimumWage);

    var amounts = amountCaptor.getAllValues();
    assertThat(amounts).hasSize(15);
    assertThat(amounts.get(0)).isEqualTo("637");
    assertThat(amounts.get(1)).isEqualTo("637");
    assertThat(amounts.get(2)).isEqualTo("638");
    assertThat(amounts.get(3)).isEqualTo("641");
    assertThat(amounts.get(4)).isEqualTo("644");
    assertThat(amounts.get(5)).isEqualTo("654");
    assertThat(amounts.get(6)).isEqualTo("667");
    assertThat(amounts.get(7)).isEqualTo("678");
    assertThat(amounts.get(8)).isEqualTo("691");
    assertThat(amounts.get(9)).isEqualTo("705");
    assertThat(amounts.get(10)).isEqualTo("719");
    assertThat(amounts.get(11)).isEqualTo("734");
    assertThat(amounts.get(12)).isEqualTo("748");
    assertThat(amounts.get(13)).isEqualTo("764");
    assertThat(amounts.get(14)).isEqualTo("786");

    var publicationDates = publicationDateCaptor.getAllValues();
    assertThat(publicationDates).hasSize(15);
    assertThat(publicationDates.get(0)).isEqualTo("平成14年10月1日");
    assertThat(publicationDates.get(1)).isEqualTo("平成14年10月1日");
    assertThat(publicationDates.get(2)).isEqualTo("平成16年10月1日");
    assertThat(publicationDates.get(3)).isEqualTo("平成17年10月1日");
    assertThat(publicationDates.get(4)).isEqualTo("平成18年10月1日");
    assertThat(publicationDates.get(5)).isEqualTo("平成19年10月19日");
    assertThat(publicationDates.get(6)).isEqualTo("平成20年10月19日");
    assertThat(publicationDates.get(7)).isEqualTo("平成21年10月10日");
    assertThat(publicationDates.get(8)).isEqualTo("平成22年10月15日");
    assertThat(publicationDates.get(9)).isEqualTo("平成23年10月6日");
    assertThat(publicationDates.get(10)).isEqualTo("平成24年10月18日");
    assertThat(publicationDates.get(11)).isEqualTo("平成25年10月18日");
    assertThat(publicationDates.get(12)).isEqualTo("平成26年10月8日");
    assertThat(publicationDates.get(13)).isEqualTo("平成27年10月8日");
    assertThat(publicationDates.get(14)).isEqualTo("平成28年10月1日");
  }

  @Test
  public void createMinimumWage() {
    var result1 = creator.createMinimumWage("1000", "令和1年1月1日");
    var result2 = creator.createMinimumWage("1001", "平成31年12月31日");

    // assert
    assertThat(result1.amount).isEqualTo(1000);
    assertThat(result1.publicationDate).isEqualTo(LocalDate.of(2019, 1, 1));
    assertThat(result2.amount).isEqualTo(1001);
    assertThat(result2.publicationDate).isEqualTo(LocalDate.of(2019, 12, 31));
  }
}
