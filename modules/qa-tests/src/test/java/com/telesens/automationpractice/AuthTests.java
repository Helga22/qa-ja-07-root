package com.telesens.automationpractice;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AuthTests {
    private WebDriver driver;
    private String baseUrl = "http://automationpractice.com/index.php";
    Properties prop = new Properties();
    CharSequence login;
    CharSequence password;

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setUp(String browser) throws Exception {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "D:/drivers/selenium/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "D:/drivers/selenium/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        InputStream inputStream = null;
        try {
            String propFileName = "automationpractice.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
                login = prop.getProperty("login");
                password = prop.getProperty("password");
            } else {
                throw new FileNotFoundException("property file :" + propFileName + "not found in the classpath");
            }


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }

    ;

    @Ignore
    public void testAuthSuccess() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.id("SubmitLogin")).click();
        WebElement usernameElement = driver.findElement(By.xpath("//a[@title='View my customer account']/span"));
        String usernameElementText = usernameElement.getText();
        Assert.assertEquals(usernameElementText, "test test");
        driver.findElement(By.linkText("Sign out")).click();
    }

//    @Test(dataProvider = "invalidInputProvider")
//    public void testAuthErrorMessage(String invalidEmail, String invalidPW) throws Exception {
//        driver.get(baseUrl);
//        driver.findElement(By.linkText("Sign in")).click();
//        driver.findElement(By.id("email")).click();
//        driver.findElement(By.id("email")).clear();
//        driver.findElement(By.id("email")).sendKeys(invalidEmail);
//        driver.findElement(By.id("passwd")).click();
//        driver.findElement(By.id("passwd")).clear();
//        driver.findElement(By.id("passwd")).sendKeys(invalidPW);
//        driver.findElement(By.id("SubmitLogin")).click();
//        WebElement errorMsg = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[1]/ol/li"));
//        String actualError = errorMsg.getText();
//        Assert.assertEquals(actualError, "Invalid email address.");
//    }
//
//    @DataProvider(name = "invalidInputProvider")
//    public Object[][] provideData() {
//        return new Object[][]{
//                {"10", "20"},
//                {"giufhovedhf30", "password"},
//                {"emmmm", "####"}
//        };
//    }

    @Test
    public void testWomenCategory() {
        /*
        3) Реализовать автотест для сайта automationpractice.com:
	Сценарий:
		- перейти на главную страницу по ссылке: http://automationpractice.com
		- в меню (WOMEN | DRESSES | T-SHIRTS) кликнуть пункт 'WOMEN'
		- Сделать следующие проверки:
			1) страница title содержит слово "Women"
			2) навигатор страниц содержит пункт "women"
			3) слева над каталогом расположен раздел "WOMEN"
			4) по середение содержится текст с заголовком "Women" и содержимое текста начинается
				"You will find here all woman fashion..."
			5) наличие под текстом в середине категории "WOMEN" "There are 7 products". (Число 7 может быть другим)
			6) в футоре внизу наличие "Women" в пункте "Categories"
         */
        WebElement womenCategory = driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(1) > a"));

        WebElement pageTitle = driver.findElement(By.cssSelector("head > title"));
        WebElement navigationMenu = driver.findElement(By.className("sf-menu clearfix menu-content sf-js-enabled sf-arrows"));
        WebElement blockWomen = driver.findElement(By.id("categories_block_left"));
        WebElement banner = driver.findElement(By.className("content_scene_cat_bg"));
        WebElement womenCatHeading = driver.findElement(By.className("page-heading product-listing"));
        WebElement footerCat = driver.findElement(By.className("blockcategories_footer footer-block col-xs-12 col-sm-2"));

        driver.get(baseUrl);
        womenCategory.click();

        Assert.assertEquals(true, pageTitle.isDisplayed());
        Assert.assertEquals(true, navigationMenu.isDisplayed());
        Assert.assertEquals(true, blockWomen.isDisplayed());
        Assert.assertEquals(true, banner.isDisplayed());
        Assert.assertEquals(true, womenCatHeading.isDisplayed());
        Assert.assertEquals(true, footerCat.isDisplayed());


    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
