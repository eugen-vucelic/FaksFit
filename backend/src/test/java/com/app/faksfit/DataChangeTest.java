package com.app.faksfit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class DataChangeTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/");

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login() throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String email = "testfaksfit@gmail.com";
        String password = "p.assword123";

//      gumb Prijava
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("link-button")));
        driver.findElement(By.className("link-button"))
                .click();

//      unos emaila
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
        driver.findElement(By.id("identifierId"))
                .sendKeys(email);
        Thread.sleep(2000);
//      next
        driver.findElement(By.xpath("//button[contains(@class, 'nCP5yc')]"))
                .click();

//      unos lozinke
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd"))
                .sendKeys(password);
        Thread.sleep(5000);
//      continue
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'nCP5yc')]")));
        driver.findElement(By.xpath("//button[contains(@class, 'nCP5yc')]"))
                .click();
//      continue
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/c-wiz/div/div[3]/div/div/div[2]/div/div/button")));
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/c-wiz/div/div[3]/div/div/div[2]/div/div/button"))
                .click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @Test(dependsOnMethods = {"login"})
    public void dataChange() throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'profile')]")));
        driver.findElement(By.xpath("//a[contains(@href, 'profile')]"))
                .click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ime")));

        driver.findElement(By.id("ime")).clear();
        driver.findElement(By.id("ime"))
                .sendKeys("Test");
        Thread.sleep(2000);

        driver.findElement(By.id("prezime")).clear();
        driver.findElement(By.id("prezime"))
                .sendKeys("Test");
        Thread.sleep(2000);

        WebElement gender = driver.findElement(By.id("spol"));
        Select genderSelect = new Select(gender);
        genderSelect.selectByVisibleText("Ž");
        Thread.sleep(2000);

        driver.findElement(By.id("nacionalnost")).clear();
        driver.findElement(By.id("nacionalnost"))
                .sendKeys("Hrvatica");
        Thread.sleep(2000);

        driver.findElement(By.id("datRod")).clear();
        driver.findElement(By.id("datRod"))
                .sendKeys("23022004");
        Thread.sleep(2000);

        driver.findElement(By.id("broj")).clear();
        driver.findElement(By.id("broj"))
                .sendKeys("0999999888");
        Thread.sleep(2000);

        WebElement semestar = driver.findElement(By.id("semestar"));
        Select semestarSelect = new Select(semestar);
        semestarSelect.selectByVisibleText("3");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[@type='submit']"))
                .click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'profile')]")));
        driver.findElement(By.xpath("//a[contains(@href, 'profile')]"))
                .click();

        Thread.sleep(10000);
        Assert.assertTrue(driver.getCurrentUrl().contains("profile"));
    }
}
