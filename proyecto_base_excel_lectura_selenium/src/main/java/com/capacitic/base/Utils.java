package com.capacitic.base;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class Utils {
	private String archivoXLS = "";
	private String rutaVideo = "";
	private HashMap<String, ArrayList<String>> hash_map = new HashMap();
	private String PRINCIPAL_PATH = "";
	ExtentReports report;
	ExtentTest test;
	XSSFWorkbook book;
	
	public Utils() throws Exception {
		this.PRINCIPAL_PATH = System.getProperty("user.dir").replace("\\", "/");		
		this.book = this.cargaXSL();
	}
	
	
	public XSSFWorkbook cargaXSL() throws IOException {
		FileInputStream file = new FileInputStream(new File(PRINCIPAL_PATH+"\\Escenarios_PruebaQA.xlsx"));
		XSSFWorkbook book = new XSSFWorkbook(file);
		return book;
	}
	
	public HashMap<String, ArrayList<String>> LeerData() throws IOException {
		int numSheets = this.book.getNumberOfSheets();
		
		for(int i = 0; i < numSheets; ++i) {
			XSSFRow varsRow = this.book.getSheetAt(i).getRow(0);
			int cols = varsRow.getPhysicalNumberOfCells();
			int Rows = this.book.getSheetAt(i).getPhysicalNumberOfRows();
			for(int col=0;col<cols;col++) {
				ArrayList Values = new ArrayList();			
				for(int row=1;row<Rows;row++) {
					XSSFRow ValueRow = book.getSheetAt(i).getRow(row);
					if(ValueRow.getCell(col)!=null){
						Values.add(ValueRow.getCell(col).toString());
					}		
					else {
						Values.add("");						
					}
				}
				this.hash_map.put(varsRow.getCell(col).toString(), Values);
			}					
		}
		
		return this.hash_map;
	}	
	
	public void logPass(String mensaje, WebDriver driver) throws IOException {

		this.test.log(LogStatus.PASS, mensaje, this.test.addScreenCapture(this.capture(driver)));
	}
	
	public void logError(String mensaje, WebDriver driver) throws IOException {
		this.test.log(LogStatus.ERROR, mensaje, this.test.addScreenCapture(this.capture(driver)));
	}
	
	public void logFail(String mensaje, WebDriver driver) throws IOException {
		this.test.log(LogStatus.FAIL, mensaje, this.test.addScreenCapture(this.capture(driver)));
	}
	
	public void logWarning(String mensaje, WebDriver driver) throws IOException {
		this.test.log(LogStatus.WARNING, mensaje, this.test.addScreenCapture(this.capture(driver)));
	}
	
	public void logInfo(String mensaje, WebDriver driver) throws IOException {
		this.test.log(LogStatus.INFO, mensaje, this.test.addScreenCapture(this.capture(driver)));
	}
	
	public void logFatal(String mensaje, WebDriver driver) throws IOException {
		this.test.log(LogStatus.FAIL, mensaje, this.test.addScreenCapture(this.capture(driver)));
	}
	
	public void generaReporteHTML(String nombre) throws IOException {
		this.report = new ExtentReports(PRINCIPAL_PATH+"\\Reportes\\"+nombre+"_"+ System.currentTimeMillis() +".html");
		
	}
	
	public void iniciarTest(String nombre) throws IOException {
		this.test = this.report.startTest(nombre);
	}
	
	public String capture(WebDriver driver) throws IOException {
		File scrFile = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File(PRINCIPAL_PATH+"\\Reportes\\Imagenes\\" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getPath();
		Files.copy(scrFile, Dest);
		return errflpath;
	}
	
	public void ExitTest(WebDriver driver) {
		this.report.endTest(test);
		this.report.flush();
		driver.close();
		System.exit(1);
	}
	
	public void cerrarTest() {
		this.report.endTest(test);
	}
	
	public void cerrarReporte() {
		this.report.endTest(test);
		this.report.flush();
	}
	
	public boolean existsElement(WebDriver driver, String strArg) {
		try {
			if (strArg.startsWith("//")) {
				driver.findElement(By.xpath(strArg));
			} else {
				driver.findElement(By.id(strArg));
			}
			
			return true;
		} catch (Exception var4) {
			return false;
		}
	}
	
	public HashMap<String, ArrayList<String>> getHash_map() {
		return this.hash_map;
	}
	
	public void setHash_map(HashMap<String, ArrayList<String>> hash_map) {
		this.hash_map = hash_map;
	}


}	