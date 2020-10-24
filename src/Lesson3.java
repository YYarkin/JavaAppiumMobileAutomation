import io.appium.java_client.AppiumDriver;
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

public class Lesson3 {

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
    public void testEx2ElementHasText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Element doesnt contain the search text"
        );
    }

    @Test
    public void testEx3CancelSearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find Search element",
                5
        );

        assertHasSomeElementsWithLocator(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "No query results found",
                10
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find Search element",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Search results are still displayed",
                10
        );
    }

    @Test
    public void testEx4TitleResultsContainsText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                5
        );

        String searhTarget = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searhTarget,
                "Cannot find Search element",
                5
        );

        List<WebElement> elementsList = assertHasSomeElementsWithLocator(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "No query results found",
                10
        );

        assertElementHasText(
                elementsList,
                searhTarget,
                "Elements dont contain expected text"
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

    private WebElement assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement soughtElement = waitForElementPresent(by, "Cannot find sought element");
        String soughtText = soughtElement.getAttribute("text");
        Assert.assertTrue(errorMessage +
                        "\nReceiving text: " + soughtText +
                        "\nExpected text: " + expectedText,
                soughtText.contains(expectedText));
        return soughtElement;
    }

    private void assertElementHasText(WebElement soughtElement, String expectedText, String errorMessage) {
        String soughtText = soughtElement.getAttribute("text");
        Assert.assertTrue(errorMessage +
                        "\nReceiving text: " + soughtText +
                        "\nExpected text: " + expectedText,
                soughtText.contains(expectedText));
    }

    private void assertElementHasText(List<WebElement> soughtElements, String expectedText, String errorMessage) {
        String soughtText;
        for (int i = 0; i < soughtElements.size(); i++) {
            soughtText = soughtElements.get(i).getAttribute("text");
            Assert.assertTrue(errorMessage +
                            "\n(Element " + String.valueOf(i + 1) + ") Receiving text: " + soughtText +
                            "\nExpected text: " + expectedText,
                    soughtText.contains(expectedText));
        }
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private List<WebElement> assertHasSomeElementsWithLocator(By by, String errorMessage, long timeoutInSeconds) {
        waitForElementPresent(by, errorMessage, timeoutInSeconds);
        List<WebElement> soughtsElements = driver.findElements(by);
        System.out.println("Number of elements displayed = " + soughtsElements.size());
        for (int i = 0; i < soughtsElements.size(); i++)
            System.out.println(i + 1 + " = " + soughtsElements.get(i).getAttribute("text"));
        Assert.assertTrue(errorMessage, soughtsElements.size() >= 1);
        return soughtsElements;
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}