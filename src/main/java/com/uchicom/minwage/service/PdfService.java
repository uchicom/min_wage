// (C) 2024 uchicom
package com.uchicom.minwage.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import javax.inject.Inject;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfService {
  private final URI uri;

  @Inject
  public PdfService(URI uri) {
    this.uri = uri;
  }

  String getPdfText() throws IOException, InterruptedException {
    var con = uri.toURL().openConnection();
    con.setRequestProperty("User-Agent", "minwage/0.0.1");

    try (var is = con.getInputStream();
        var pdDocument = createPDDocument(is)) {
      return createPDFTextStripper().getText(pdDocument);
    }
  }

  PDFTextStripper createPDFTextStripper() {
    return new PDFTextStripper();
  }

  PDDocument createPDDocument(InputStream is) throws IOException {
    return Loader.loadPDF(is.readAllBytes());
  }
}
