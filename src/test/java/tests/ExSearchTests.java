package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ExSearchTests extends CoreTestCase {

    @Test
    public void testEx2ElementHasText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.searchInputHasText("Search…");
    }

    @Test
    public void testEx3CancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.searchHasResults();
        searchPageObject.clearSearchResults();
    }

    @Test
    public void testEx4TitleResultsContainsText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String searсhTarget = "Java";
        searchPageObject.typeSearchLine(searсhTarget);
        List<WebElement> elementsList = searchPageObject.searchHasResults();
        searchPageObject.searchResultsHasText(elementsList, searсhTarget);
    }

    @Test
    public void testEx9SearchElementByTitleAndDescription() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.searchHasResults(3);
        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isIOS())) {
            searchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia");
            searchPageObject.waitForElementByTitleAndDescription("JavaScript", "Programming language");
            searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        } else {
            searchPageObject.waitForElementByTitleAndDescription("Java", "Indonesian island");
            searchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
            searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        }
    }
}
