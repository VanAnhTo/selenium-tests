package com.wikia.webdriver.elements.oasis.pages;

import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.elements.oasis.components.templateclassificiation.TemplateClassification;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TemplateEditPage extends WikiBasePageObject {

  @FindBy(css = "#editarea")
  private WebElement editArea;

  @FindBy(css = "#PermissionsError")
  private WebElement permissionsErrorNotice;

  public TemplateEditPage() {
    super();
  }

  public TemplateEditPage open(String templateName) {
    getUrl(urlBuilder.appendQueryStringToURL(String.format("%s%s%s:%s", urlBuilder.getUrlForWiki(),
       URLsContent.WIKI_DIR, URLsContent.TEMPLATE_NAMESPACE, templateName),
       URLsContent.ACTION_EDIT));

    return this;
  }

  public TemplateClassification getTemplateClassification() {
    return new TemplateClassification();
  }

  public boolean isEditAreaDisplayed() {
    return editArea.isDisplayed();
  }

  public boolean isEditAreaEmpty() {

    return ((List<String>) jsActions.execute("$('.ace_line_group')")).isEmpty();
  }

  public boolean isPermissionErrorDisplayed() {
    wait.forElementPresent(By.cssSelector("#PermissionsError"));

    return permissionsErrorNotice.isDisplayed();
  }
}
