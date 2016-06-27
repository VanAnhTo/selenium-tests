package com.wikia.webdriver.testcases.logintests;

import com.wikia.webdriver.common.core.MailFunctions;
import com.wikia.webdriver.common.core.annotations.RelatedIssue;
import com.wikia.webdriver.common.core.configuration.Configuration;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.componentobject.AuthModal;
import com.wikia.webdriver.pageobjectsfactory.componentobject.global_navitagtion.NavigationBar;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.login.SpecialUserLoginPageObject;

import org.testng.annotations.Test;

@Test(groups = "auth-forgottenPassword")
public class ForgottenPasswordTests extends NewTestTemplate {

  Credentials credentials = Configuration.getCredentials();

  @Test(groups = "ForgottenPassword_anonCanRemindPasswordFromAuthModal")
  public void anonCanRemindPasswordFromAuthModal() {
    String userName = credentials.userNameForgottenPassword;
    MailFunctions.deleteAllEmails(credentials.email, credentials.emailPassword);
    WikiBasePageObject base = new WikiBasePageObject();
    base.openWikiPage(wikiURL);
    NavigationBar signInLink = new NavigationBar(driver);
    signInLink.clickOnSignIn();
    AuthModal loginModal = new AuthModal();
    loginModal.clickForgotPasswordLink();
    SpecialUserLoginPageObject login = new SpecialUserLoginPageObject(driver);
    login.remindPasswordNewAuth(userName, credentials.apiToken);
    login.verifyMessageAboutNewPassword(userName);
    login.clickLogInLink();
    String
        newPassword =
        login.receiveMailWithNewPassword(credentials.email, credentials.emailPassword);
    loginModal.login(userName, newPassword);
    loginModal.verifyUserLoggedIn(userName);
  }

  @Test(groups = "ForgottenPassword_anonCanRemindPasswordOnUserLoginSpecialPageUsingLowerCaseUserName")
  @RelatedIssue(issueID = "SOC-2672", comment = "Test manually")
  public void anonCanRemindPasswordOnUserLoginSpecialPageUsingLowerCaseUserName() {
    String userNameUC = credentials.userNameForgottenPassword3;
    String userName = userNameUC.toLowerCase();
    MailFunctions.deleteAllEmails(credentials.email, credentials.emailPassword);
    WikiBasePageObject base = new WikiBasePageObject();
    base.openWikiPage(wikiURL);
    SpecialUserLoginPageObject login = base.openSpecialUserLogin(wikiURL);

    AuthModal loginModal = new AuthModal();
    loginModal.clickForgotPasswordLink();
    login.remindPassword(userName, credentials.apiToken);
    login.verifyMessageAboutNewPassword(userName);
    String
        newPassword =
        login.receiveMailWithNewPassword(credentials.email, credentials.emailPassword);
    login.login(userName, newPassword);
    newPassword = login.setNewPassword();
    String verifyString = userName.substring(0, 1).toUpperCase() + userName.substring(1);
    login.verifyUserLoggedIn(verifyString);

    login.logOut(wikiURL);
    login.openSpecialUserLogin(wikiURL);
    login.login(userName, newPassword);
    login.verifyUserLoggedIn(verifyString);
  }

  @Test(groups = "ForgottenPassword_anonCanRemindPasswordOnUserLoginSpecialPage")
  @RelatedIssue(issueID = "SOC-2672", comment = "Test manually")
  public void anonCanRemindPasswordOnUserLoginSpecialPage() {
    String userName = credentials.userNameForgottenPassword2;
    MailFunctions.deleteAllEmails(credentials.email, credentials.emailPassword);
    WikiBasePageObject base = new WikiBasePageObject();
    SpecialUserLoginPageObject login = base.openSpecialUserLogin(wikiURL);
    AuthModal loginModal = new AuthModal();
    loginModal.clickForgotPasswordLink();
    login.remindPassword(userName, credentials.apiToken);
    login.verifyMessageAboutNewPassword(userName);
    String
        newPassword =
        login.receiveMailWithNewPassword(credentials.email, credentials.emailPassword);
    login.login(userName, newPassword);
    newPassword = login.setNewPassword();
    login.verifyUserLoggedIn(userName);

    login.logOut(wikiURL);
    login.openSpecialUserLogin(wikiURL);
    login.login(userName, newPassword);
    login.verifyUserLoggedIn(userName);
  }

}
