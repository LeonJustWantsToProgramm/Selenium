package webelement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class XpathUndListe {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\Selenium\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://letskodeit.teachable.com/p/practice");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		List<WebElement> RadioButtons = driver.findElements(By.xpath("//legend[contains(text(), 'Radio')]//following-sibling::label//following-sibling::input"));
		
		for (WebElement listeAutos : RadioButtons) {
			System.out.println(listeAutos.getAttribute("value"));
			listeAutos.click();
			Thread.sleep(1000);
		}
		
		driver.close();
		
	}

}
