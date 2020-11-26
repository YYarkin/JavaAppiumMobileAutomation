package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        service.start();
        driver = Platform.getInstance().getDriver();
        this.skipWelcomePageForIOSApp();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        service.stop();

        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private void skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }
}
