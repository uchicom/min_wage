// (C) 2024 uchicom
package com.uchicom.minwage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.uchicom.minwage.AbstractTest;
import com.uchicom.minwage.model.MinWage;
import com.uchicom.minwage.model.WageFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class MinWageServiceTest extends AbstractTest {

  @Mock PdfService pdfService;
  @Mock WageFactory wageFactory;

  @InjectMocks @Spy MinWageService service;

  @Captor ArgumentCaptor<String> textCaptor;
  @Captor ArgumentCaptor<List<String>> cleanListCaptor;
  @Captor ArgumentCaptor<List<String>> relocationListCaptor;

  @Test
  public void getMinWage() throws Exception {
    // mock
    var text = "test1";
    doReturn(text).when(pdfService).getPdfText();
    var cleanList = List.of("test2");
    doReturn(cleanList).when(service).clean(textCaptor.capture());
    var relocationList = List.of("test3");
    doReturn(relocationList).when(service).relocation(cleanListCaptor.capture());
    var minWage = mock(MinWage.class);
    doReturn(minWage).when(wageFactory).createMinWage(relocationListCaptor.capture());

    // test
    var result = service.getMinWage();

    // assert
    assertThat(result).isEqualTo(minWage);
    assertThat(textCaptor.getValue()).isEqualTo(text);
    assertThat(cleanListCaptor.getValue()).isEqualTo(cleanList);
    assertThat(relocationListCaptor.getValue()).isEqualTo(relocationList);
  }

  @Test
  public void clean() throws Exception {
    // mock
    var text =
        """
        年
        都
        全
        改
        平
        令
        123 -
        北 海 道 1,234 令和元年1月1日
        青 森 123 令和 2年 2月 2日
        """;

    // test
    var result = service.clean(text);

    // assert
    assertThat(result).hasSize(2);
    assertThat(result.get(0)).isEqualTo("北海道1234令和1年1月1日");
    assertThat(result.get(1)).isEqualTo("青森123令和2年2月2日");
  }

  @Test
  public void relocation() throws Exception {
    // mock
    doReturn(1).when(service).getPprefectureSize();
    var list =
        List.of(
            "北海道637平成14年10月1日637平成14年10月1日638平成16年10月1日641平成17年10月1日644平成18年10月1日",
            "北海道",
            "654平成19年10月19日");

    // test
    var result = service.relocation(list);

    // assert
    assertThat(result).hasSize(2);
    assertThat(result.get(0))
        .isEqualTo("北海道637平成14年10月1日637平成14年10月1日638平成16年10月1日641平成17年10月1日644平成18年10月1日");
    assertThat(result.get(1)).isEqualTo("北海道654平成19年10月19日");
  }

  @Test
  public void getPprefectureSize() {

    // test
    var result = service.getPprefectureSize();
    // assert
    assertThat(result).isEqualTo(47);
  }
}
