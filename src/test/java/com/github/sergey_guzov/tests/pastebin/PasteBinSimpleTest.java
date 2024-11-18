package com.github.sergey_guzov.tests.pastebin;


import com.github.sergey_guzov.pages.pastebin.PasteBinHomepage;
import com.github.sergey_guzov.pages.pastebin.settings.ExpirationTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

public class PasteBinSimpleTest {

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup () {

        driver = new ChromeDriver();
    }

    @Test(description = "Simple code paste creating test")
    public void simplePasteCreated () {

        String expectedCode = "Hello from WebDriver";
        String pasteName = "helloweb";
        String resultedCode = new PasteBinHomepage(driver).openPage()
                .enterCode(expectedCode)
                .setExpirationTime(ExpirationTime.TEN_MINUTES)
                .setTitle(pasteName)
                .createPaste()
                .getCodeOfPaste();

        Assert.assertTrue(resultedCode.equals(expectedCode),"Code in paste doesn't match with expected code");

    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        driver.close();
        driver = null;
    }

}