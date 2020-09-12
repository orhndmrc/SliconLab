import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SeleniumBasics  {
    public WebDriver driver;
    @BeforeClass
    public void setUp(){
        driver=ElementUtils.getDriver("chrome");
    }

    @Test
    public void navigating(){
        //get: navigates to a webpage. WebDriver will wait until the page is fully loaded
        ElementUtils.navigateToUrl(driver,"https://www.google.com/");
        //driver.get("https://www.google.com/");
        System.out.println(driver.getTitle());

        //to: navigates to a webpage. WebDriver will not wait until the page is fully loaded
        driver.navigate().to("https://www.amazon.com/");
        System.out.println(driver.getTitle());

        driver.navigate().back();
        System.out.println(driver.getTitle());

        driver.navigate().forward();
        System.out.println(driver.getTitle());
    }
    @Test
    public void alertHandling() throws InterruptedException {
        ElementUtils.navigateToUrl(driver,"https://mail.rediff.com/cgi-bin/login.cgi");
        By alrt = By.xpath("//input[@title='Sign in']");
        ElementUtils.alertAccept(driver,alrt);
    }
    @Test
    public void takingScreenShot() throws IOException {
        ElementUtils.navigateToUrl(driver,"https://google.com");
        ElementUtils.screenShot(driver);

    }
    @Test
    public void takingScreenShotOfSpecificArea() throws IOException {
        driver.get("https://learnsdet.com");
        WebElement element = driver.findElement(By.xpath("//nav[@class='navbar navbar-expand-lg navbar-light header-navigation stricky original']//a//img"));

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].style.border='10px solid red'",element);
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        java.awt.Rectangle rect = new java.awt.Rectangle(width+600, height+600);

        BufferedImage img = null;
        img= ImageIO.read(src);

        BufferedImage dest = img.getSubimage(p.getX(),p.getY(),rect.width,rect.height);
        ImageIO.write(dest,"png",src);

        FileUtils.copyFile(src,new File("C:\\Users\\demir\\SliconLab\\target\\screenhots2\\pic.png"));

    }
    @Test
    public void FindElementsConcept(){
        driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
        List<WebElement> linklist = driver.findElements(By.tagName("a"));
        System.out.println("How many links in the dom? ===> "+linklist.size());
        for (int i = 0; i <linklist.size() ; i++) {
            String text = linklist.get(i).getText();
            //System.out.println(text);
            /* if(!text.isEmpty()){
                System.out.println(text);
            }*/
            if(text.equals("Forgot Password?")){
               linklist.get(i).click();
               break;
            }
        }
    }
    @Test
    public void multiDropDown() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        List<WebElement> drp = driver.findElements(By.xpath("//select[@id='dropdown']/option"));
        System.out.println(drp.size());
        for (int i = 0; i <drp.size() ; i++) {
            String options = drp.get(i).getText();
            System.out.println(options);

            //to ignore empty values
            if(!options.isEmpty()){
                Thread.sleep(1000);
                drp.get(i).click();
                Thread.sleep(1000);
            }

            //Put break to take only specific values
            if(options.equals("Option 1")){
                drp.get(i).click();
            }

        }
    }
    @Test
    public void dropDown() throws InterruptedException {
        ElementUtils.navigateToUrl(driver,"https://the-internet.herokuapp.com/dropdown");
        By drp = By.id("dropdown");
        ElementUtils.dropDownByIndex(driver,drp,0);
    }
    @Test
    public void scrollDown() throws InterruptedException {
        ElementUtils.navigateToUrl(driver,"https://www.cnn.com/");
        By element = By.xpath("//h2[text()='Travel']");
        ElementUtils.scrollDownToElement(driver,element);
        ElementUtils.highlightElement(driver,element);

    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
