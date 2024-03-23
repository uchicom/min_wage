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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class PrefectureWageTest extends AbstractTest {

  @Mock Prefecture prefecture;
  @Mock List<MinimumWage> minimumWageList;

  @InjectMocks @Spy PrefectureWage model;

  @Test
  public void getMinumWage() {
    var minimumWage1 = new MinimumWage();
    minimumWage1.publicationDate = LocalDate.of(2024, 3, 24);
    var minimumWage2 = new MinimumWage();
    minimumWage2.publicationDate = LocalDate.of(2023, 3, 24);
    // mock
    doReturn(List.of(minimumWage1, minimumWage2)).when(minimumWageList).reversed();

    // test
    var result1 = model.getMinumWage(LocalDate.of(2024, 3, 23));
    var result2 = model.getMinumWage(LocalDate.of(2024, 3, 24));

    // assert
    assertThat(result1).isEqualTo(minimumWage2);
    assertThat(result2).isEqualTo(minimumWage1);
  }
}
