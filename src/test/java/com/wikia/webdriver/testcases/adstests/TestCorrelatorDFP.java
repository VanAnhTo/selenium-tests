package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.NetworkTrafficDump;
import com.wikia.webdriver.common.templates.TemplateNoFirstLoad;
import com.wikia.webdriver.pageobjectsfactory.pageobject.HomePage;

import org.testng.annotations.Test;

public class TestCorrelatorDFP extends TemplateNoFirstLoad {

  String correlatorDesktop = "_adtest,home,gpt";
  String correlatorMobile = "_adtest,home,mobile";

  @Test(groups = "DFPCorrelatorOasis")
  @NetworkTrafficDump
  @Execute(onWikia = "adtest")
  public void correlatorIDInQueryStringShouldBeTheSameOasis() {
    networkTrafficInterceptor.startIntercepting("DFPCorrelator");
    new HomePage().open();
    networkTrafficInterceptor.logDFP(correlatorDesktop);
  }

  @Test(groups = "DFPCorrelatorMercury")
  @NetworkTrafficDump
  @Execute(onWikia = "adtest")
  public void correlatorIDInQueryStringShouldBeTheSameMercury() {
    networkTrafficInterceptor.startIntercepting("DFPCorrelator");
    new HomePage().open();
    networkTrafficInterceptor.logDFP(correlatorMobile);
  }
}
