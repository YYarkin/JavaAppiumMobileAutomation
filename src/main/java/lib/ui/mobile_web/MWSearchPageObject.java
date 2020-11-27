package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "xpath://div[@class = 'header-action']/button[contains(@class, 'cancel')]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[@class = 'wikidata-description'][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://p[text()='No page with this title.']";
        SEARCH_RES_WITH_TITLE_AND_DESCRIP_BY_SUBS_TPL = "xpath://*[@title = '{TITLE}']//*[text() = '{DESCRIPTION}']";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
