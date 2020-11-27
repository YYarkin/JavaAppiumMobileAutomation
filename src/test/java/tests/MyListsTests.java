package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String nameOfFolder = "Learning programming";
    private static final String login = "WikiMobileTest";
    private static final String password = "vTgfhYv9wiki";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyNewList(nameOfFolder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()){
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
        } else {
            navigationUI.openNavigation();

            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.login(login, password);

            articlePageObject.waitForTitleElement();
            articlePageObject.addArticlesToMySaved();

            assertEquals("We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle()
            );
        }


        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameOfFolder);
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }
}
