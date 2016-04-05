package com.wikia.webdriver.pageobjectsfactory.pageobject.createnewwiki;

import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.wikia.webdriver.common.contentpatterns.CreateWikiMessages;
import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.componentobject.AuthModal;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;

public class SpecialCreateNewWikiPage extends WikiBasePageObject {

  @FindBy(name = "wiki-name")
  private WebElement wikiName;
  @FindBy(name = "wiki-domain")
  private WebElement wikiDomain;
  @FindBy(css = "span.domain-status-icon img[src*='check.png']")
  private WebElement successIcon;
  @FindBy(css = ".next.enabled")
  private WebElement submitButton;
  @FindBy(css = "select[name='wiki-language']")
  private WebElement languageSelector;
  @FindBy(css = "#ChangeLang")
  private WebElement languageSelectorTrigger;
  @FindBy(css = ".domain-country")
  private WebElement languageSelectedIndicator;
  @FindBy(css = ".wiki-domain-error.error-msg")
  private WebElement wikiDomainErrorMessage;

  private String wikiNameString;

  public SpecialCreateNewWikiPage() {
    super();
  }

  /**
   * Open special Page to create new Wikia. This special page 'Special:CreateNewWiki' is only
   * available on www.wikia.com domain
   * 
   * @return
   */
  public SpecialCreateNewWikiPage open() {
    getUrl(urlBuilder.getUrlForWiki("wikia") + URLsContent.SPECIAL_CREATE_NEW_WIKI);

    return this;
  }

  public String getWikiName() {
    wikiNameString = CreateWikiMessages.WIKINAME_PREFIX + DateTime.now().getMillis();
    return this.wikiNameString;
  }


  public void selectLanguage(String lang) {
    scrollAndClick(languageSelectorTrigger);
    wait.forElementVisible(languageSelector);
    Select language = new Select(languageSelector);
    List<WebElement> langList = language.getOptions();
    for (int i = 0; i < langList.size(); i++) {
      String langDropElement = langList.get(i).getText();
      if (langDropElement.contains(lang + ":")) {
        language.selectByIndex(i);
        Assertion.assertEquals(languageSelectedIndicator.getText(), lang + ".");
        break;
      }
    }
  }

  public SpecialCreateNewWikiPage typeInWikiName(String name) {
    wikiName.sendKeys(name);
    PageObjectLogging.log("typeInWikiName ", "Typed wiki name" + name, true);

    return this;
  }

  /**
   * Use this for typing in new, unique wiki name for testing wiki creation
   * @return
   */
  public SpecialCreateNewWikiPage typeInWikiName() {
    return typeInWikiName(CreateWikiMessages.WIKINAME_PREFIX + DateTime.now().getMillis());
  }

  public void typeInWikiDomain(String domain) {
    wikiDomain.clear();
    wikiDomain.sendKeys(domain);
    PageObjectLogging.log("typeInWikiDomain ", "Typed wiki domain " + domain, true);
  }

  public SpecialCreateNewWikiPage verifySuccessIcon() {
    wait.forElementVisible(successIcon);
    wait.forElementVisible(submitButton);
    PageObjectLogging.log("waitForSuccessIcon", "Success icon found", true, driver);

    return this;
  }

  public void verifyOccupiedWikiAddress(String wikiName) {
    wait.forTextInElement(wikiDomainErrorMessage, wikiName.toLowerCase());
    PageObjectLogging.log("verifyOccupiedWikiAddress", "Verified occupied wiki address", true);
  }

  public void verifyIncorrectWikiName() {
    wait.forTextInElement(wikiDomainErrorMessage, CreateWikiMessages.WIKINAME_VIOLATES_POLICY);
    PageObjectLogging.log("verifyIncorrectWikiName", "Verified wiki name violates naming policy",
        true);
  }

  public CreateNewWikiPageObjectStep2 submit() {
    scrollAndClick(submitButton);
    PageObjectLogging.log("submit", "Submit button clicked", true, driver);
    return new CreateNewWikiPageObjectStep2(driver);
  }

  public AuthModal submitToLogInSignUp() {
    scrollAndClick(submitButton);
    PageObjectLogging.log("submit", "Submit button clicked", true, driver);
    return new AuthModal();
  }

  public void verifyWikiName(String expectedWikiName) {
    Assertion.assertEquals(wikiName.getAttribute("value"), expectedWikiName);
    PageObjectLogging.log("verifyWikiName", "verified wiki name equals: " + expectedWikiName, true);
  }

}
