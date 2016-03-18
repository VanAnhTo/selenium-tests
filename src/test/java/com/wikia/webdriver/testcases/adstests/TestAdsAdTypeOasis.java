package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.dataprovider.ads.AdTypeDataProvider;
import com.wikia.webdriver.common.templates.TemplateNoFirstLoad;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.mobile.MobileAdsBaseObject;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsBaseObject;

public class TestAdsAdTypeOasis extends TemplateNoFirstLoad {

  private static final String SRC = "gpt";

  @Test(
      groups = {"TestAdsAdTypeAsync_imageAdOasis", "TestAdType_Oasis"},
      dataProviderClass = AdTypeDataProvider.class,
      dataProvider = "asyncSuccessWithAdOasis"
  )
  public void adsAdTypeAsync_imageAdOasis(String wikiName, String article, String adUnit,
                                               String slotName, String imgUrl) {
    String testedPage = urlBuilder.getUrlForPath(wikiName, article);
    AdsBaseObject ads = new AdsBaseObject(driver, testedPage);
    ads.waitForSlot(slotName);
    ads.verifyGptIframe(adUnit, slotName, SRC);
    ads.verifyImgAdLoadedInSlot(slotName, imgUrl);
  }

  @Test(
      groups = {"TestAdTypeAsync_002_Oasis", "TestAdType_Oasis"},
      dataProviderClass = AdTypeDataProvider.class,
      dataProvider = "asyncHopNoAdOasis"
  )
  public void TestAdTypeAsync_002_noAdOasis(String wikiName, String article, String adUnit,
                                            String slotName) {
    String testedPage = urlBuilder.getUrlForPath(wikiName, article);
    MobileAdsBaseObject ads = new MobileAdsBaseObject(driver, testedPage);
    ads.wait.forElementPresent(By.id(slotName));
    ads.verifyGptIframe(adUnit, slotName, SRC);

    //It's not working because there is Liftium on Oasis and no Liftium on Mobile
    //ads.verifyNoAdInSlot(slotName);
  }

}
