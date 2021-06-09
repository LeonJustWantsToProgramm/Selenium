package test;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class Amazon {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\LeonRausch\\Selenium\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.Amazon.de");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Hallo Welt");
		driver.findElement(By.linkText("Prime Video")).click();
		
	}

}
