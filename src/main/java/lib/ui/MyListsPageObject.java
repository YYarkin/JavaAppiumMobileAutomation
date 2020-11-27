package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);
        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder by name " + nameOfFolder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementPresent(articleXpath,
                "Cannot find saved article by title " + articleTitle,
                15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementNotPresent(articleXpath,
                "Saved article still present with title " + articleTitle,
                15);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isAndroid())) {
            this.swipeElementToLeft(
                    articleXpath,
                    "Cannot find saved article"
            );
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementAndClick(
                    removeLocator,
                    "Cannot click buton to remove article from saved",
                    10
            );
        }
        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(articleXpath, "Cannot find saved article");
        }
        if (Platform.getInstance().isMW()) {
            waitTimeOut(1);
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    ////////////////////////////////////////////My
    public void clickToArticleByTitle(String articleTitle) {
        this.waitForElementAndClick(getSavedArticleXpathByTitle(articleTitle),
                "Cannot find saved article with title " + articleTitle,
                5);
    }
}
