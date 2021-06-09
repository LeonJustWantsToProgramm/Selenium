package hugotest;

import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

	private DriverFactory() {
		// nothing | not allowing to initialize this class from outside
	}
	
	private static DriverFactory instance = new DriverFactory();	

	public static DriverFactory getInstance() {
		return instance;
	}

	ThreadLocal<ChromeDriver> driver = new ThreadLocal<>();
	
	public void setDriver(ChromeDriver driverParam) {
		driver.set(driverParam);
	}
	
	public ChromeDriver getDriver() {
		return driver.get();
	}
	
	public void removeDriver() {	// closes only one Browser, not all at the same time
		driver.get().quit();
		driver.remove();
	}
}
