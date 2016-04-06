package com.wikia.webdriver.testcases.infoboxbuilder;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.elements.oasis.pages.InfoboxBuilderPage;
import com.wikia.webdriver.elements.oasis.pages.TemplateEditPage;
import com.wikia.webdriver.elements.oasis.pages.TemplatePage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.PortableInfobox;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.themedesigner.SpecialThemeDesignerPageObject;
import org.testng.annotations.Test;

@Test(groups = "InfoboxBuilderTests")
public class InfoboxBuilderTests extends NewTestTemplate {

  @Execute(asUser = User.USER)
  public void verifyDefaultStructure() {
    new InfoboxBuilderPage()
        .open("InfoboxBuilderVerifyDefaultStructure")
        .verifyDefaultTemplateStructure();
  }

  @Execute(asUser = User.USER)
  public void addingComponents() {
    InfoboxBuilderPage builder = new InfoboxBuilderPage();

    builder.open("InfoboxBuilderAddingComponents");
    int rowComponents = builder.countRows();
    int titleComponents = builder.countTitles();
    int imageComponents = builder.countImages();
    int headerComponents = builder.countHeaders();

    builder.addRowComponent().addImageComponent().addTitleComponent().addHeaderComponent();

    Assertion.assertEquals(rowComponents + 1, builder.countRows());
    Assertion.assertEquals(titleComponents + 1, builder.countTitles());
    Assertion.assertEquals(imageComponents + 1, builder.countImages());
    Assertion.assertEquals(headerComponents + 1, builder.countHeaders());
  }

  @Execute(asUser = User.USER)
  public void savingTemplate() {
    TemplatePage templatePage = new InfoboxBuilderPage()
        .open("InfoboxBuilderSavingTemplate")
        .deleteRowComponentWithIndex(0)
        .addRowComponent()
        .save();

    Assertion.assertEquals("infoboxbuildersavingtemplate",
                           templatePage.getHeaderText().toLowerCase());
  }

  @Execute(asUser = User.USER)
  public void deletingDefaultComponents() {
    InfoboxBuilderPage builder = new InfoboxBuilderPage();
    builder.open("InfoboxBuilderDeletingDefaultComponents");
    int rowComponents = builder.countRows();
    int titleComponents = builder.countTitles();
    int imageComponents = builder.countImages();

    Assertion.assertEquals(rowComponents - 1,
                           builder.deleteRowComponentWithIndex(0).countRows());
    Assertion.assertEquals(titleComponents - 1,
                           builder.deleteTitleComponentWithIndex(0).countTitles());
    Assertion.assertEquals(imageComponents - 1,
                           builder.deleteImageComponentWithIndex(0).countImages());
  }

  @Execute(asUser = User.USER)
  public void deletingAddedComponents() {
    InfoboxBuilderPage builder = new InfoboxBuilderPage();
    builder.open("InfoboxBuilderDeletingAddedComponents");
    int rowComponents = builder.countRows();
    int titleComponents = builder.countTitles();
    int imageComponents = builder.countImages();
    int headerComponents = builder.countHeaders();

    builder.addImageComponent().addTitleComponent().addRowComponent().addHeaderComponent();

    /* deleting last (newly added) components */
    Assertion.assertEquals(
        rowComponents , builder.deleteRowComponentWithIndex(builder.countRows() - 1).countRows()
    );
    Assertion.assertEquals(
        titleComponents,
        builder.deleteTitleComponentWithIndex(builder.countTitles() - 1).countTitles()
    );
    Assertion.assertEquals(
        imageComponents,
        builder.deleteImageComponentWithIndex(builder.countImages() - 1).countImages()
    );
    Assertion.assertEquals(
        headerComponents,
        builder.deleteHeaderComponentWithIndex(builder.countHeaders() - 1).countHeaders()
    );
  }

  @Execute(asUser = User.USER)
  public void customizingComponents() {
    TemplatePage template = new InfoboxBuilderPage()
        .open("InfoboxBuilderCustomizingComponents")
        .changeHeaderCollapsibilityState(0)
        .setAndVerifyRowLabel(0, "AutomatedTest")
        .setLongLabelNameAndVerifyBreakLine(1, "AutomatedTestVeryLongName")
        .setAndVerifyHeaderName(0, "AutomatedTestHeader")
        .setTitleToUseArticleName(0)
        .save();

    String infoboxBackground = template.getPortableInfobox().getHeaderText();
    Assertion.assertEquals("InfoboxBuilderCustomizingComponents", infoboxBackground);
  }

  @Execute(asUser = User.USER)
  public void newTemplateCreation() {
    new TemplateEditPage().open("InfoboxBuilderNewTemplateCreation")
        .getTemplateClassification()
        .selectTemplateType()
        .clickAddButton();

    Assertion.assertTrue(new InfoboxBuilderPage().isInfoboxBuilderDisplayed());
  }

  @Execute(asUser = User.USER)
  public void verifyComponentHelpDialog() {
    new InfoboxBuilderPage().open("InfoboxBuilderVerifyInterfaceFunctionality")
        .selectRowWithIndex(0).verifyHelpDialog()
        .selectTitleWithIndex(0).verifyHelpDialog()
        .selectRowWithIndex(0).verifyHelpDialog()
        .selectHeaderWithIndex(0).verifyHelpDialog();
  }

  @Execute(asUser = User.USER)
  public void verifySidebarBackArrow() {
    new InfoboxBuilderPage().open("InfoboxBuilderVerifyInterfaceFunctionality")
        .selectRowWithIndex(0).verifyBackArrowFunctionality()
        .selectTitleWithIndex(0).verifyBackArrowFunctionality()
        .selectImageWithIndex(0).verifyBackArrowFunctionality()
        .selectHeaderWithIndex(0).verifyBackArrowFunctionality();
  }

  @Execute(asUser = User.STAFF)
  public void verifyInfoboxPreviewTheme() {
    InfoboxBuilderPage builder = new InfoboxBuilderPage();
    SpecialThemeDesignerPageObject themeDesigner = new SpecialThemeDesignerPageObject(driver);
    PortableInfobox infobox = new PortableInfobox();

    /* select light theme */
    themeDesigner.openSpecialDesignerPage(wikiURL).selectTheme(0);
    themeDesigner.submitTheme();

    String invocationBackgroundColor = infobox.open(PageContent.PORTABLE_INFOBOX_01)
        .getBackgroundColor();

    builder.open("InfoboxBuilderVerifyInfoboxTheme")
        .verifyInfoboxPreviewBackgroundColor(invocationBackgroundColor);

    /* select dark theme */
    themeDesigner.openSpecialDesignerPage(wikiURL).selectTheme(3);
    themeDesigner.submitTheme();

    invocationBackgroundColor = infobox.open(PageContent.PORTABLE_INFOBOX_02)
        .getBackgroundColor();

    builder.open("InfoboxBuilderVerifyInfoboxTheme")
        .verifyInfoboxPreviewBackgroundColor(invocationBackgroundColor);
  }

  /* Verify if scrolling is enabled when Infobox's height in
  preview is greater than the preview area height. */
  @Execute(asUser = User.USER)
  public void verifyScrolling() {
    new InfoboxBuilderPage().open("InfoboxBuilderVerifyScrolling")
        .addImageComponent()
        .addImageComponent()
        .addImageComponent()
        .addImageComponent()
        .verifyScrollbarIsVisible()
        .moveToLastComponent();
  }

  @Execute(asUser = User.STAFF)
  public void verifyUserInteractions() {
    InfoboxBuilderPage builder = new InfoboxBuilderPage();

    builder.open("InfoboxBuilderVerifySelectedBorderStyling").verifyTooltipOnHover();

    String borderStyle = builder.getBorderStyle();
    Assertion.assertEquals(borderStyle, "1px solid rgb(26, 94, 184)");

    borderStyle = builder.clickBuilderBackground().getBackgroundColor();
    Assertion.assertNotEquals(borderStyle, "1px solid rgb(26, 94, 184)");
  }

  @Execute(asUser = User.STAFF)
  public void verifyReordering() {
    new InfoboxBuilderPage().open("InfoboxBuilderVerifyReordering")
        .dragAndDropToTheTop(2)
        .dragAndDropToTheTop(3)
        .dragAndDropToTheTop(1);
  }

  @Execute(asUser = User.USER)
  public void verifyRedirectingUnsupportedInfoboxes() {
    new TemplatePage().open("InfoboxBuilderMultipleInfoboxes").editArticleInSrcUsingDropdown();
    Assertion.assertTrue(new TemplateEditPage().isEditAreaDisplayed());

    new TemplatePage().open("InfoboxBuilderUnsupportedMarkup").editArticleInSrcUsingDropdown();
    Assertion.assertTrue(new TemplateEditPage().isEditAreaDisplayed());
  }

  @Execute(asUser = User.USER)
  public void immutableSources() {
    new InfoboxBuilderPage().open("InfoboxBuilderImmutableRows")
        .setAndVerifyRowLabel(0, "AutomatedTest")
        .save();

    String invocationLabelText = new PortableInfobox().open("InfoboxBuilderImmutableExample")
        .getDataLabelTextWithIndex(0);

    Assertion.assertEquals("AutomatedTest", invocationLabelText);
  }
}
