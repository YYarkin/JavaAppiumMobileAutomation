import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class Lesson4 {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:/PROG/IDEAproject/JavaAppiumMobileAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testEx5SaveTwoArticle() {
        String searchWikipediaLocatorXpath = "//*[contains(@text,'Search Wikipedia')]";
        waitForElementAndClick(
                By.xpath(searchWikipediaLocatorXpath),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        String searchLine1 = "Java";
        String searchFieldLocatorXpath = "//*[contains(@text,'Search…')]";
        waitForElementAndSendKeys(
                By.xpath(searchFieldLocatorXpath),
                searchLine1,
                "Cannot find search element",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + searchLine1,
                15
        );

        String pageTitleLocatorId = "org.wikipedia:id/view_page_title_text";
        String title1BeforeSaveInList = waitForElementAndGetAttribute(
                By.id(pageTitleLocatorId),
                "text",
                "Cannot find title of article",
                10
        );

        String moreOptionsLocatorXpath = "//android.widget.ImageView[@content-desc='More options']";
        waitForElementAndClick(
                By.xpath(moreOptionsLocatorXpath),
                "Cannot find button to open article options",
                5
        );

        String addToListBtnLocatorXpath = "//android.widget.TextView[@text='Add to reading list'][@instance='2']";
        waitForElementAndClick(
                By.xpath(addToListBtnLocatorXpath),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        String textInputForNameListLocatorId = "org.wikipedia:id/text_input";
        waitForElementAndClear(
                By.id(textInputForNameListLocatorId),
                "Cannot find input to set name of articles folder",
                5
        );

        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath(searchWikipediaLocatorXpath),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        String searchLine2 = "C++";
        waitForElementAndSendKeys(
                By.xpath(searchFieldLocatorXpath),
                searchLine2,
                "Cannot find search element",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='General purpose high-level programming language']"),
                "General purpose high-level programming language' topic searching by " + searchLine2,
                5
        );

        waitForElementPresent(
                By.id(pageTitleLocatorId),
                "Cannot find title of article",
                10
        );

        waitForElementAndClick(
                By.xpath(moreOptionsLocatorXpath),
                "Cannot find button to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath(addToListBtnLocatorXpath),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find option to add article to created folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find created folder",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + searchLine2 + "']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='" + searchLine2 + "']"),
                "Find delete article",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article",
                5
        );

        String title1InList = waitForElementAndGetAttribute(
                By.id(pageTitleLocatorId),
                "text",
                "Cannot find title of article",
                10
        );

        Assert.assertEquals(
                "Article title have been changed after save in list",
                title1BeforeSaveInList,
                title1InList
        );
    }

    @Test
    public void testEx6AssertTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search element",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    protected void swipeElementToLeft(By by, String errorMesage) {
        WebElement element = waitForElementPresent(by, errorMesage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(300)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    private boolean waitForElementNotPresent(By by, String errorMesage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMesage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private void assertElementPresent(By by, String errorMesage) {
        //Assert.assertTrue(errorMesage, driver.findElement(by).isDisplayed());
        //ну или еще проще
        waitForElementPresent(by,errorMesage,0);
    }
}

