package testNG;

import org.openqa.selenium.JavascriptExecutor;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigateTo {

	private ChromeDriver driver;
	private String baseURL;
	
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\eclipse-workspace\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		baseURL = "https://www.wikipedia.de/";

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		driver.get(baseURL);
	}

	@Test
	public void navigatetoTest() throws InterruptedException {
		String titel1 = driver.getTitle();
		System.out.println("Der Titel der Seite 1 ist: " + titel1);
		String url1 = driver.getCurrentUrl();
		System.out.println("Die URL ist: " + url1 + "\n");

		driver.findElement(By.xpath("//a[@onclick=\"triggerPiwikTrack(this, 'wikimedia.de-logo');\"]")).click();
		// oder driver.navigate().to("https://www.wikimedia.de/");

		String titel2 = driver.getTitle();
		System.out.println("Der Titel der Seite 2 ist: " + titel2);
		String url2 = driver.getCurrentUrl();
		System.out.println("Die URL ist: " + url2 + "\n");

		driver.navigate().back();

		Thread.sleep(1000);

		driver.navigate().forward();
		
		Thread.sleep(1000);
		
		((JavascriptExecutor) driver).executeScript("document.getElementById('search-input').style.display='block';");
				
		driver.findElement(By.xpath("//button[@data-toggle='search-callout-header']")).click();
		
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver).executeScript("document.getElementById('search-input').value='Es funktioniert';");
		
		Thread.sleep(2000);
		
		driver.get("https://de.wikipedia.org/wiki/Heureka");
		
		
		// String PageSource = driver.getPageSource();
		// System.out.println(PageSource);
	}
  
	@AfterMethod
	public void teardown() throws InterruptedException {
		
		Thread.sleep(2000);
		
		driver.quit();
	}

}
