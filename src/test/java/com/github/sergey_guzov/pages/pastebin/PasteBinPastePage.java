package com.github.sergey_guzov.pages.pastebin;

import com.github.sergey_guzov.pages.pastebin.settings.SyntaxHighlighting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class PasteBinPastePage extends PasteBinAbstractPage {
    private static final Logger logger = Logger.getLogger(PasteBinPastePage.class.getName());
    private String url;
    @FindBys(@FindBy(xpath ="//div[@class='highlighted-code']//div[@class='de1']"))
    private List<WebElement> codeLines;

    public PasteBinPastePage (WebDriver webDriver) {
        super(webDriver);
    }

    public PasteBinPastePage (WebDriver webDriver, String url) {
        super(webDriver);
        this.url = url;

    }

    @Override
    public PasteBinPastePage openPage ()  {
        if (url == null) throw new RuntimeException("URL for PasteBinPastePage is not set");
        webDriver.get(url);
        return this;

    }

    public String getCodeOfPaste () {

        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(codeLines.get(0)));
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Paste is not created or code field is empty");
        }
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (WebElement element: codeLines) {
            counter +=1;
            builder.append(element.getText());
            if (counter < codeLines.size()) builder.append("\n");
        }
        return builder.toString();
    }

    public boolean isSyntaxStyleElementExists (SyntaxHighlighting syntaxHighlighting) {
        String syntax = syntaxHighlighting.getSyntax().toLowerCase();
        String xpath = "//a[@href='/archive/" + syntax + "']";
        logger.info(xpath);
        List<WebElement> elements = webDriver.findElements(By.xpath(xpath));
        boolean result = elements.isEmpty();
        logger.info(result + elements.toString());
        return !elements.isEmpty();
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
