package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        //todo
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
        SEARCH_INPUT_BY_ID = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_ELEMENTS_TITLE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']";
        SEARCH_RES_WITH_TITLE_AND_DESCRIP_BY_SUBS_TPL = "xpath://*[@text='{TITLE}']/../*[@text='{DESCRIPTION}']";
    }

    public iOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
