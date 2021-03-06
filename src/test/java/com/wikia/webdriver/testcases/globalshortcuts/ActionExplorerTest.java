package com.wikia.webdriver.testcases.globalshortcuts;

import org.testng.annotations.Test;

import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.InBrowser;
import com.wikia.webdriver.common.core.annotations.RelatedIssue;
import com.wikia.webdriver.common.core.drivers.Browser;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.HomePage;

@Execute(asUser = User.USER, onWikia = "globalshortcuts-en")
@InBrowser(browser = Browser.CHROME)
public class ActionExplorerTest extends NewTestTemplate {

  @Test(groups = "globalShortcuts_actionExplorer_openAndCloseModalByShortcuts")
  @RelatedIssue(issueID = "CE-3356")
  public void globalShortcuts_actionExplorer_openAndCloseModalByShortcuts() {
    new HomePage()
        .open()
        .getActionExplorer()
        .useShortcut(".")
        .useShortcut("ESC");
  }

  @Test(groups = "globalShortcuts_actionExplorer_openKeyboardShortcutsBySearch")
  public void globalShortcuts_actionExplorer_openKeyboardShortcutsBySearch() {
    new HomePage()
        .open()
        .getActionExplorer()
        .useShortcut(".")
        .searchFor("Keyboard")
        .selectKeyboardShortcutsFromSearchSuggestions();
  }

  @Test(groups = "globalShortcuts_actionExplorer_openSpecialAllPagesFromAutocompleteSuggestions")
  public void globalShortcuts_actionExplorer_openSpecialAllPagesFromAutocompleteSuggestions() {
    new HomePage()
        .open()
        .getActionExplorer()
        .useShortcut(".")
        .scrollToAllPagesLink()
        .openAllPagesLink();
  }
}
