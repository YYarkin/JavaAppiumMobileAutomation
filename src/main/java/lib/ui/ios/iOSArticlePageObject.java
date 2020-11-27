package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CLOSE_DIALOG_WINDOW = "id:places auth close";
        TITLE_WITH_ID = "id:{TITLE}";
    }

    public iOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
