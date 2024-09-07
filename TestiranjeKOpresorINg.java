import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.ITestResult;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;






import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;


public class TestiranjeKOpresorINg {

    WebDriver driver;
    ExtentReports extent;
    ExtentSparkReporter reporter;
    ExtentTest test;

    @BeforeTest

    public void SetUp(){
        driver = new ChromeDriver();
        driver.get("https://kompresoring.com/");
        // Set up ExtentReports
        String path = System.getProperty("user.dir") + "\\reports\\kompresor.html";
        reporter = new ExtentSparkReporter(path);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Nemanja Nikitovic");
        reporter.config().setReportName("Rezultati Testiranja");
        reporter.config().setDocumentTitle("Web testiranje Sajta");


    }

    @Test

    public void testLanguageSwitch(){
        test = extent.createTest("testLanguageSwitch");
       // WebElement SrpskaZastava = driver.findElement(By.xpath("//img[@class='trp-flag-image' and @title='Srpski']"));

        //SrpskaZastava.click();
        //Assert.assertTrue(driver.getPageSource().contains("Zajedno gradimo budućnost"), "Sajt nije na srpskom");


        WebElement EngleskaZastava = driver.findElement(By.xpath("//img[@class='trp-flag-image' and @title='English']"));
        EngleskaZastava.click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "NEWS"));


        Assert.assertTrue(driver.getPageSource().contains("NEWS"), "Sajt je na engleskom");


        test.pass("Test uspešno prošao");

    }

    @AfterMethod

    public void TearDown(ITestResult result){
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destDir = new File("screenshots");
                if (!destDir.exists()) {
                    destDir.mkdirs();  // Kreiraj folder ako ne postoji
                }
                File destFile = new File(destDir, result.getName() + ".png");
                Files.copy(srcFile.toPath(), destFile.toPath());
                System.out.println("Snimljen screenshot: " + destFile.getAbsolutePath());

                // Log failure in ExtentReports
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(destFile.getAbsolutePath()).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destDir = new File("screenshots");
                if (!destDir.exists()) {
                    destDir.mkdirs();  // Kreiraj folder ako ne postoji
                }
                File destFile = new File(destDir, result.getName() + ".png");
                Files.copy(srcFile.toPath(), destFile.toPath());
                System.out.println("Snimljen screenshot: " + destFile.getAbsolutePath());


                test.pass("Test passed", MediaEntityBuilder.createScreenCaptureFromPath(destFile.getAbsolutePath()).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        driver.quit();
        extent.flush();
    }


}
