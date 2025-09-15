import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @BeforeClass
    public void loginAndAddItem() {
        System.out.println("Logging in and adding an item to the cart...");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.className("btn_inventory")).click();
        System.out.println("Item added to cart.");
    }

    @Test(priority = 1)
    public void proceedToCheckoutTest() {
        System.out.println("Proceeding to Checkout...");
        test = extent.createTest("Proceeding to Checkout");

        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        WebElement checkoutForm = driver.findElement(By.id("checkout_info_container"));
        Assert.assertTrue(checkoutForm.isDisplayed(), "Checkout page did not open!");

        System.out.println("Checkout page opened successfully.");
        test.pass("Checkout page opened successfully.");
    }

    @Test(priority = 2, dependsOnMethods = "proceedToCheckoutTest")
    public void fillCheckoutInfoTest() {
        System.out.println("Filling checkout Information...");
        test = extent.createTest("Filling checkout Information");

        driver.findElement(By.id("first-name")).sendKeys("Umang");
        driver.findElement(By.id("last-name")).sendKeys("Chaturvedi");
        driver.findElement(By.id("postal-code")).sendKeys("201301");
        driver.findElement(By.id("continue")).click();
        WebElement overviewPage = driver.findElement(By.id("checkout_summary_container"));
        Assert.assertTrue(overviewPage.isDisplayed(), "Checkout overview page not reached!");

        System.out.println("Checkout information submitted successfully.");
        test.pass("Checkout information submitted successfully.");
    }

    @Test(priority = 3, dependsOnMethods = "fillCheckoutInfoTest")
    public void finishCheckoutTest() {
        System.out.println("Finishing Checkout Process...");
        test = extent.createTest("Finishing Checkout Process");

        driver.findElement(By.id("finish")).click();
        WebElement confirmationMessage = driver.findElement(By.className("complete-header"));    //Finds Confirmation Message
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Order completion message not shown!");
        Assert.assertEquals(confirmationMessage.getText(), "Thank you for your order!", "Wrong confirmation message!");

        System.out.println("Checkout completed. Order placed successfully.");
        test.pass("Checkout completed. Order placed successfully.");
    }
}
