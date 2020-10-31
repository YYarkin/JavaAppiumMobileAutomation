package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        String searchLine = "Linkin Park Discography";
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few results!", amountOfSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        String searchLine = "zxcvbasdfgwerty";
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    //My
    @Test
    public void testEx2ElementHasText() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.searchInputHasText("Search…");
    }

    @Test
    public void testEx3CancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.searchHasResults();
        searchPageObject.clearSearchResults();
    }

    @Test
    public void testEx4TitleResultsContainsText() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        String searсhTarget = "Java";
        searchPageObject.typeSearchLine(searсhTarget);
        List<WebElement> elementsList = searchPageObject.searchHasResults();
        searchPageObject.searchResultsHasText(elementsList, searсhTarget);
    }

    @Test
    public void testEx9SearchElementByTitleAndDescription() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.searchHasResults(3);
        searchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "Programming language");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");

    }
}
