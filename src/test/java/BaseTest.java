import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;   //Declares Driver

    @BeforeSuite
    public void setupReport() {     //Sets up Extent Report
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        System.out.println("Extent Report Setup Complete.");
    }

    @BeforeClass    //Runs before all tests
    public void setUp() {
        WebDriverManager.chromedriver().setup();    //Sets up Chrome
        driver = new ChromeDriver();    //Launches Chrome
        driver.manage().window().maximize();    //Maximizes Window
        driver.get("https://www.saucedemo.com/");      //Navigates to Swag Lab
    }

    @AfterMethod        //Runs after each test
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed successfully.");
        } else {
            test.skip("Test skipped.");
        }
    }

    @AfterClass     //Runs after all tests
    public void tearDown() throws InterruptedException {    //Interrupts Thread
        if (driver != null) {   //Checks if Driver is not Null
            driver.quit();  //Quits Driver
        }
        Thread.sleep(1500);     //Waits for 1.5 seconds
    }

    @AfterSuite     //Runs after all Suites
    public void generateReport() {
        System.out.println("Flushing Extent Reports...");
        extent.flush();
        System.out.println("Extent Report generated at: test-output/ExtentReport.html");
    }
}