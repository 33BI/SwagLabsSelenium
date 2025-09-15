import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @BeforeClass
    public void login() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @Test(priority = 1)
    public void addToCartTest() {
        System.out.println("Testing Adding Item to the Cart");
        test = extent.createTest("Testing Adding Item to the Cart");

        driver.findElement(By.className("btn_inventory")).click();
        System.out.println("Clicked on 'Add to Cart' button");

        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertTrue(cartBadge.isDisplayed(), "Item was not added to the cart!");  //Checks if cart badge is displayed
        Assert.assertEquals(cartBadge.getText(), "1", "Cart count is incorrect!");

        System.out.println("Item successfully added to the cart!");
        test.pass("Item successfully added to the cart!");
    }

    @Test(priority = 2, dependsOnMethods = "addToCartTest")
    public void removeFromCartTest() {
        System.out.println("Testing Removing Item from the Cart");
        test = extent.createTest("Testing Removing Item from the Cart");

        driver.findElement(By.className("shopping_cart_link")).click();
        System.out.println("Opened cart");

        driver.findElement(By.className("cart_button")).click();
        System.out.println("Clicked on Remove button");

        boolean cartBadgeExists = !driver.findElements(By.className("shopping_cart_badge")).isEmpty();  //Checks if cart badge exists
        Assert.assertFalse(cartBadgeExists, "Cart is not empty after removing the item!");

        System.out.println("Item successfully removed from cart!");
        test.pass("Item successfully removed from cart!");
    }

}