package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browser, String gridUrl) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser.toLowerCase());

        WebDriver remoteDriver = new RemoteWebDriver(new URL(gridUrl), caps);
        remoteDriver.manage().window().maximize();
        remoteDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(remoteDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}