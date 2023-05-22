import data.residence.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import data.personals.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

import static org.apache.commons.lang3.StringUtils.substring;

public class OtusTest {

    private  WebDriver driver;
    private final String login=System.getProperty("login");
    private final String password = System.getProperty("password");
    private String  url=System.getProperty("url");
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(OtusTest.class);

    @BeforeAll
    public static void beforeTest() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void close(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
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

        close();
        proverka();

    }

    private void loginInOtus(){
        getElementClickable(By.cssSelector(".header3__button-sign-in")).click();
        waitVisible(By.cssSelector(".js-login"));
        //input.new-input:nth-child(2)

        driver.findElement(By.cssSelector(".js-email-input")).sendKeys(login);
        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password);
        driver.findElement(By.cssSelector(".new-button_md")).submit();
        waitClickInvisible(By.xpath("//div[contains(@class, 'modal-container')][1]"));
    }

    private void enterLP(){

        // WebElement form=driver.findElement(By.cssSelector(".header3__container"));
        waitVisible(By.cssSelector(".header3__container"));
        getElementClickable(By.cssSelector(".header3__user-info-name")).click();
        getElementClickable(By.cssSelector("a.header3__user-info-popup-link:nth-child(1)")).click();
        logger.info("Открываем личный кабинет");
    }
    private void convertUrl() {

        if (url.trim().endsWith("/")) {
            url= substring(url,0,url.length()-1);
        }
        url=url.toLowerCase();

    }

    public void waitVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Assertions.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed());
    }

    public void waitClickInvisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(5));
        Assertions.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)));
    }

    private WebElement getElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    private void checkTextTextArea( String expectedText) {
        String xpath = String.format("//div[contains(text(),'%s')]", expectedText);
        WebElement element=driver.findElement(By.xpath(xpath));
        Assertions.assertEquals(expectedText, element.getText());
    }

    private void checkTextValueArea(WebElement element, String expectedText) {

        Assertions.assertEquals(expectedText, element.getAttribute("value"));
    }

    private void findElemSetValue(By locator, String value) {
        WebElement element = getElementClickable(locator);
        element.clear();
        element.sendKeys(value);

    }
    private void setPrivateDataInfo() {

        findElemSetValue(By.id("id_fname"),UserInfoData.FIRST_NAME.getName() );
        findElemSetValue(By.id("id_lname"), UserInfoData.SECOND_NAME.getName());
        findElemSetValue(By.id("id_fname_latin"),UserInfoData.FIRST_NAME_LATIN.getName());
        findElemSetValue(By.id("id_lname_latin"), UserInfoData.SECOND_NAME_LATIN.getName());
        findElemSetValue(By.id("id_blog_name"),UserInfoData.BLOG_NAME.getName());
        findElemSetValue(By.xpath("//input[@name='date_of_birth']"), UserInfoData.BIRTH_DATE.getName());

    }

    private void setContactInfo() {


        getElementClickable(By.xpath("//input[@name='country']/following-sibling::div")).click();
        setDataInfo(CountryData.Russia.getName());
        getElementClickable(By.xpath("//input[@name='city']/following-sibling::div/span")).click();
        setDataInfo(String.valueOf(ResidenceData.ResidenceOne.getName()));
        getElementClickable(By.xpath("//input[@name='english_level']/following-sibling::div")).click();
        setDataInfo(String.valueOf(LanguageData.INTERMEDIATE.getName()));
        getElementClickable(By.xpath("//span[contains(text(),'Да')]")).click();
        getElementClickable(By.xpath("//span[contains(text(),'Способ связи')]")).click();
        setDataInfo(String.valueOf(ContactsData.VK.getName()));
        findElemSetValue(By.xpath("//input[@id='id_contact-0-value']"), ContactsFieldData.CONTACT1.getName());
        getElementClickable(By.xpath("//button[contains(text(),'Добавить')]")).click();
        findElemSetValue(By.xpath("//input[@id='id_contact-1-value']"), ContactsFieldData.CONTACT2.getName());
        getElementClickable(By.xpath("//span[contains(text(),'Способ связи')]")).click();
        setDataInfo(String.valueOf(ContactsData.TELEGRAM.getName()));
        getElementClickable(By.xpath("//select[@id='id_gender']")).click();
        getElementClickable(By.xpath(String.format("//option[contains(text(),'%s')]",UserInfoData.GENDER.getName()))).click();
        findElemSetValue(By.xpath("//input[@id='id_company']"), UserInfoData.COMPANY.getName());
        findElemSetValue(By.xpath("//input[@id='id_work']"), UserInfoData.WORK.getName());
        driver.findElement(By.cssSelector(".button_md-4:nth-child(1)")).submit();


    }
    public void  clearContact (){
        driver.findElement(By.xpath("//input[@id='id_contact-0-value']")).clear();
        driver.findElement(By.xpath("//input[@id='id_contact-1-value']")).clear();
        driver.findElement(By.cssSelector(".button_md-4:nth-child(1)")).submit();
    }
    private void setDataInfo(String someText){
        String xpathSelector = String.format("//button[contains(text(),'%s')]", someText);
        getElementClickable(By.xpath(xpathSelector)).click();
    }
    public boolean elementIsNotPresent(String xpath){
        return driver.findElements(By.xpath(xpath)).isEmpty();


    }
    private void proverka(){
        setUp();
        driver.get(url);
        loginInOtus();
//        Войти в личный кабинет
        enterLP();
        checkTextValueArea(driver.findElement(By.id("id_fname")), UserInfoData.FIRST_NAME.getName());
        checkTextValueArea(driver.findElement(By.id("id_fname_latin")), UserInfoData.FIRST_NAME_LATIN.getName());
        checkTextValueArea(driver.findElement(By.id("id_lname")), UserInfoData.SECOND_NAME.getName());
        checkTextValueArea(driver.findElement(By.id("id_lname_latin")),UserInfoData.SECOND_NAME_LATIN.getName());
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_blog_name']")), UserInfoData.BLOG_NAME.getName());
        checkTextValueArea(driver.findElement(By.xpath("//input[@name='date_of_birth']")), UserInfoData.BIRTH_DATE.getName());
        checkTextTextArea(CountryData.Russia.getName());
        checkTextTextArea(ResidenceData.ResidenceOne.getName());
        checkTextTextArea(LanguageData.INTERMEDIATE.getName());
        checkTextTextArea(ContactsData.VK.getName());
        checkTextTextArea(ContactsData.TELEGRAM.getName().trim());
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_contact-0-value']")), ContactsFieldData.CONTACT2.getName());
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_contact-1-value']")), ContactsFieldData.CONTACT1.getName());
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_company']")), UserInfoData.COMPANY.getName());
        checkTextValueArea(driver.findElement(By.xpath("//input[@id='id_work']")), UserInfoData.WORK.getName());
        clearContact ();

    }
}

