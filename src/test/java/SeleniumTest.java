//package com.example.tests;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.Select;

public class SeleniumTest extends BaseTest {
//    private WebDriver driver;


    @Before
    public void setUp() throws Exception {

//        driver = new ChromeDriver(@"C:\Workspace\automatyzacja-2016-11\src\main\resources");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        baseUrl = "https://automatyzacja2016.wordpress.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void shouldLogIntoPage() throws Exception {
        driver.get(baseUrl + "/wp-admin");
        login("szkolenieautomatyzacja", "QW12qw12");
        logout();
//        Assert.assertEquals(driver.getTitle(),"Obserwowanie");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @Test
    public void ShouldAddNewPost() {
        Random randomId = new Random();
        String title = "Moj nowy post"+String.valueOf(randomId.nextInt(1000000));
        String post ="Test Lukasz"+String.valueOf(randomId.nextInt(1000000));

        open("/wp-admin");
        login("szkolenieautomatyzacja", "QW12qw12");

        addNewPost(title, post);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logout();

        //check if post is added
        open("");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue("Nie ma postu o tytule: "+title,driver.getPageSource().contains(title));
        Assert.assertTrue("Nie ma postu o tresci: "+post,driver.getPageSource().contains(post));
    }

    private void addNewPost(String title, String post) {
        driver.findElement(By.xpath(".//*[@id='menu-posts']/a/div[3]")).click();
        driver.findElement(By.xpath(".//*[@id='menu-posts']/ul/li[3]/a")).click();
        driver.findElement(By.xpath(".//*[@id='title']")).sendKeys(title);
        driver.findElement(By.xpath(".//*[@id='content-html']")).click();
        driver.findElement(By.xpath(".//*[@id='content']")).sendKeys(post);
        driver.findElement(By.xpath(".//*[@id='publish']")).click();
    }


}
