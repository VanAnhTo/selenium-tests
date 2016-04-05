package com.wikia.webdriver.testcases.createawikitests;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.wikia.webdriver.common.contentpatterns.CreateWikiMessages;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.RelatedIssue;
import com.wikia.webdriver.common.core.configuration.Configuration;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.componentobject.AuthModal;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.createnewwiki.CreateNewWikiPageObjectStep2;
import com.wikia.webdriver.pageobjectsfactory.pageobject.createnewwiki.CreateNewWikiPageObjectStep3;
import com.wikia.webdriver.pageobjectsfactory.pageobject.createnewwiki.SpecialCreateNewWikiPage;

@Test(groups = {"CNW_Anon"})
public class CreateWikiTests_loggedOutUser extends NewTestTemplate {

  Credentials credentials = Configuration.getCredentials();

  @Test(groups = {"CNW", "CreateNewWikiLoggedOut_001"})
  @RelatedIssue(issueID = "CE-3160",
      comment = "Test manually: Test may fail until the ticket is fixed as"
          + " notification is obscuring the feature being tested")
  public void CreateNewWiki_001_loggedOutUser() {
    final String NEW_WIKI_NAME = CreateWikiMessages.WIKINAME_PREFIX + DateTime.now().getMillis();

    new SpecialCreateNewWikiPage()
        .open()
        .typeInWikiName(NEW_WIKI_NAME)
        .verifySuccessIcon()
        .submitToLogInSignUp()
        .login(User.USER_13);

    CreateNewWikiPageObjectStep2 cnw2 = new CreateNewWikiPageObjectStep2(driver);
    cnw2.selectCategory(CreateWikiMessages.WIKI_CATEGORY);
    CreateNewWikiPageObjectStep3 cnw3 = cnw2.submit();
    cnw3.selectThemeByName(CreateWikiMessages.WIKI_THEME);
    ArticlePageObject article = cnw3.submit();
    article.verifyWikiTitleOnCongratualtionsLightBox(NEW_WIKI_NAME);
    article.closeNewWikiCongratulationsLightBox();
    article.verifyWikiTitleHeader(NEW_WIKI_NAME);
    article.verifyUserLoggedIn(credentials.userName13);
  }

  @Test(groups = {"CNW", "CreateNewWikiLoggedOut_002"})
  public void CreateNewWiki_002_wrongPassword() {
    AuthModal loginWindow = new SpecialCreateNewWikiPage()
        .open()
        .typeInWikiName()
        .verifySuccessIcon()
        .submitToLogInSignUp();

    loginWindow.login(credentials.userName, "WrongPassword");

    Assertion.assertEquals(loginWindow.getErrorMessage(),
        CreateWikiMessages.INVALID_PASSWORD_ERROR_MESSAGE);
  }

  @Test(groups = {"CNW", "CreateNewWikiLoggedOut_003"})
  public void CreateNewWiki_003_blankPassword() {
    AuthModal loginWindow = new SpecialCreateNewWikiPage()
        .open()
        .typeInWikiName()
        .verifySuccessIcon()
        .submitToLogInSignUp();

    loginWindow.login(credentials.userName, "");

    Assertion.assertEquals(loginWindow.getErrorMessage(),
        CreateWikiMessages.BLANK_PASSWORD_ERROR_MESSAGE);
  }

  @Test(groups = {"CNW", "CreateNewWikiLoggedOut_004"})
  public void CreateNewWiki_004_blankUserName() {
    AuthModal loginWindow = new SpecialCreateNewWikiPage()
        .open()
        .typeInWikiName()
        .verifySuccessIcon()
        .submitToLogInSignUp();

    loginWindow.login("", "DummyPass");

    Assertion.assertEquals(loginWindow.getErrorMessage(),
        CreateWikiMessages.BLANK_USERNAME_ERROR_MESSAGE);
  }

  @Test(groups = {"CNW", "CreateNewWikiLoggedOut_005"})
  public void CreateNewWiki_005_invalidUserName() {
    AuthModal loginWindow = new SpecialCreateNewWikiPage()
        .open()
        .typeInWikiName()
        .verifySuccessIcon()
        .submitToLogInSignUp();

    loginWindow.login(credentials.userName + "1", "DummyPass");

    Assertion.assertEquals(loginWindow.getErrorMessage(),
        CreateWikiMessages.INVALID_USERNAME_ERROR_MESSAGE);
  }
}
