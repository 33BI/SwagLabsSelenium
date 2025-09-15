import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class LoginTest extends BaseTest {        //Extends BaseTest

    @Test(priority = 1)     //Runs first
    public void validPageTitle() {
        System.out.println("Valid Page Title Test");    //Prints to Console
        test = extent.createTest("Valid Page Title Test");

        String pageTitle = driver.getTitle();   //Stores Page Title
        Assert.assertEquals(pageTitle, "Swag Labs");    //Checks if Page Title is Correct

        System.out.println("Page title is Correct");
        test.pass("Valid Page Title test passed.");
    }

    @Test(priority = 2)     //Runs after validPageTitle
    public void validLogin() {
        System.out.println("Valid Login Test");
        test = extent.createTest("Valid Login Test");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");   //Inputs Username
        driver.findElement(By.id("password")).sendKeys("secret_sauce");    //Inputs Password
        driver.findElement(By.id("login-button")).click();  //Clicks Login Button

        System.out.println("Login was Successful");
        test.pass("Valid Login test passed.");
    }

    @Test(dependsOnMethods = "validLogin")  //Depends on validLogin
    public void logoutTest() {
        System.out.println("Logout Test");
        test = extent.createTest("Logout Test");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  //Waits for 5 seconds before timing out
        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));    //Waits for Menu Button
        menuButton.click(); //Clicks Menu Button
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));    //Waits for Logout Link
        logoutLink.click(); //Clicks Logout Link

        System.out.println("Logout was Successful");
        test.pass("Logout test passed.");
    }

    @Test(dependsOnMethods = "logoutTest")
    public void invalidLogin() {
        System.out.println("Invalid Login Test");
        test = extent.createTest("Invalid Login Test");

        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.className("error-message-container"));  //Finds Error Message
        Assert.assertTrue(errorMessage.isDisplayed(), "Epic sadface: Sorry, this user has been locked out.");    //Checks if Error Message is Displayed

        System.out.println("Login Failed (User is Locked Out)");
        test.pass("Invalid Login test passed.");
    }

    @Test
    public void problemUserTest() {
        System.out.println("ProblemUser UI Test");
        test = extent.createTest("ProblemUser UI Test");

        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        List<WebElement> images = driver.findElements(By.xpath("//img[@alt='Sauce Labs Backpack']"));  //Finds Image
        for (WebElement img : images) {
            String src = img.getDomAttribute("src");    //Gets Image Source

            if (src == null || src.isEmpty()) {     //Checks if Image Source is Null or Empty
                Assert.fail("Image source is null or empty, possible UI issue!");
            }
            else if (src.contains("sl-404")) {      //Checks if Image Source contains "sl-404"
                    System.out.println("Problem user has an Incorrect Image (Dog instead of a Backpack): " + src);
            }
            else {
                    System.out.println("Correct image found: " + src);
            }
        }

        logoutTest();   //Logs Out User
        test.pass("ProblemUser UI test passed.");
    }

    @Test
    public void performanceGlitchUserTest() {
        System.out.println("Performance Glitch User UI Test");
        test = extent.createTest("Performance Glitch User UI Test");

        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        long startTime = System.currentTimeMillis();    //Starts Timer
        driver.findElement(By.id("login-button")).click();
        long endTime = System.currentTimeMillis();      //Ends Timer
        long duration = (endTime - startTime) / 1000; // Convert to Seconds and Calculates Duration

        if (duration > 2) {     //Checks if Duration is longer than 2 seconds
            System.out.println("Performance Issue: Login took longer than 2 seconds!");
        } else {
            System.out.println("Login was fast, no performance issue detected.");
        }

        logoutTest();
        test.pass("Performance Glitch User UI test passed.");
    }

    @Test
    public void visualUserTest() {
        System.out.println("VisualUser UI Test");
        test = extent.createTest("VisualUser UI Test");
        driver.findElement(By.id("user-name")).sendKeys("visual_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        List<WebElement> itemPrice = driver.findElements(By.xpath("//div[@id='inventory_container']//div[2]//div[2]//div[2]//div[1]"));
        for (WebElement price : itemPrice) {    //Finds Price
            String text = price.getText();  //Gets Text
            if (text.contains("9.99")) {    //Checks if Text contains "9.99"
                System.out.println("Correct Price: " + text);
            }
            else {
                System.out.println("Incorrect Price (Should be $9.99): " + text);
            }
        }

        logoutTest();
        test.pass("VisualUser UI test passed.");
    }
}