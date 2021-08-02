package com.example.selenium;

import org.openqa.selenium.chrome.ChromeDriver;

import static com.example.selenium.MainPage.URL_MAIN_PAGE;

public class RecentPage {

    private final ChromeDriver driver;

    public RecentPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public RecentPage callPage() {
        driver.get(URL_MAIN_PAGE+"recent");
        return this;
    }

    public RecentPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }


}
