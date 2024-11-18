package com.github.sergey_guzov.pages.pastebin;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class PasteBinAbstractPage {

    protected WebDriver webDriver;
    protected JavascriptExecutor js;

    protected abstract PasteBinAbstractPage openPage ();
    protected abstract void scrollPageTo (WebElement element);

    protected PasteBinAbstractPage (WebDriver webDriver) {
        this.webDriver = webDriver;
        this.js = (JavascriptExecutor) webDriver;
        PageFactory.initElements(webDriver,this);
    }

}
