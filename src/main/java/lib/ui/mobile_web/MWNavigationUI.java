package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "xpath://span[text()='Watchlist']/..";
        OPEN_NAVIGATION = "xpath://label[@title= 'Open main menu']";
    }

    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
