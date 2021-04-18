package com.pjyotik.base;

import com.pjyotik.components.CustomKeywords;
import com.pjyotik.components.PropertiesOperations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class Base extends CustomKeywords {

    BrowserFactory browserFactory = new BrowserFactory();

    @BeforeMethod
    public void LaunchApplication() throws Exception {
        String browser = PropertiesOperations.getPropertyValueByKey("browser");
        String url = 	PropertiesOperations.getPropertyValueByKey("url");
        // Set the Driver based on selection
        DriverFactory.getInstance().setDriver(browserFactory.createBrowserInstance(browser));
        // Get the driver
        DriverFactory.getInstance().getDriver().manage().window().maximize();
        DriverFactory.getInstance().getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        DriverFactory.getInstance().getDriver().navigate().to(url);

    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.getInstance().closeBrowser();
    }

}
