package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_ELEMENTS_TITLE,
            SEARCH_RES_WITH_TITLE_AND_DESCRIP_BY_SUBS_TPL;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    //TEMPLATES METHODS
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description) {
        return SEARCH_RES_WITH_TITLE_AND_DESCRIP_BY_SUBS_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    //TEMPLATES METHODS

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Сannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button!", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present!", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button.", 5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring" + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring" + substring, 10);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    ///////////////////////////////////My
    public void searchInputHasText(String text) {
        this.assertElementHasText(SEARCH_INPUT, text, "Element doesnt contain the search text " + text);
    }

    public void searchResultsHasText(List<WebElement> elementsList, String text) {
        this.assertElementHasText(elementsList, text, "Elements doesnt contain expected text " + text);
    }

    public List<WebElement> searchHasResults() {
        return this.assertHasSomeElementsWithLocator(SEARCH_RESULT_ELEMENTS_TITLE, "No query results found", 10);
    }

    public List<WebElement> searchHasResults(int countOfElements) {
        if (Platform.getInstance().isAndroid()) {
            return this.assertHasSomeElementsWithLocator(SEARCH_RESULT_ELEMENTS_TITLE, countOfElements, "No query results found", 10);
        } else {
            return this.assertHasSomeElementsWithLocator(SEARCH_RESULT_ELEMENT, countOfElements, "No query results found", 10);
        }
    }

    public void clearSearchResults() {
        this.waitForElementAndClear(SEARCH_INPUT, "Cannot find Search element", 5);
        this.waitForElementNotPresent(SEARCH_RESULT_ELEMENT, "Search results are still displayed", 10);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        this.waitForElementPresent(
                getResultSearchElementByTitleAndDescription(title, description),
                String.format("Cannot find element with title '%s' and description '%s'", title, description),
                10);
    }
}
