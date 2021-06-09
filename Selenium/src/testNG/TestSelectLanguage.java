package testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestSelectLanguage {

	private ChromeDriver driver;
	private String baseURL;
	
  @BeforeClass
  public void setUp() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\Selenium\\chromedriver.exe");
	  driver = new ChromeDriver();
	  baseURL = "https://www.wikipedia.org/";
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  driver.get(baseURL);
  }
  
  @Test
  public void selectLang() throws InterruptedException {
	  WebElement selectElement = driver.findElement(By.id("searchLanguage"));
	  Select selectLang = new Select(selectElement);
	  
	  selectLang.selectByValue("de");
	  Thread.sleep(2000);
	  
	  //Index 0 ist der Wert der an 1. Stelle steht (wie bei Arrays), nachdem man die Dropdownbox öffnet.
	  selectLang.selectByIndex(0);	
	  Thread.sleep(2000);
	  
	  //Text der zwischen einem offenem und geschlossenem Tag steht: <offenerTag> Text </geschlossenerTag>
	  selectLang.selectByVisibleText("Español");
	  
	  
  }

  @AfterClass
  public void teardown() throws InterruptedException {
	  Thread.sleep(2000);
	  driver.close();
  }
  	
}
