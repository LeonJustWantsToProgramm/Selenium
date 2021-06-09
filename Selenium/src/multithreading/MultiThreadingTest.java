package multithreading;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;


class MultiThreadingTest extends ThreadHandler{

  @Test
  @Parameters({ "search" })
  public void multiThreading1(String search) throws InterruptedException {
	  
	  // see if the TestNG.xml file has the right settings for execution
	  try {
		  getDriver().get("https://www.amazon.de/");
		  System.out.println("Test1 thread is " + Thread.currentThread().getId());
		  getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  getDriver().manage().window().maximize();
	
		  getDriver().findElementById("twotabsearchtextbox").sendKeys(search);
		  
		  Thread.sleep(2000);
		  getDriver().findElementById("twotabsearchtextbox").sendKeys(Keys.ENTER);
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
  }
  
//  @Test
//  public void multiThreading2() throws InterruptedException {
//	 
//	  try {
//		  getDriver().get("https://www.amazon.de/");
//		  System.out.println("Test2 thread is " + Thread.currentThread().getId());
//		  getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		  getDriver().manage().window().maximize();
//	
//		  getDriver().findElementById("twotabsearchtextbox").sendKeys("Tastatur");
//		  
//		  Thread.sleep(2000);
//		  getDriver().findElementById("twotabsearchtextbox").sendKeys(Keys.ENTER);
//	  } catch (Exception e) {
//		  e.printStackTrace();
//	  }
//  }
//  
//  @Test
//  public void multiThreading3() throws InterruptedException {
//	  
//	  try {
//		  getDriver().get("https://www.amazon.de/");
//		  System.out.println("Test3 thread is " + Thread.currentThread().getId());
//		  getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		  getDriver().manage().window().maximize();
//	
//		  getDriver().findElementById("twotabsearchtextbox").sendKeys("Monitor");
//		  
//		  Thread.sleep(2000);
//		  getDriver().findElementById("twotabsearchtextbox").sendKeys(Keys.ENTER);
//	  } catch (Exception e) {
//		  e.printStackTrace();
//	  }
//  }
}
