package com.wikia.webdriver.common.templates.fandom;

import com.wikia.webdriver.common.core.configuration.Configuration;
import com.wikia.webdriver.common.core.url.FandomUrlBuilder;
import com.wikia.webdriver.common.templates.core.CoreTestTemplate;

public class FandomTestTemplate extends CoreTestTemplate {

  private String mainURL = "http://%s:%s@qa.fandom.wikia.com/";
  protected FandomUrlBuilder urlBuilder = new FandomUrlBuilder();

  @Override
  protected void prepareURLs() {

  }

  @Override
  protected void loadFirstPage() {
    driver.navigate().to(String.format(mainURL, Configuration.getCredentials().httpAuthName,
        Configuration.getCredentials().httpAuthPass));
  }
}
