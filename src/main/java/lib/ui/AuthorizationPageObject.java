package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {
    private static final String
            LOGIN_BUTTON = "xpath://span[text()='Log in']/..",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:button[name='wploginattempt']";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void login(String login, String password) {
        tryClickElementWithFewAttempts(LOGIN_BUTTON, "Cant find and click auth button", 5);
        waitTimeOut(1);
        waitForElementAndSendKeys(LOGIN_INPUT, login, "Cant find and put a login to the login input", 5);
        waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cant find and put a pass to the pass input", 5);
        waitForElementAndClick(SUBMIT_BUTTON, "Cant find and click submit button", 5);
    }
}
