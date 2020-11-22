package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[not(@name='Search Wikipedia')]";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath:////XCUIElementTypeStaticText[@name='No results found']";
        //My
        SEARCH_RESULT_ELEMENTS_TITLE = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell";
        SEARCH_RES_WITH_TITLE_AND_DESCRIP_BY_SUBS_TPL = "xpath://*[contains(@name,'{TITLE}')][contains(@name,'{DESCRIPTION}')]";
    }

    public iOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
