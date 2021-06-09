package hugotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {

	static Logger logger = LogManager.getLogger(Login.class);
	
	public static ChromeDriver driver;
	public static String baseURL;

	static String port = null;
	static String sqlUser = null;
	static String sqlPassword = null;

	static String CountryCode = "";
	static String LicensePlateNumber = "";
	static String EuroCode = "";
	static String Category = "";
	static String AttachedAccount = "";
	static String OBUID = "";
	static String OBUPIN = "";
	static String SettingOfVehicleCategory = "";
	static long PhoneNumberAssignedToVehicle = 0;
	static double WeightInTons = 0;
	static double AxleWeightInTons = 0;
	static double WidthInMeters = 0;
	static double HeightInMeters = 0;
	static double LenghtInMeters = 0;
	static String Model = "";
	static int YearOfManufacture = 0;
	static String VINNumber = "";
	static String VehicleRegistrationCertificatePath = "";
	static Path path = Paths.get("hu-go-vehicleTest.jar");
	static Path directoryPath = path.toAbsolutePath();
	
	static ConfigConnection connection = new ConfigConnection();
	
	public static void declareSQLData() {
		try {
			port = ConfigConnection.getPort();
			sqlUser = ConfigConnection.getSQLUser();
			sqlPassword = ConfigConnection.getSQLPassword();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error("Error reading the the Config.properties file! Errormessage: " + ioe.getMessage());
		}
	}
	
	public static void SQLConnection() throws Exception {
		declareSQLData();
		logger.debug("The vehicle registration path is = " + directoryPath);
		
		try {
		String url = "jdbc:mysql://localhost:" + port + "/seleniumhugotest";
		String username = sqlUser;
		String password = sqlPassword;
		String query = "SELECT * FROM vehicles WHERE ProcessProgress = 'Open'";
				
		Connection con = DriverManager.getConnection(url, username, password);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		rs.next();
		
		CountryCode = rs.getString(1);
		LicensePlateNumber = rs.getString(2);
		EuroCode = rs.getString(3);
		Category = rs.getString(4);
		AttachedAccount = rs.getString(5);
		SettingOfVehicleCategory = rs.getString(8);
		PhoneNumberAssignedToVehicle = rs.getLong(9);
		WeightInTons = rs.getDouble(10);
		AxleWeightInTons = rs.getDouble(11);
		WidthInMeters = rs.getDouble(12);
		HeightInMeters = rs.getDouble(13);
		LenghtInMeters = rs.getDouble(14);
		Model = rs.getString(15);
		YearOfManufacture = rs.getInt(16);
		VINNumber = rs.getString(17);
		VehicleRegistrationCertificatePath = rs.getString(18);

		logger.debug("The Database on port \"" + port + "\" with User \"" + sqlUser + "\" and Password  \"" + sqlPassword + "\" has been "
				+ "accessed successfully and the vehicle information has been written to the variables");
		} catch (Exception e) {
			logger.error("Error! The database couldn't be accessed, maybe there is no ProcessProgress field that is set to \"Open\"." + e.getMessage());
		}
	}
	
	
  @BeforeMethod
  public static void beforeClass() throws Exception {
	  declareSQLData();
	  logger.debug("Application has started");

	  System.setProperty("logFilename", directoryPath.getParent().toString());
      logger.info("The log file can now be found in the directory: \"" + directoryPath.getParent().toString() + "\\logger.log\"");
      
	  SQLConnection();
	  
	  String url = "jdbc:mysql://localhost:" + port + "/seleniumhugotest";
	  String username = sqlUser;
	  String password = sqlPassword;
	  String inProcess = "UPDATE vehicles SET ProcessProgress = 'InProcess' WHERE LicensePlateNumber = '" + LicensePlateNumber + "'";

	  Connection con = DriverManager.getConnection(url, username, password);
	  Statement st = con.createStatement();
	  int setInProcess = st.executeUpdate(inProcess);
	  System.out.println(setInProcess + "row/s effected");
	  
	  logger.info("ChromeDriver with path \"" + (directoryPath.getParent() + "\\chromedriver.exe") + "\" found");
	  
	  System.setProperty("webdriver.chrome.driver", (directoryPath.getParent() + "\\chromedriver.exe"));
	  
	  ChromeOptions options = new ChromeOptions();
	  options.addArguments("--headless", "--window-size=1920,1200", "--ignore-certificate-errors");
	  driver = new ChromeDriver(options);
	  
	  System.out.println("current thread: " + Thread.currentThread().getId());
	  
	  baseURL = "https://www.hu-go.hu/articles/category/news";
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  driver.get(baseURL);
  }
  
  
  @Test
  public static void completeLogin() throws Exception {
	  declareSQLData();
	  
	  try {
		  driver.findElement(By.id("login_username")).sendKeys("1676155243");
		  
		  driver.findElement(By.id("login_password")).sendKeys("Hallo12345!");
		  Thread.sleep(300);
		  
		  driver.findElement(By.id("login_button")).click();
		  Thread.sleep(2500);

		  logger.info("Hugo login successful");
	  } catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Error while logging into the hugo portal" + e.getMessage());
	  }
	  driver.findElement(By.id("buttonwarnnext")).click();
	  
	  
	  //Adds a new Vehicle 
	  
	  driver.findElement(By.id("vehicles_menu")).click();
	  
	  driver.findElement(By.xpath("//a[@href=\"https://www.hu-go.hu/Customers/gepjarmu/1676155243\"]")).click();
	  
	  logger.info("adding new vehicle...");
	  
	  WebElement CountryDropDown = driver.findElement(By.id("vehicleBasicCountry"));
	  Select selectCountry = new Select(CountryDropDown);
	  selectCountry.selectByVisibleText(CountryCode);
	  
	  driver.findElement(By.id("vehicleBasicLicensePlateNumber")).sendKeys(LicensePlateNumber);

	  WebElement EuroCodeDropDown = driver.findElement(By.id("vehicleBasicEuroCode"));
	  Select selectEuroCode = new Select(EuroCodeDropDown);
	  selectEuroCode.selectByValue(EuroCode);
	  
	  WebElement CategoryDropDown = driver.findElement(By.id("vehicleParamsCategory"));
	  Select selectCategory = new Select(CategoryDropDown);
	  selectCategory.selectByValue(Category);

	  WebElement AccountDropDown = driver.findElement(By.id("CustomerAccountId"));
	  Select selectAccount = new Select(AccountDropDown);
	  selectAccount.selectByValue(AttachedAccount);
  
	  WebElement VehicleCategory = driver.findElement(By.id("vehicleParamsCategoryChange"));
	  Select selectVehicleCategory = new Select(VehicleCategory);
	  selectVehicleCategory.selectByVisibleText(SettingOfVehicleCategory);
	  
	  if(PhoneNumberAssignedToVehicle != 0) {
		  driver.findElement(By.id("vehicleBasicPhone")).sendKeys(String.valueOf(PhoneNumberAssignedToVehicle));
	  }
	  
	  if(WeightInTons != 0) {
		  driver.findElement(By.id("vehicleParamsWeight")).sendKeys(String.valueOf(WeightInTons));  
	  }
	  
	  if(AxleWeightInTons != 0) {
		  driver.findElement(By.id("vehicleParamsAxleWeight")).sendKeys(String.valueOf(AxleWeightInTons));  
	  }	
	  
	  if(WidthInMeters != 0) {
		  driver.findElement(By.id("vehicleParamsWidth")).sendKeys(String.valueOf(WidthInMeters));  
	  }
	  
	  if(HeightInMeters != 0) {
		  driver.findElement(By.id("vehicleParamsHeight")).sendKeys(String.valueOf(HeightInMeters));  
	  }
	  
	  if(LenghtInMeters != 0) {
		  driver.findElement(By.id("vehicleParamsLength")).sendKeys(String.valueOf(LenghtInMeters));  
	  }
	  
	  WebElement ModelDropDown = driver.findElement(By.id("vehicleBasicBrandId"));
	  Select selectCarModel = new Select(ModelDropDown);
	  selectCarModel.selectByVisibleText(Model);
	  
	  WebElement YearDropDown = driver.findElement(By.id("vehicleBasicManufactureYear"));
	  Select selectYear = new Select(YearDropDown);
	  selectYear.selectByValue(String.valueOf(YearOfManufacture));
	  
	  driver.findElement(By.id("vehicleBasicChassisNumber")).sendKeys(VINNumber);
	  
	  Path directoryPath = path.toAbsolutePath();
	  
	  logger.warn("Trying to use the path " + VehicleRegistrationCertificatePath);
	  
	  if (VehicleRegistrationCertificatePath.contains("Test.pdf")) {
		  VehicleRegistrationCertificatePath = directoryPath.getParent() + "\\Test.pdf";
	  } else if (VehicleRegistrationCertificatePath.contains("Test 2.pdf")) {
		  VehicleRegistrationCertificatePath = directoryPath.getParent() + "\\Test 2.pdf";
	  } else if (VehicleRegistrationCertificatePath.contains("Test 3.pdf")) {
		  VehicleRegistrationCertificatePath = directoryPath.getParent() + "\\Test 3.pdf";
	  } else {
		  logger.error("No Path for VehicleRegCertificate found");
	  }
	  driver.findElement(By.id("CustomerImageField")).sendKeys(VehicleRegistrationCertificatePath);
	  // Thread.sleep(10000);
	  
	  logger.info("Use of Path " + VehicleRegistrationCertificatePath + " successful");
	  
	  // driver.findElement(By.id("buttonsave")).click();
	  
	  logger.info("The Vehicle with the Licenseplatenumber " + LicensePlateNumber + " was saved");
  }
  
  
  @AfterMethod
  public static void afterClass() throws Exception {
	  declareSQLData();
	  
	  String url = "jdbc:mysql://localhost:" + port + "/seleniumhugotest";
	  String username = sqlUser;
	  String password = sqlPassword;
	  String Completed = "UPDATE vehicles SET ProcessProgress = 'Completed' WHERE LicensePlateNumber = '" + LicensePlateNumber + "'";  //AND ProcessProgress = 'InProcess'
	  
	  Connection con = DriverManager.getConnection(url, username, password);
	  Statement st = con.createStatement();
	  int setCompleted = st.executeUpdate(Completed);
	  System.out.println(setCompleted + "row/s effected");
	  
	  Socket socket = new Socket();
	  socket.connect(new InetSocketAddress("google.com", 80));
	  System.out.println(socket.getLocalAddress());
	  
	  String giveIP = "UPDATE vehicles SET ProcessProgress = '" + socket.getLocalAddress() + "' WHERE LicensePlateNumber = '" + LicensePlateNumber + "'";
	  int setIP = st.executeUpdate(giveIP);
	  System.out.println(setIP + "row/s effected");
	  logger.debug("the Application was completed by the machine with the local IP-Adress " + socket.getLocalAddress());
	 
	  if (setIP > 1) {
		  logger.fatal("Something went wrong, more than one row was updated with the local IP-Adress of this machine!");
	  } else if (setIP < 1) {
		  logger.fatal("Something went wrong, no row was updated with the local IP-Adress of this machine!");
	  }
	  
	  socket.close();
	  driver.quit();
	  
	  logger.info("The Application ended successfully!");	  
  }
}