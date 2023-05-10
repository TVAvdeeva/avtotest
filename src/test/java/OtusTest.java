import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.apache.commons.lang3.StringUtils.substring;

public class OtusTest {

    protected WebDriver driver;
    private final String login=System.getProperty("login");
    private final String password = System.getProperty("password");
    private String  url=System.getProperty("url");
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(OtusTest.class);

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
     }

    @AfterEach
    public void close(){
        if (driver != null)
            driver.close();
            driver.quit();
    }

    @Test
    public void firstTest() {


        convertUrl();
        driver.get(url);
        loginInOtus();
        enterLP();
        setPrivateDataInfo();
        setContactInfo();
        logger.info("Кейс ввода персональных данных пройден ");

        //   Открыть https://otus.ru в “чистом браузере”

        if (driver != null) {
            driver.close();
            driver.quit();
        }
        proverka();

    }

    private void proverka(){
        setUp();
        driver.get(url);
        loginInOtus();
//        Войти в личный кабинет
        enterLP();
        checkTextValueArea(driver.findElement(By.id("id_fname")), "Мария");
        checkTextValueArea(driver.findElement(By.id("id_fname_latin")), "Maria");
        checkTextValueArea(driver.findElement(By.id("id_lname")), "Иванова");
        checkTextValueArea(driver.findElement(By.id("id_lname_latin")), "Ivanova");
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_blog_name']")), "testblok");
        checkTextValueArea(driver.findElement(By.xpath("//input[@name='date_of_birth']")), "01.01.1985");
        checkTextTextArea(driver.findElement(By.xpath("//div[contains(text(),'Россия')]")), "Россия");
        checkTextTextArea(driver.findElement(By.xpath("//div[contains(text(),'Владивосток')]")), "Владивосток");
        checkTextTextArea(driver.findElement(By.xpath("//div[contains(text(),'Средний (Intermediate)')]")), "Средний (Intermediate)");
        checkTextTextArea(driver.findElement(By.xpath("//div[contains(text(),'Skype')]")), "Skype");
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_contact-0-value']")), "fox123)");
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_contact-1-value']")), "+79114967878");
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_company']")), "WB");
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_work']")), "Инженер");
        clearContact ();

    }

    private void loginInOtus(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header3__button-sign-in"))).click();
        WebElement form = driver.findElement(By.xpath("//form[@action = '/login/']"));
        form.findElement(By.xpath(".//input[@name='email']")).sendKeys(login);
        form.findElement(By.xpath(".//input[@name='password']")).sendKeys(password);
        form.findElement(By.xpath(".//button[@type='submit']")).submit();
    }

    private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    private void enterLP(){
       new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions
                        .invisibilityOf(
                                driver.findElement(By.cssSelector(".header3__container"))));
        driver.findElement(By.cssSelector(".header3__user-info-name")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Мой профиль')]")).click();
        logger.info("Открываем личный кабинет");
    }
    private void convertUrl() {

        if (url.trim().endsWith("/")) {
            url= substring(url,0,url.length()-1);
        }
        url=url.toLowerCase();

    }
    private WebElement getElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(50));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    private void checkTextValueArea(WebElement element, String expectedText) {
        Assertions.assertEquals(expectedText, element.getAttribute("value"));
    }
    private void checkTextTextArea(WebElement element, String expectedText) {
        Assertions.assertEquals(expectedText, element.getText());
    }

    private void findElemSetValue(By locator, String value) {
        WebElement element = getElementClickable(locator);//wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);

    }
    private void setPrivateDataInfo() {
        final String name = "Мария";
        final String nameLatin = "Maria";
        final String lastName = "Иванова";
        final String lastNameLatin = "Ivanova";
        final String blogName = "testblok";
        final String dateOfBirth = "01.01.1985";


        findElemSetValue(By.id("id_fname"),name );
        findElemSetValue(By.id("id_lname"), lastName);
        findElemSetValue(By.id("id_fname_latin"),nameLatin);
        findElemSetValue(By.id("id_lname_latin"), lastNameLatin);
        findElemSetValue(By.id("id_blog_name"),blogName);
        findElemSetValue(By.xpath("//input[@name='date_of_birth']"), dateOfBirth);

    }

    private void setContactInfo() {


        getElementClickable(By.xpath("//input[@name='country']/following-sibling::div")).click();
        getElementClickable(By.xpath("//button[contains(text(),'Россия')]")).click();
        getElementClickable(By.xpath("//input[@name='city']/following-sibling::div/span")).click();
        getElementClickable(By.xpath("//button[contains(text(),'Владивосток')]")).click();
        getElementClickable(By.xpath("//input[@name='english_level']/following-sibling::div")).click();
        getElementClickable(By.xpath("//button[contains(text(),'Средний (Intermediate)')]")).click();
        getElementClickable(By.xpath("//span[contains(text(),'Да')]")).click();

        getElementClickable(By.xpath("//span[contains(text(),'Способ связи')]")).click();
        getElementClickable(By.xpath("//button[contains(text(),'Skype')]")).click();//input[@name='contact-%s-value']
        findElemSetValue(By.xpath("//input[@id='id_contact-0-value']"), "fox123)");
        getElementClickable(By.xpath("//button[contains(text(),'Добавить')]")).click();
        findElemSetValue(By.xpath("//input[@id='id_contact-1-value']"), "+79114967878");
        getElementClickable(By.xpath("//span[contains(text(),'Способ связи')]")).click();
        getElementClickable(By.xpath("//button[contains(text(),' Тelegram')]")).click();
        getElementClickable(By.xpath("//select[@id='id_gender']")).click();
        getElementClickable(By.xpath("//option[contains(text(),'Женский')]")).click();
        findElemSetValue(By.xpath("//input[@id='id_company']"), "WB");
        findElemSetValue(By.xpath("//input[@id='id_work']"), "Инженер");
        driver.findElement(By.cssSelector(".button_md-4:nth-child(1)")).submit();


    }
      public void  clearContact (){
        driver.findElement(By.xpath("//input[@id='id_contact-0-value']")).clear();
        driver.findElement(By.xpath("//input[@id='id_contact-1-value']")).clear();
        driver.findElement(By.cssSelector(".button_md-4:nth-child(1)")).submit();
    }
}

