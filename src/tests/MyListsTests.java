package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePageObject.addArticleToMyNewList(nameOfFolder);
        articlePageObject.closeArticle();

        navigationUI.clickMyLists();

        myListsPageObject.openFolderByName(nameOfFolder);
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    //My
    @Test
    public void testEx5SaveTwoArticle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        
        searchPageObject.initSearchInput();
        String searchLine1 = "Java";
        searchPageObject.typeSearchLine(searchLine1);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        articlePageObject.waitForTitleElement();
        String title1BeforeSaveInList = articlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePageObject.addArticleToMyNewList(nameOfFolder);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        String searchLine2 = "C++";
        searchPageObject.typeSearchLine(searchLine2);
        searchPageObject.clickByArticleWithSubstring("General purpose high-level programming language");

        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToMyCurrentList(nameOfFolder);
        articlePageObject.closeArticle();

        navigationUI.clickMyLists();

        myListsPageObject.openFolderByName(nameOfFolder);
        myListsPageObject.swipeByArticleToDelete(searchLine2);
        myListsPageObject.clickToArticleByTitle("Java (programming language)");

        articlePageObject.waitForTitleElement();
        String title1InList = articlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after save in list",
                title1BeforeSaveInList,
                title1InList
        );
    }
}
