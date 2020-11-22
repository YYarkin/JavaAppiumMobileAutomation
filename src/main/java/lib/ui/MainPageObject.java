package lib.ui;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action
                .press(PointOption.point(x, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, endY))
                .release()
                .perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String errorMesage, int maxSwipes) {
        By by = this.getLocatorByString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMesage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 1).getLocation().getY();
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMesage) {
        WebElement element = this.waitForElementPresent(locator + "/..", errorMesage);
        int rightX = element.getLocation().getX();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        int width = element.getSize().getWidth();

        int pointToClickX = (rightX + width) - 3;
        int pointToClickY = middleY;

        TouchAction action = new TouchAction(driver);
        action.tap(PointOption.point(pointToClickX, pointToClickY)).perform();
    }

    public void swipeElementToLeft(String locator, String errorMesage) {
        WebElement element = waitForElementPresent(locator, errorMesage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present\n";
            throw new AssertionError(defaultMessage + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else throw new IllegalArgumentException("Cannot get type of locator. Locator " + locator_with_type);
    }

    //////////////////////////////////////My
    public WebElement assertElementHasText(String locator, String expectedText, String errorMessage) {
        WebElement soughtElement = waitForElementPresent(locator, "Cannot find sought element");
        String soughtText = soughtElement.getAttribute("text");
        Assert.assertTrue(errorMessage +
                        "\nReceiving text: " + soughtText +
                        "\nExpected text: " + expectedText,
                soughtText.contains(expectedText));
        return soughtElement;
    }

    public void assertElementHasText(WebElement soughtElement, String expectedText, String errorMessage) {
        String soughtText = soughtElement.getAttribute("text");
        Assert.assertTrue(errorMessage +
                        "\nReceiving text: " + soughtText +
                        "\nExpected text: " + expectedText,
                soughtText.contains(expectedText));
    }

    public void assertElementHasText(List<WebElement> soughtElements, String expectedText, String errorMessage) {
        String soughtText;
        for (int i = 0; i < soughtElements.size(); i++) {
            soughtText = soughtElements.get(i).getAttribute("text");
            Assert.assertTrue(errorMessage +
                            "\n(Element " + String.valueOf(i + 1) + ") Receiving text: " + soughtText +
                            "\nExpected text: " + expectedText,
                    soughtText.contains(expectedText));
        }
    }

    public List<WebElement> assertHasSomeElementsWithLocator(String locator, String errorMessage, long timeoutInSeconds) {
        waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        By by = this.getLocatorByString(locator);
        List<WebElement> soughtsElements = driver.findElements(by);
        System.out.println("Number of elements displayed = " + soughtsElements.size());
        for (int i = 0; i < soughtsElements.size(); i++)
            System.out.println(i + 1 + " = " + soughtsElements.get(i).getAttribute("text"));
        Assert.assertTrue(errorMessage, soughtsElements.size() >= 1);
        return soughtsElements;
    }

    public List<WebElement> assertHasSomeElementsWithLocator(String locator, int countOfElements, String errorMessage, long timeoutInSeconds) {
        waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        By by = this.getLocatorByString(locator);
        List<WebElement> soughtsElements = driver.findElements(by);
        System.out.println("Number of elements displayed = " + soughtsElements.size());
        for (int i = 0; i < soughtsElements.size(); i++)
            System.out.println(i + 1 + " = " + soughtsElements.get(i).getAttribute("text"));
        Assert.assertTrue(errorMessage, soughtsElements.size() >= countOfElements);
        return soughtsElements;
    }

    public void assertElementPresent(String locator, String errorMesage) {
        By by = this.getLocatorByString(locator);
        Assert.assertTrue(errorMesage, driver.findElement(by).isDisplayed());
    }

    public boolean checkElementPresent(String locator, long waitTimeOut) {
        By by = this.getLocatorByString(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTimeOut);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (ElementNotFoundException | TimeoutException ex) {
            return false;
        }
    }
}
