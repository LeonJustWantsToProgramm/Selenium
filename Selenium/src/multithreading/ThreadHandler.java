package multithreading;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

class ThreadHandler {
	
	protected static ThreadLocal<ChromeDriver> driver = new ThreadLocal<>();
	
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\eclipse-workspace\\Selenium\\chromedriver.exe");
		  
		driver.set(new ChromeDriver());
	}
	
	ChromeDriver getDriver() {
		return driver.get();
	}
	
	@AfterMethod
	void tearDown() { // closes only one Browser, not all at the same time
		driver.get().quit();
		driver.remove();
	}
	
}
