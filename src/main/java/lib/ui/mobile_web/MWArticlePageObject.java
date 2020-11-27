package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer[role='contentinfo']";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[text()='Watch']/..";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://*[text()='Unwatch']/..";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
