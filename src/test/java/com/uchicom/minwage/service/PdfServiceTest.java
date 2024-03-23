// (C) 2024 uchicom
package com.uchicom.minwage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import com.uchicom.minwage.AbstractTest;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class PdfServiceTest extends AbstractTest {

  @Mock URI uri;

  @InjectMocks @Spy PdfService service;

  @Captor ArgumentCaptor<String> keyCaptor;
  @Captor ArgumentCaptor<String> valueCaptor;
  @Captor ArgumentCaptor<InputStream> inputStreamCaptor;
  @Captor ArgumentCaptor<PDDocument> pdDocumentCaptor;
  @Captor ArgumentCaptor<byte[]> bytesCaptor;

  @Test
  public void getPdfText() throws Exception {
    // mock
    var url = mock(URL.class);
    doReturn(url).when(uri).toURL();
    var urlConnection = mock(URLConnection.class);
    doReturn(urlConnection).when(url).openConnection();
    doNothing().when(urlConnection).setRequestProperty(keyCaptor.capture(), valueCaptor.capture());
    var inputStream = mock(InputStream.class);
    doReturn(inputStream).when(urlConnection).getInputStream();

    var pdDocument = mock(PDDocument.class);
    doReturn(pdDocument).when(service).createPDDocument(inputStreamCaptor.capture());
    var pdfTextStripper = mock(PDFTextStripper.class);
    doReturn(pdfTextStripper).when(service).createPDFTextStripper();
    var pdfText = "test";
    doReturn(pdfText).when(pdfTextStripper).getText(pdDocumentCaptor.capture());

    // test
    var result = service.getPdfText();

    // assert
    assertThat(result).isEqualTo(pdfText);
    assertThat(keyCaptor.getValue()).isEqualTo("User-Agent");
    assertThat(valueCaptor.getValue()).isEqualTo("minwage/0.0.1");
    assertThat(inputStreamCaptor.getValue()).isEqualTo(inputStream);
    assertThat(pdDocumentCaptor.getValue()).isEqualTo(pdDocument);
  }

  @Test
  public void createPDFTextStripper() {
    // test
    var result = service.createPDFTextStripper();

    // assert
    assertThat(result).isNotNull();
  }

  @SuppressWarnings("resource")
  @Test
  public void createPDDocument() throws Exception {

    // mock
    try (var mocked = mockStatic(Loader.class)) {
      var pdDocument = mock(PDDocument.class);
      mocked.when(() -> Loader.loadPDF(bytesCaptor.capture())).thenReturn(pdDocument);
      var inputStream = mock(InputStream.class);
      var bytes = "test".getBytes(StandardCharsets.UTF_8);
      doReturn(bytes).when(inputStream).readAllBytes();

      // test
      var result = service.createPDDocument(inputStream);

      // assert
      assertThat(result).isNotNull();
      assertThat(bytesCaptor.getValue()).isEqualTo(bytes);
    }
  }
}
