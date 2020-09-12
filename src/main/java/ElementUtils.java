import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class ElementUtils extends WebDrivers {
    /**
     * @author: Orhan Demirci
     */
    public static WebDriver driver;

    /**
     *
     * @param browser
     * @return
     */
    public static WebDriver getDriver(String browser){
//        System.setProperty("webdriver.chrome.driver","C:\\Users\\demir\\SliconLab\\chromedriver.exe");
        if(browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        }
        else if(browser.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        }
        else if(browser.equals("edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        }
        return driver;
    }

    /**
     *
     * @param driver
     * @param locator
     * @return
     */
    public static WebElement getElement(WebDriver driver, By locator){
        WebElement element = driver.findElement(locator);
        return element;
    }

    /**
     *
     * @param driver
     * @param url
     */

    public static void navigateToUrl(WebDriver driver, String url){
        driver.get(url);
    }

    /**
     *
     * @param driver
     * @throws IOException
     */
    public static void screenShot(WebDriver driver) throws IOException {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src,new File("C:\\Users\\demir\\SliconLab\\target\\screenhots\\pic.png"));
    }

    /**
     *
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void alertAccept(WebDriver driver,By locator) throws InterruptedException {
        WebElement alrt = driver.findElement(locator);
        alrt.click();
        Thread.sleep(1000);
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        System.out.println(text);
        Assert.assertEquals(text,"Please enter a valid user name","Fail");
        alert.accept();

    }

    /**
     *
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void alertDismiss(WebDriver driver,By locator) throws InterruptedException {
        WebElement alrt = driver.findElement(locator);
        alrt.click();
        Thread.sleep(1000);
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        System.out.println(text);
        Assert.assertEquals(text,"Please enter a valid user name","Fail");
        alert.dismiss();

    }

    /**
     *
     * @param driver
     * @param locator
     * @param value
     * @throws InterruptedException
     */
    public static void dropDownByText(WebDriver driver, By locator, String value ) throws InterruptedException {
        WebElement drp = driver.findElement(locator);
        Select s = new Select(drp);
        System.out.println("Is multiple dropdown? : "+s.isMultiple());
        s.selectByVisibleText(value);
        Thread.sleep(1000);
    }

    /**
     *
     * @param driver
     * @param locator
     * @param value
     * @throws InterruptedException
     */
    public static void dropDownByIndex(WebDriver driver, By locator, int value ) throws InterruptedException {
        WebElement drp = driver.findElement(locator);
        Select s = new Select(drp);
        System.out.println("Is multiple dropdown? : "+s.isMultiple());
        s.selectByIndex(value);
        Thread.sleep(1000);
    }

    /**
     *
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void scrollDownToElement(WebDriver driver,By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement element = driver.findElement(locator);
        Thread.sleep(1000);
        js.executeScript("arguments[0].scrollIntoView(true);",element);
        Thread.sleep(1000);
    }

    /**
     *
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void highlightElement(WebDriver driver,By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement element = driver.findElement(locator);
        Thread.sleep(1000);
        js.executeScript("arguments[0].style.border='7px dotted blue'",element);
        Thread.sleep(1000);
    }
}
