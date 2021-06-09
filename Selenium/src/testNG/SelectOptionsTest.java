package testNG;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelectOptionsTest {

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
	public void selectOpt() {

		WebElement SelectElement = driver.findElement(By.id("searchLanguage"));

		Select selectLang = new Select(SelectElement);

		List<WebElement> listeLang = selectLang.getOptions();

		for (WebElement weblang : listeLang) {

			System.out.println(weblang.getText());
		}
	}

	@Test
	public void selectLangDA() {

		boolean DAselected = false;

		WebElement SelectElement = driver.findElement(By.id("searchLanguage"));

		Select selectLang = new Select(SelectElement);

		List<WebElement> listeLang = selectLang.getOptions();

		for (WebElement webLang : listeLang) {

			if (webLang.getAttribute("value").equals("da")) {

				webLang.click();
				DAselected = webLang.isSelected();
			}
		}
		Assert.assertTrue(DAselected);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {

		Thread.sleep(2000);
		driver.close();
	}
}
