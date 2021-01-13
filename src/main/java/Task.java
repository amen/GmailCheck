import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Task extends Thread{
    private DesiredCapabilities capabilities;
    private ChromeOptions options;
    private Handler master;
    private WebDriver driver;
    private FileIO fileIO = new FileIO();
    private static final String LOGINURL = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
    public Task(Handler master){
        this.master = master;
    }
    public void stopMe() {
        finished = true;
    }

    public void run(){
        try {
            setupChrome(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver.get(LOGINURL);
        while(true){
            try{
                String alias = master.getAccount();
            } catch(Exception e) {
                stopMe();
            }
            if(alias == null)
                break;
            fillBasicByID("identifierId", alias + Keys.ENTER);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(! driver.getCurrentUrl().contains("deniedsigninrejected")){
                try {
                    fileIO.writeToFile("uncreated.txt", alias);
                    System.out.println("Uncreated oge found: " + alias);
                    fillBasicByID("identifierId",Keys.CONTROL+"a"+Keys.BACK_SPACE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                driver.get(LOGINURL);
            }
        }

    }
    public void fillBasicByID(String fieldID, String contents){
        WebElement field = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id(fieldID)));
        field.sendKeys(contents);
    }
    public void setupChrome(boolean ui) throws FileNotFoundException {
        DriverService.Builder serviceBuilder = new ChromeDriverService.Builder().withSilent(true);
        options = new ChromeOptions();
        ChromeDriverService chromeDriverService = (ChromeDriverService)serviceBuilder.build();
        chromeDriverService.sendOutputTo(new FileOutputStream(System.getProperty("user.dir") + "chromedriver.log"));
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-in-process-stack-traces");
        options.addArguments("--log-level=3");
        options.addArguments("--disable-logging");
        options.addArguments("--disable-gpu");
        options.addArguments("disable-blink-features=AutomationControlled");
        options.addArguments("--incognito");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("disable-infobars");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        if(! ui) {
            options.setHeadless(true);
        }
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, false);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        driver = new ChromeDriver(chromeDriverService,capabilities);
    }
}
