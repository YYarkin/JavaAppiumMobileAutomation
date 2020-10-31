package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
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
                .press(x, startY)
                .waitAction(timeOfSwipe)
                .moveTo(x, endY)
                .release()
                .perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMesage, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMesage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeElementToLeft(By by, String errorMesage) {
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

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present\n";
            throw new AssertionError(defaultMessage + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    //My
    public WebElement assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement soughtElement = waitForElementPresent(by, "Cannot find sought element");
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

    public List<WebElement> assertHasSomeElementsWithLocator(By by, String errorMessage, long timeoutInSeconds) {
        waitForElementPresent(by, errorMessage, timeoutInSeconds);
        List<WebElement> soughtsElements = driver.findElements(by);
        System.out.println("Number of elements displayed = " + soughtsElements.size());
        for (int i = 0; i < soughtsElements.size(); i++)
            System.out.println(i + 1 + " = " + soughtsElements.get(i).getAttribute("text"));
        Assert.assertTrue(errorMessage, soughtsElements.size() >= 1);
        return soughtsElements;
    }
}
