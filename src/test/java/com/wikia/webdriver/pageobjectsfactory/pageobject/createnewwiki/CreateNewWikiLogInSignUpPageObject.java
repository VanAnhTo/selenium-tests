package com.wikia.webdriver.pageobjectsfactory.pageobject.createnewwiki;

import com.wikia.webdriver.common.contentpatterns.ApiActions;
import com.wikia.webdriver.common.contentpatterns.CreateWikiMessages;
import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.signup.SignUpPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateNewWikiLogInSignUpPageObject extends WikiBasePageObject {

  @FindBy(css = "div.UserLoginModal input[name='username']")
  WebElement userNameField;
  @FindBy(css = "div.UserLoginModal input[name='password']")
  WebElement passwordField;
  @FindBy(css = "div.UserLoginModal input[type='submit']")
  WebElement submitButton;
  @FindBy(css = "div.UserLoginModal div.error-msg")
  WebElement usernameValidationText;
  @FindBy(css = "form#SignupRedirect input[type='submit']")
  WebElement signUpSubmitButton;
  @FindBy(css = ".UserLoginModal .forgot-password")
  WebElement forgotPasswordLink;

  public CreateNewWikiLogInSignUpPageObject(WebDriver driver) {
    super();
  }

  public void typeInUserName(String userName) {
    userNameField.sendKeys(userName);
    PageObjectLogging.log("typeInUserName", "user name was typed", true);
  }

  public void typeInPassword(String password) {
    passwordField.sendKeys(password);
    PageObjectLogging.log("typeInPassword", "password name was typed", true);
  }

  public void clickForgotPassword(String userName, String apiToken) {
    Assertion.assertEquals(resetForgotPasswordTime(userName, apiToken),
        ApiActions.API_ACTION_FORGOT_PASSWORD_RESPONSE);
    wait.forElementVisible(forgotPasswordLink);
    forgotPasswordLink.click();
  }

  public CreateNewWikiPageObjectStep2 submitLogin() {
    wait.forElementVisible(submitButton);
    submitButton.click();
    PageObjectLogging.log("submitLogin", "submit button was clicked", true, driver);
    return new CreateNewWikiPageObjectStep2(driver);
  }

  public SignUpPageObject submitSignup() {
    wait.forElementVisible(signUpSubmitButton);
    signUpSubmitButton.click();
    PageObjectLogging.log("submitSignUp", "signup submit button was clicked", true, driver);
    return new SignUpPageObject(driver);
  }

  public void verifyMessageAboutNewPassword(String userName) {
    wait.forElementVisible(usernameValidationText);
    String newPasswordMsg = PageContent.NEW_PASSWORD_SENT_MESSAGE.replace("%userName%", userName);
    wait.forTextInElement(usernameValidationText, newPasswordMsg);
    PageObjectLogging.log("MessageAboutPasswordSent", "Message about new password sent present",
        true);
  }


}
