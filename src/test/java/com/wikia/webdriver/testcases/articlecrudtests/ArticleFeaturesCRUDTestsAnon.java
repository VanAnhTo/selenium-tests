package com.wikia.webdriver.testcases.articlecrudtests;

import com.wikia.webdriver.common.core.annotations.RelatedIssue;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.editmode.VisualEditModePageObject;

import org.testng.annotations.Test;

public class ArticleFeaturesCRUDTestsAnon extends NewTestTemplate {

  private static final String articleName = "ArticleFeaturesCRUDAnon";

  @Test(groups = {"ArticleFeatureCRUDAnonymous_001", "ArticleFeaturesCRUDAnon"})
  @RelatedIssue(issueID = "SOC-2669", comment = "fails after product change, SOC team on it" )
  public void ArticleCRUDAnonymous_001_AddingImage() {
    ArticlePageObject article = new ArticlePageObject().open(articleName);
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.clickPhotoButton();
    visualEditMode.verifyModalLoginAppeared();
  }

  @Test(groups = {"ArticleFeatureCRUDAnonymous_002", "ArticleFeaturesCRUDAnon"})
  @RelatedIssue(issueID = "SOC-2669", comment = "fails after product change, SOC team on it" )
  public void ArticleCRUDAnonymous_002_AddingGallery() {
    ArticlePageObject article = new ArticlePageObject().open(articleName);
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.clickGalleryButton();
    visualEditMode.verifyModalLoginAppeared();
  }

  @Test(groups = {"ArticleFeatureCRUDAnonymous_003", "ArticleFeaturesCRUDAnon"})
  @RelatedIssue(issueID = "SOC-2669", comment = "fails after product change, SOC team on it" )
  public void ArticleCRUDAnonymous_003_AddingSlideshow() {
    ArticlePageObject article = new ArticlePageObject().open(articleName);
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.clickSlideshowButton();
    visualEditMode.verifyModalLoginAppeared();
  }

  @Test(groups = {"ArticleFeatureCRUDAnonymous_004", "ArticleFeaturesCRUDAnon"})
  @RelatedIssue(issueID = "SOC-2669", comment = "fails after product change, SOC team on it" )
  public void ArticleCRUDAnonymous_004_AddingSlider() {
    ArticlePageObject article = new ArticlePageObject().open(articleName);
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.clickSliderButton();
    visualEditMode.verifyModalLoginAppeared();
  }

  @Test(groups = {"ArticleFeatureCRUDAnonymous_005", "ArticleFeaturesCRUDAnon"})
  @RelatedIssue(issueID = "SOC-2669", comment = "fails after product change, SOC team on it" )
  public void ArticleCRUDAnonymous_005_AddingVideo() {
    ArticlePageObject article = new ArticlePageObject().open(articleName);
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.clickVideoButton();
    visualEditMode.verifyModalLoginAppeared();
  }
}
