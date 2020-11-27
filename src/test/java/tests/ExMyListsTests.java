package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ExMyListsTests extends CoreTestCase {

    private static final String nameOfFolder = "Learning programming";
    private static final String login = "WikiMobileTest";
    private static final String password = "vTgfhYv9wiki";

    @Test
    public void testEx5SaveTwoArticle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String searchLine1 = "Java";
        searchPageObject.typeSearchLine(searchLine1);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        articlePageObject.waitForTitleElement();
        String title1 = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyNewList(nameOfFolder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
        } else {
            navigationUI.openNavigation();

            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.login(login, password);

            articlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login",
                    title1,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticlesToMySaved();

        }

        searchPageObject.initSearchInput();
        String searchLine2 = "C++";
        searchPageObject.typeSearchLine(searchLine2);

        if (Platform.getInstance().isAndroid()) {
            searchPageObject.clickByArticleWithSubstring("General purpose high-level programming language");
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticleToMyCurrentList(nameOfFolder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            searchPageObject.clickByArticleWithSubstring("General purpose high-level programming language");
            articlePageObject.waitForTitleElement(searchLine2);
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
        } else {
            searchPageObject.clickByArticleWithSubstring("General-purpose programming language");
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticlesToMySaved();
            navigationUI.openNavigation();
        }

        navigationUI.clickMyLists();

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameOfFolder);
        }

        myListsPageObject.swipeByArticleToDelete(searchLine2);
        myListsPageObject.waitForArticleToAppearByTitle(title1);
    }
}
