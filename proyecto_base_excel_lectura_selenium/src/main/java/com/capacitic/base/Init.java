package com.capacitic.base;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Init {
	public static WebDriver driver = null;
	protected static WebDriverWait wait = null;
	private static String PRINCIPAL_PATH = "";
	
	
	public Init() throws Exception {
		this.PRINCIPAL_PATH = System.getProperty("user.dir").replace("\\", "/").toString();		
		
	}
	public static void main(String[] args) throws Exception {
		Utils leerExcel = new Utils();
		HashMap<String, ArrayList<String>> Data = leerExcel.LeerData();
		leerExcel.generaReporteHTML("Pruebas Robot");
		leerExcel.iniciarTest("Robot Portable");
		System.setProperty("webdriver.chrome.driver",  System.getProperty("user.dir").replace("\\", "/").toString() + "\\chromedriver.exe");
	    driver= new ChromeDriver();
		for (int i = 0; i < Data.get("Probar").size(); i++) {
			if (Data.get("Probar").get(i).toLowerCase().contentEquals("si")) {
				try {
					driver.manage().window().maximize();
					driver.navigate().to("https://www.google.com.mx/");
					driver.findElement(By.name("q")).sendKeys(Data.get("NombComp").get(i));
					driver.findElement(By.name("btnK")).submit();
				
					Thread.sleep(10);
				//	WebElement myDynamicElement = (new WebDriverWait(driver, 500)).until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
//					String actualTitle = driver.getTitle();

					
					 // wait until the google page shows the result
				  //  WebElement myDynamicElement = (new WebDriverWait(driver, 50)).until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));

				    List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

				    // this are all the links you like to visit
				    for (WebElement webElement : findElements)
				    {
				    	//   driver.navigate().to(webElement.getAttribute("href"));
				        System.out.println(webElement.getAttribute("href"));
				    }
				           
				  //  WebElement myDynamicElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
				//	driver.findElement(By.xpath(".//*[@id='rso']/li[1]/div/h3/a")).click();
				//	  String third_link = findElements.get(1).getAttribute("href");
				//	    driver.navigate().to(third_link);
				   // Thread.sleep(20);
				   // driver.navigate().to(webElement.getAttribute("href"));
					driver.findElement(By.xpath("//*[@id=\"rso\"]")).click();
					//Thread.sleep(70);

		         
		            
				
				} catch (Exception e) {
					leerExcel.logFail("Error en la Prueba", driver);
					System.out.println(e);
					continue;
				}
			}
		}
		Thread.sleep(10);
		leerExcel.cerrarTest();
		leerExcel.cerrarReporte();
		driver.quit();
		//driver.close();
		System.exit(1);
	}

	
	
	private static boolean isElementPresent(By by) {
	    try {
	        driver.findElement(by);
	        return true;
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}
}
