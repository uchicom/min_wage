// (C) 2024 uchicom
package com.uchicom.minwage.module;

import dagger.Module;
import dagger.Provides;
import java.net.URI;
import java.net.URISyntaxException;

@Module
public class MainModule {

  @Provides
  static URI uri() {
    try {
      return new URI("https://www.mhlw.go.jp/content/11200000/001140686.pdf");
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
