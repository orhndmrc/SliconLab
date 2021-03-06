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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SeleniumBasics  {
    public WebDriver driver;
    @BeforeClass
    public void setUp(){
        driver=ElementUtils.getDriver(driver,"chrome");
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
    @Test
    public void singleValueDropDown(){
        ElementUtils.navigateToUrl(driver,"https://www.jqueryscript.net/demo/Drop-Down-Combo-Tree/");
        By select = By.xpath(" //input[@id='justAnInputBox']");
        ElementUtils.click(driver,select);
        By selection = By.xpath("//span[@class='comboTreeItemTitle']");
        ElementUtils.singleValueSelectionDropDown(driver,selection,"choice 6 2");

    }
    @Test
    public void multipleValueValueDropDown(){
        ElementUtils.navigateToUrl(driver,"https://www.jqueryscript.net/demo/Drop-Down-Combo-Tree/");
        By select = By.xpath(" //input[@id='justAnInputBox']");
        ElementUtils.click(driver,select);
        By selection = By.xpath("//span[@class='comboTreeItemTitle']");
        ElementUtils.multipleValueSelectionDropDown(driver,selection,"choice 7","choice 6 1","choice 4");
    }
    @Test
    public void tableHandling(){
        driver.get("https://www.w3schools.com/html/html_tables.asp");
        //*[@id="customers"]/tbody/tr[2]/td[1]
        //*[@id="customers"]/tbody/tr[3]/td[1]
        //*[@id="customers"]/tbody/tr[7]/td[1]

        //number of rows
        List<WebElement> rowList = driver.findElements(By.xpath("//*[@id=\"customers\"]//tr"));
        int rowCount = rowList.size();
        System.out.println(rowCount);

        //number of columns
        List<WebElement> columnList = driver.findElements(By.xpath("//*[@id=\"customers\"]//th"));
        int columnCount = columnList.size();
        System.out.println(columnCount);

        String beforeXpath = "//*[@id=\"customers\"]/tbody/tr[";
        String afterXpath = "]/td[";
        String completeXpath ="]";

        for (int rowNum = 1; rowNum <= rowCount ; rowNum++) {
            for (int colNum = 1; colNum <= columnCount ; colNum++) {
                String actualXpath = beforeXpath + rowNum + afterXpath + colNum + completeXpath;
                //System.out.println(actualXpath);
               String text = driver.findElement(By.xpath(actualXpath)).getText();
               System.out.println(text);
            }
            System.out.println("");
        }
    }
    @Test
    public void exampleTableHandling(){
        ElementUtils.navigateToUrl(driver,"https://rahulshettyacademy.com/AutomationPractice/");
        String beforeXpath = "//*[@id=\"product\"]/tbody/tr[";
        String afterXpath = "]/td[";
        String completeXpath = "]";
        By locatorRow =By.xpath("//*[@id=\"product\"]//tr");
        By locatorCol = By.xpath("//*[@id=\"product\"]//th");
        ElementUtils.getTableData(driver,beforeXpath,afterXpath,completeXpath,locatorRow,locatorCol);
    }
    @Test
    public void uploadingFiles(){
        driver.get("https://cgi-lib.berkeley.edu/ex/fup.html");
        driver.findElement(By.xpath("//input[@name='upfile']")).sendKeys("C:\\Users\\demir\\Desktop\\Capture.jpg");
    }
    @Test
    public void authenticationPopUp() throws InterruptedException {
//       driver.get("https://the-internet.herokuapp.com/basic_auth");
//       Thread.sleep(2000);
//      username = "admin";
//      password = "admin";
      //driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        ElementUtils.authenticationPopUp(driver,"admin","admin","the-internet.herokuapp.com/basic_auth");
        Thread.sleep(2000);

    }
    @Test
    public void windowHandling(){
        driver.get("http://www.popuptest.com/goodpopups.html");
        driver.findElement(By.className("black")).click();

        Set<String> handles = driver.getWindowHandles();
        Iterator<String> it = handles.iterator();
        String parentWindowID = it.next();
        System.out.println("parent window is: "+parentWindowID);

        String childWindowID = it.next();
        System.out.println("child window is: "+childWindowID);
    }
    @Test
    public void javaScriptExecuter(){
        driver.get("http://darksky.net");
        System.out.println(JavaScriptUtil.getTitleByJS(driver));
    }
    @Test
    public void frameHandling(){
        driver.get("http://londonfreelance.org/courses/frames/index.html");

        driver.switchTo().frame(driver.findElement(By.name("main")));
        WebElement header = driver.findElement(By.xpath("//h2[contains(text(),'Title bar')]"));
        System.out.println(header.getText());
        System.out.println(driver.getTitle());
        driver.switchTo().defaultContent();//getting back to the main page
        System.out.println();

    }
    @Test
    public void selectWithoutSelectClass() throws InterruptedException {
        driver.get("https://www.amazon.com/");
        By options = By.xpath("//*[@id='searchDropdownBox']//option");
        ElementUtils.singleValueSelectionDropDown(driver,options,"Amazon Warehouse");
        Thread.sleep(3000);

    }
    @Test
    public void multipleSelection() throws InterruptedException {
        driver.get("https://www.jqueryscript.net/demo/Drop-Down-Combo-Tree/");
        By selectBox = By.id("justAnInputBox");
        ElementUtils.click(driver,selectBox);
        By multi = By.xpath("//span[@class='comboTreeItemTitle']");
        ElementUtils.multipleValueSelectionDropDown(driver,multi,"choice 4","choice 2 3","choice 7","choice 6 1");
        Thread.sleep(3000);
    }
    @Test
    public void alertHandle() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        By alerButton = By.xpath("//*[text()='Click for JS Confirm']");
        ElementUtils.alertAccept(driver,alerButton);

    }
    @Test
    public void frameHandling2(){
        driver.get("http://londonfreelance.org/courses/frames/index.html");
        driver.switchTo().frame(4);
        System.out.println(driver.findElement(By.xpath("//*[contains(text(),'provides alternate navigation')]")).getText());
    }
    @Test
    public void windowHandling2() throws InterruptedException {
        driver.get("https://promo.bankofamerica.com/multiproduct/?cm_mmc=ENT-Consumer-_-Google-PS-_-bankofamerica-_-Brand_CoreBrand&gclid=Cj0KCQjwtsv7BRCmARIsANu-CQc51TXF_idY4AENSaCcem3cM9vnDCuFRv-K5Nc9i4xF8eNne9Q1Jw8aAhw2EALw_wcB&gclsrc=aw.ds");
        By clickButton = By.xpath("//div[@class='tile credit front']//a[@id='Card_GetStarted_MultiproductRWD']");
        Thread.sleep(5000);
        ElementUtils.windowHandle(driver,clickButton);
        System.out.println(driver.findElement(By.xpath("//h2[contains(text(),'res & ben')]")).getText());
    }
    @Test
    public void amazonList(){
        driver.get("https://www.amazon.com/");
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='searchDropdownBox']/option"));
        for (int i = 0; i < list.size() ; i++) {
            String text = list.get(i).getText();
            System.out.println(text);
            if(text.contains("Electronics")){
                list.get(i).click();
                break;
            }

        }
    }
    @Test
    public void amazonListSelect(){
        driver.get("https://www.amazon.com/");
        Select sel = new Select(driver.findElement(By.xpath("//*[@id='searchDropdownBox']")));
        sel.selectByVisibleText("Electronics");
    }
//    @AfterClass
//    public void tearDown() throws IOException {
//        driver.quit();
//
//    }
}
