package com.wikia.webdriver.pageobjectsfactory.componentobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.login.SpecialUserLoginPageObject;

public class AuthModal extends WikiBasePageObject {

  @FindBy(css = ".auth.desktop.signin-page")
  private WebElement authModal;
  @FindBy(css = "#loginUsername")
  private WebElement usernameField;
  @FindBy(css = "#loginPassword")
  private WebElement passwordField;
  @FindBy(css = "#loginSubmit")
  private WebElement signInButton;
  @FindBy(css = ".auth-modal iframe")
  private WebElement iFrame;
  @FindBy(css = ".forgotten-password")
  private WebElement forgottenPasswordLink;
  @FindBy(css = "#loginForm small.error")
  private WebElement errorMessage;
  private String mainWindowHandle;

  public AuthModal() {
    super();
    waitForNewWindow();
    this.mainWindowHandle = driver.getWindowHandle();
  }

  private void switchToAuthModalHandle() {
    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);
    }
  }

  private void switchToMainWindowHandle() {
    driver.switchTo().window(this.mainWindowHandle);
  }

  public boolean isOpened() {
    switchToAuthModalHandle();
    boolean isOpenedResult = authModal.isDisplayed();
    switchToMainWindowHandle();
    return isOpenedResult;
  }

  public void login(String username, String password) {
    switchToAuthModalHandle();
    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    signInButton.click();
    switchToMainWindowHandle();
  }

  public void login(User user) {
    login(user.getUserName(), user.getPassword());
  }

  public SpecialUserLoginPageObject clickForgotPasswordLink() {
    switchToAuthModalHandle();
    forgottenPasswordLink.click();
    switchToMainWindowHandle();

    return new SpecialUserLoginPageObject(driver);
  }

  /**
   * Get Visible error message
   * 
   * @return user visible error message text
   */
  public String getErrorMessage() {
    try {
      switchToAuthModalHandle();
      wait.forElementVisible(errorMessage);
      return errorMessage.getText();
    } finally {
      switchToMainWindowHandle();
    }
  }
}
