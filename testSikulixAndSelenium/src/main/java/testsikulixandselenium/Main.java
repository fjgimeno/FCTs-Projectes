package testsikulixandselenium;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class Main {

	public static void main(String[] args) {
		System.out.println(ImagePath.getBundlePath()); // print current bundlePath
		ImagePath.setBundlePath("src/images"); // set custom bundlePath
		System.out.println(ImagePath.getBundlePath()); // print new bundlePath

		// 1. URL
		String myUrl = "https://sede-tu.seg-social.gob.es/wps/portal/";

		// 2. Get the driver
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
		WebDriver driver = null;
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS); // Set implicit wait
		driver.get(myUrl);
		final WebDriver driverFinal = driver;

		// 3. Get the browser wait
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);

		// 4. click on the icon for accessing by certificate
		String myXpath = "//a[@title='Acceso como interesado' and @class='btnGo']";
		String myXpath2 = "//a[@title='Acceder con Certificado']";
		// webElementGetterClicker(myXpath, wait, driver);
		// webElementGetterClicker(myXpath2, wait, driver);
		driver.findElement(By.xpath(myXpath)).click();
		System.out.println("Finished clicking");
		new Thread(new Runnable() {
			public void run() {
				// 5. Now displays a window offering the available certificates
				// and use Sikulix for selecting certificate for XIMO DANTE
				// using OCR and click on button to accept the certificate

				// System.out.println("Here we go");
				Point top = driverFinal.manage().window().getPosition();
				Dimension dim = driverFinal.manage().window().getSize();
				try {
					TimeUnit.SECONDS.sleep(1);
					// OCR recognition
					utils.SikulixUtils.click(top.getX(), top.getY(), dim.getWidth(), dim.getHeight(),"FRANCISCO-JAVIER");
					utils.SikulixUtils.click(top.getX(), top.getY(), dim.getWidth(), dim.getHeight(),"Aceptar");
					// System.out.println("Found name match");
					//Region region = new Region(top.getX(), top.getY(), dim.getWidth(), dim.getHeight());
					// The image of the button to click
					//Pattern image = new Pattern("1616752672767.png");
					//Match match = region.find(image);
					// System.out.println("Found image match");
					//match.click();

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		driver.findElement(By.xpath(myXpath2)).click();

		// WebElement webElement = driver.findElement(By.xpath(myXpath2));
		// ((JavascriptExecutor)driver).executeScript("arguments[0].click()",webElement);
		// System.out.println("Finished clicking");

	}

	public static void webElementGetterClicker(String myXpath, Wait<WebDriver> wait, WebDriver driver) {
		WebElement webElement = null;
		/*
		 * try { webElement = wait.until(new Function<WebDriver, WebElement>() { public
		 * WebElement apply(WebDriver browser) { WebElement wE = null; for (WebElement
		 * webElem : browser.findElements(By.xpath(myXpath))) {
		 * System.out.println("WebElem=" + webElem.toString()); wE = webElem; if
		 * ((wE.isDisplayed() && wE.isEnabled())) break; } return wE; } }); webElement =
		 * driver.findElement(By.xpath(myXpath)); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		/* webElement = */driver.findElement(By.xpath(myXpath)).click();
		// Click the element
		// System.out.println("Proceding with click");
		// webElement.click();
		System.out.println("Finished clicking");
	}
}
