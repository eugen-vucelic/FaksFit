package com.app.faksfit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegistrationTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://faksfit-7du1.onrender.com");

//        TODO: dodaj delete iz postgres baze
//        try {
//            String jdbcURL = "jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE";
//            String username = "sa";
//            String password = "";
//
//            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
//            System.out.println("Database connected!");
//
//            String emailToDelete = "testfaksfit@gmail.com";
//
//            String sqlDeleteQuery = "DELETE FROM students WHERE email = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteQuery);
//            preparedStatement.setString(1, emailToDelete);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            System.out.println(rowsAffected + " row(s) deleted.");
//
//            preparedStatement.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            System.out.println("Database connection error: " + e.getMessage());
//            e.printStackTrace();
//        }
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

        System.out.println("found the register page");

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

        Thread.sleep(5000);

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

//        TODO: dodaj assert da je na dashboardu
//        Assert.assertEquals();
    }
}
