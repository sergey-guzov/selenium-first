package com.github.sergey_guzov.pages.pastebin;

import com.github.sergey_guzov.pages.pastebin.settings.ExpirationTime;
import com.github.sergey_guzov.pages.pastebin.settings.SyntaxHighlighting;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class PasteBinHomepage extends PasteBinAbstractPage {
    private static final String HOME_URL = "https://pastebin.com";
    private final String ACCEPT_COOKIES_BUTTON_XPATH = "//button[@mode='primary']/span[text()='AGREE']";
    private final String HIDE_SLIDE_BANNER_BUTTON_XPATH = "//vli[@id='hideSlideBanner']";
    @FindBy(xpath = "//textarea[@id='postform-text']")
    private WebElement codeInputForm;
    @FindBy(xpath = "//div[@class='form-group field-postform-format']//span[@class='selection']")
    private WebElement syntaxHighlightingDropBox;
    @FindBy(xpath = "//div[@class='form-group field-postform-expiration']//span[@class='selection']")
    private WebElement expirationTimeDropBox;
    @FindBy(xpath = "//div[@class='form-group field-postform-name']//input[@id='postform-name']")
    private WebElement titleInputField;
    @FindBy(xpath = "//button[text()='Create New Paste']")
    private WebElement createPasteButton;
    @FindBy(xpath = "//div[@class='error-summary']")
    private WebElement errorMessage;
    public PasteBinHomepage (WebDriver webDriver) {
        super(webDriver);
    }
    @Override
    public PasteBinHomepage openPage () {

        webDriver.get(HOME_URL);
        acceptCookies();
        hideSlideBanner();
        return this;
    }
    public PasteBinHomepage enterCode (String code) {

        codeInputForm.click();
        codeInputForm.sendKeys(code);
        return this;
    }
    public PasteBinHomepage setSyntaxHighlighting (SyntaxHighlighting syntaxHighlighting) {

        String syntax = syntaxHighlighting.getSyntax();
        scrollPageTo(syntaxHighlightingDropBox);
        syntaxHighlightingDropBox.click();
        WebElement syntaxHighlightingDropBoxField = webDriver.
                findElement(By.xpath("//li[text()='"+ syntax +"']"));
        syntaxHighlightingDropBoxField.click();
        return this;

    }
    public PasteBinHomepage setExpirationTime (ExpirationTime expirationTime) {

        String time = expirationTime.getExpirationTime();
        scrollPageTo(expirationTimeDropBox);
        expirationTimeDropBox.click();
        WebElement expirationTimeDropBoxField = webDriver.
                findElement(By.xpath("//li[text()='"+ time +"']"));
        expirationTimeDropBoxField.click();
        return this;

    }
    public PasteBinHomepage setTitle (String title) {

        scrollPageTo(titleInputField);
        titleInputField.click();
        titleInputField.sendKeys(title);
        return this;

    }
    public PasteBinPastePage createPaste ()  {

        scrollPageTo(createPasteButton);
        createPasteButton.click();
        new WebDriverWait(webDriver, Duration.ofSeconds(2)).until(ExpectedConditions.urlMatches("https://pastebin\\.com/.+"));
        return new PasteBinPastePage(webDriver);
    }
    private void acceptCookies () {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH)));
        WebElement acceptCookiesButton = webDriver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH));
        acceptCookiesButton.click();
    }
    private void hideSlideBanner () {
        new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(HIDE_SLIDE_BANNER_BUTTON_XPATH)));
        WebElement hideBannerButton = webDriver.findElement(By.xpath(HIDE_SLIDE_BANNER_BUTTON_XPATH));
        hideBannerButton.click();
    }
    @Override
    protected void scrollPageTo (WebElement element) {

        js.executeScript(
                "const element = arguments[0];" +
                        "const rect = element.getBoundingClientRect();" +
                        "window.scrollBy({ top: rect.top + window.scrollY - (window.innerHeight / 2), behavior: 'smooth' });",
                element
        );
    }
}
