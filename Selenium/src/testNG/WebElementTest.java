package testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WebElementTest {
  
	private ChromeDriver driver;
	private String baseURL;
  
  
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		baseURL = "https://letskodeit.teachable.com/p/practice";
	  
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseURL);
	}
  
  
	@Test
  	public void testRadioButton() {
	  WebElement element = driver.findElement(By.id("benzradio"));
	  element.click();
	  
	  Assert.assertTrue(element.isSelected());
	}
  

	@AfterClass
	public void teardown() throws InterruptedException {
	  Thread.sleep(2000);
	  driver.close();
	}

}
