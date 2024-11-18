package com.github.sergey_guzov.tests.pastebin;

import com.github.sergey_guzov.pages.pastebin.PasteBinHomepage;
import com.github.sergey_guzov.pages.pastebin.PasteBinPastePage;
import com.github.sergey_guzov.pages.pastebin.settings.ExpirationTime;
import com.github.sergey_guzov.pages.pastebin.settings.SyntaxHighlighting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PasteBinTiedTests {
    private WebDriver driver;
    private String pasteBinPastePageUrl;
    String expectedCode = "git config --global user.name  \"New Sheriff in Town\"\n" +
            "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
            "git push origin master --force";
    String pasteName = "how to gain dominance among developers";
    SyntaxHighlighting syntaxHighlighting = SyntaxHighlighting.BASH;

    @BeforeClass(alwaysRun = true)
    public void pageSetup () {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        new PasteBinHomepage(driver).openPage()
                .enterCode(expectedCode)
                .setSyntaxHighlighting(syntaxHighlighting)
                .setExpirationTime(ExpirationTime.TEN_MINUTES)
                .setTitle(pasteName)
                .createPaste();
        pasteBinPastePageUrl = driver.getCurrentUrl();
        driver.close();
        driver = null;
    }
    @BeforeMethod(alwaysRun = true)
    public void browserSetup () {

        driver = new ChromeDriver();
    }

    @Test(description = "Few lines code creation test")
    public void fewLinesPasteCreated () {

        String resultedCode = new PasteBinPastePage(driver, pasteBinPastePageUrl).openPage().getCodeOfPaste();
        Assert.assertTrue(expectedCode.equals(resultedCode), "Code in paste doesn't match with expected code");
    }

    @Test(description = "Page Title matches with paste name")
    public void pastePageTitleMatchesWithPasteTitle () {

        new PasteBinPastePage(driver, pasteBinPastePageUrl).openPage();
        Assert.assertTrue(driver.getTitle().equals(pasteName + " - Pastebin.com"), "Page title doesn't match with paste title");
    }

    @Test(description = "Selected syntax style applied test")
    public void selectedSyntaxStyleApplied () {

        Assert.assertTrue(new PasteBinPastePage(driver,pasteBinPastePageUrl).openPage().isSyntaxStyleElementExists(syntaxHighlighting),
                "Syntax doesn't match with selected syntax");
    }


    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        driver.close();
        driver = null;
    }

}
