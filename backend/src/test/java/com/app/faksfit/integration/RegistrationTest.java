package com.app.faksfit.integration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegistrationTest {

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
    public void registration() throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String email = "testfaksfit@gmail.com";
        String password = "p.assword123";
        String firstName = "Testni";
        String lastName = "Primjer";
        String jmbag = "0036555555";

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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ime")));

        driver.findElement(By.id("ime"))
                .sendKeys(firstName);
        Thread.sleep(1000);
        driver.findElement(By.id("prezime"))
                .sendKeys(lastName);
        Thread.sleep(1000);
        driver.findElement(By.id("jmbag"))
                .sendKeys(jmbag);
        Thread.sleep(1000);
        driver.findElement(By.id("suglasnost"))
                .click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@type='submit']"))
                .click();

        wait.until(ExpectedConditions.urlContains( "dashboard"));
        driver.navigate().refresh();

        Thread.sleep(10000);

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }
}
