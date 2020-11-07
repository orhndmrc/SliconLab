import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author : Orhan Demirci
 */
public class ElementUtils {

    /**
     * @param driver
     * @param browser
     */

    public static WebDriver getDriver(WebDriver driver, String browser) {
//        System.setProperty("webdriver.chrome.driver","C:\\Users\\demir\\SliconLab\\chromedriver.exe");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        } else if (browser.equals("headless")) {
            WebDriverManager.chromedriver().setup();
            driver = new HtmlUnitDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        }
        return driver;
    }

    /**
     * @param driver
     * @param locator
     */
    public static void click(WebDriver driver, By locator) {
        driver.findElement(locator).click();
    }

    /**
     * @param driver
     * @param locator
     * @return
     */
    public static WebElement getElement(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        return element;
    }


    /**
     * @param driver
     * @param url
     */
    public static void navigateToUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    /**
     * @param driver
     * @throws IOException
     */
    public static void screenShot(WebDriver driver) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("C:\\Users\\demir\\SliconLab\\target\\screenhots\\pic.png"));
    }

    /**
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void alertAccept(WebDriver driver, By locator) throws InterruptedException {
        WebElement alrt = driver.findElement(locator);
        alrt.click();
        Thread.sleep(1000);
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        System.out.println(text);
        alert.accept();

    }

    /**
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void alertDismiss(WebDriver driver, By locator) throws InterruptedException {
        WebElement alrt = driver.findElement(locator);
        alrt.click();
        Thread.sleep(1000);
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        System.out.println(text);
        Assert.assertEquals(text, "Please enter a valid user name", "Fail");
        alert.dismiss();

    }

    /**
     * @param driver
     * @param locator
     * @param value
     * @throws InterruptedException
     */
    public static void dropDownByText(WebDriver driver, By locator, String value) throws InterruptedException {
        WebElement drp = driver.findElement(locator);
        Select s = new Select(drp);
        System.out.println("Is multiple dropdown? : " + s.isMultiple());
        s.selectByVisibleText(value);

    }

    /**
     * @param driver
     * @param locator
     * @param value
     * @throws InterruptedException
     */
    public static void dropDownByIndex(WebDriver driver, By locator, int value) throws InterruptedException {
        WebElement drp = driver.findElement(locator);
        Select s = new Select(drp);
        System.out.println("Is multiple dropdown? : " + s.isMultiple());
        s.selectByIndex(value);
        Thread.sleep(1000);
    }

    /**
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void scrollDownToElement(WebDriver driver, By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        Thread.sleep(1000);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);
    }

    /**
     * @param driver
     * @param locator
     * @throws InterruptedException
     */
    public static void highlightElement(WebDriver driver, By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        Thread.sleep(1000);
        js.executeScript("arguments[0].style.border='7px dotted blue'", element);
        Thread.sleep(1000);
    }

    /**
     * @param driver
     * @param locator
     * @param value
     */
    public static void singleValueSelectionDropDown(WebDriver driver, By locator, String value) {
        List<WebElement> droplist = driver.findElements(locator);

        for (int i = 0; i < droplist.size(); i++) {
            String text = droplist.get(i).getText();
            System.out.println(text);

            try {
                if (!text.isEmpty()) {
                    if (text.equals(value)) {
                        droplist.get(i).click();
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * @param driver
     * @param locator
     * @param value
     */
    public static void multipleValueSelectionDropDown(WebDriver driver, By locator, String... value) {
        List<WebElement> droplist = driver.findElements(locator);
        for (int i = 0; i < droplist.size(); i++) {
            String text = droplist.get(i).getText();
            System.out.println(text);

            for (int j = 0; j < value.length; j++) {
                try {
                    if (!text.isEmpty()) {
                        if (text.equals(value[j])) {
                            droplist.get(i).click();
                        }
                    }
                } catch (Exception e) {

                }

            }

        }
    }


    //Table Handling
    public static int getTableRowCount(WebDriver driver, By locator) {
        List<WebElement> rowList = driver.findElements(locator);
        return rowList.size();
    }

    public static int getTableColumnCount(WebDriver driver, By locator) {
        List<WebElement> rowList = driver.findElements(locator);
        return rowList.size();
    }

    /**
     * @param driver
     * @param beforeXpath
     * @param afterXpath
     * @param completeXpath
     * @param locatorRow
     * @param locatorCol
     */
    public static void getTableData(WebDriver driver, String beforeXpath, String afterXpath, String completeXpath, By locatorRow, By locatorCol) {

        for (int rowNum = 2; rowNum <= ElementUtils.getTableRowCount(driver, locatorRow); rowNum++) {
            for (int colNum = 1; colNum <= ElementUtils.getTableRowCount(driver, locatorCol); colNum++) {
                String actualXpath = beforeXpath + rowNum + afterXpath + colNum + completeXpath;
                //System.out.println(actualXpath);
                String text = driver.findElement(By.xpath(actualXpath)).getText();
                System.out.println(text);
            }
            System.out.println("");
        }
    }

    /**
     * @param driver
     * @param waitTime
     * @param locator
     */
    public static void waitElement(WebDriver driver, int waitTime, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * @param driver
     * @param locator
     * @param filePath
     */
    public static void uploadFile(WebDriver driver, By locator, String filePath) {
        driver.findElement(locator).sendKeys(filePath);
    }

    /**
     * @param driver
     * @param username
     * @param password
     * @param partialUrl
     */
    public static void authenticationPopUp(WebDriver driver, String username, String password, String partialUrl) {
        driver.get("https://" + username + ":" + password + "@" + partialUrl);
    }

    public static void getScreenShot(WebDriver driver) {
        SimpleDateFormat df = new SimpleDateFormat("-yyyy-MM-dd-HH-mm");
        String date = df.format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs((OutputType.FILE));
        String target = System.getProperty("user.dir") + "/Screenshots/" + date + ".png";
        File finalDestination = new File(target);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void windowHandle(WebDriver driver, By clickLocator) {

        driver.findElement(clickLocator).click();
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> itr = handles.iterator();

        while (itr.hasNext()) {
            String parentWindow = itr.next();
            String childWindow = itr.next();
            System.out.println("Parent window is " +parentWindow);
            System.out.println("Child window is "+childWindow);
            driver.switchTo().window(childWindow);

        }
    }
}
