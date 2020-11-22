package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "xpath://XCUIElementTypeStaticText[@name=\"Learn more about Wikipedia\"]",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_PREFFERED_LANG_LINK = "xpath:(//XCUIElementTypeStaticText[@name=\"Add or edit preferred languages\"])[2]",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "xpath://XCUIElementTypeStaticText[@name=\"Learn more about data collected\"]",
            NEXT_LINK = "xpath://XCUIElementTypeButton[@name=\"Next\"]",
            GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Get started\"]\n",
            SKIP_BUTTON = "";


    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void waitForNewWayToExploreText() {
        waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore'", 10);
    }

    public void waitForAddOrEditPreferredLangText() {
        waitForElementPresent(STEP_ADD_OR_EDIT_PREFFERED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickNextButton() {
        waitForElementAndClick(NEXT_LINK, "Cannot find and click at 'Next' button", 10);
    }

    public void clickGetStartedButton() {
        waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click at 'Get started' button", 10);
    }

    //todo
    public void clickSkip() {
        this.waitForElementAndClick(SKIP_BUTTON, "Cannot find and click skip button", 5);
    }
}
