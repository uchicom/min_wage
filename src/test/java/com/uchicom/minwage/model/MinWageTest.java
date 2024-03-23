// (C) 2024 uchicom
package com.uchicom.minwage.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.uchicom.minwage.AbstractTest;
import com.uchicom.minwage.dto.MinimumWage;
import com.uchicom.minwage.enumeration.Prefecture;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class MinWageTest extends AbstractTest {
  @Mock Map<Prefecture, PrefectureWage> prefectureWageMap;

  @InjectMocks @Spy MinWage model;

  @Captor ArgumentCaptor<Prefecture> prefectureCaptor;
  @Captor ArgumentCaptor<LocalDate> dateCaptor;

  @Test
  public void getPrefectureWage() {
    // mock
    var prefectureWage = mock(PrefectureWage.class);
    doReturn(prefectureWage).when(prefectureWageMap).get(prefectureCaptor.capture());
    var prefecture = Prefecture.HOKKAIDO;

    // test
    var result = model.getPrefectureWage(prefecture);

    // assert
    assertThat(result).isEqualTo(prefectureWage);
    assertThat(prefectureCaptor.getValue()).isEqualTo(prefecture);
  }

  @Test
  public void getMinimumWage() {
    // mock
    var minimumWage = new MinimumWage();
    var prefectureWage = mock(PrefectureWage.class);
    doReturn(minimumWage).when(prefectureWage).getMinumWage(dateCaptor.capture());
    doReturn(prefectureWage).when(model).getPrefectureWage(prefectureCaptor.capture());
    var prefecture = Prefecture.HOKKAIDO;
    var date = LocalDate.of(2024, 3, 23);

    // test
    var result = model.getMinimumWage(prefecture, date);

    // assert
    assertThat(result).isEqualTo(minimumWage);
    assertThat(dateCaptor.getValue()).isEqualTo(date);
    assertThat(prefectureCaptor.getValue()).isEqualTo(prefecture);
  }
}
