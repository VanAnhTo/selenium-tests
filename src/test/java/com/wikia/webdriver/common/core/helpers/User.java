package com.wikia.webdriver.common.core.helpers;

import com.wikia.webdriver.common.core.XMLReader;
import com.wikia.webdriver.common.core.configuration.Configuration;

import java.io.File;

public enum User {
  USER("ci.user.regular.username", "ci.user.regular.password"),
  USER_2("ci.user.regular2.username", "ci.user.regular2.password"),
  USER_3("ci.user.regular3.username", "ci.user.regular3.password"),
  USER_5("ci.user.regular5.username", "ci.user.regular5.password"),
  USER_9("ci.user.regular9.username", "ci.user.regular9.password"),
  USER_12("ci.user.regular12.username", "ci.user.regular12.password", "ci.user.regular12.user_id"),
  USER_13("ci.user.regular13.username", "ci.user.regular13.password"),
  STAFF("ci.user.wikiastaff.username", "ci.user.wikiastaff.password"),
  ANONYMOUS("anonymous", "anonymous"),
  REGULAR_USER_JAPAN("ci.user.language2.username", "ci.user.language2.password"),
  USER_GO_SEARCH_PREFERRED("ci.user.goSearchPreferredUser.username",
                           "ci.user.goSearchPreferredUser.password"),
  BLOCKED_USER("ci.user.tooManyLoginAttempts.username", "ci.user.tooManyLoginAttempts.password"),
  CONSTANTLY_BLOCKED_USER("ci.user.constantlyBlockedAccountUser.username", "ci.user.constantlyBlockedAccountUser.password"),
  GOOGLE_CONNECTED("ci.user.google_connected.username", "ci.user.google_connected.password");

  private final String userName;

  private final String password;
  private final String filePath = Configuration.getCredentialsFilePath();
  private String userId;

  User(String userNameKey, String passwordKey) {
    this.userName = XMLReader.getValue(new File(filePath), userNameKey);
    this.password = XMLReader.getValue(new File(filePath), passwordKey);
    this.userId = "";
  }

  User(String userNameKey, String passwordKey, String userId) {
    this.userName = XMLReader.getValue(new File(filePath), userNameKey);
    this.password = XMLReader.getValue(new File(filePath), passwordKey);
    this.userId = XMLReader.getValue(new File(filePath), userId);
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getUserId() {
    return userId;
  }
}
