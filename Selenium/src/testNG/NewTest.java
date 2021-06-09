package testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewTest {
  
	private ChromeDriver driver;
	private String baseURL;
	
  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\eclipse-workspace\\Selenium\\chromedriver.exe");
	  driver = new ChromeDriver(); 
	  baseURL = "https://letskodeit.teachable.com/p/practice";
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  driver.get(baseURL);
  }

  @Test
  public void inTest() {
	  WebElement hondaElement = driver.findElement(By.xpath("//option[@value='honda']"));
	  hondaElement.click();
	  
	  Assert.assertEquals(hondaElement.getText(), "Honda");
  }
  
  @AfterClass
  public void afterClass() throws InterruptedException {
	  Thread.sleep(2000);
	  driver.close();
  }

}
