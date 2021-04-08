package testsikulixandselenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class ThreadSikulix implements Runnable {
	WebDriver driver = null;

	public ThreadSikulix(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public void run() {
		// 5. Now displays a window offering the available certificates
		// and use Sikulix for selecting certificate for XIMO DANTE
		// using OCR and click on button to accept the certificate

		// System.out.println("Here we go");
		Point top = driver.manage().window().getPosition();
		Dimension dim = driver.manage().window().getSize();
		try {
			TimeUnit.SECONDS.sleep(3);
			// OCR recognition
			utils.SikulixUtils.click(top.getX(), top.getY(), dim.getWidth(), dim.getHeight(), "FRANCISCO-JAVIER");
			// System.out.println("Found name match");
			Region region = new Region(top.getX(), top.getY(), dim.getWidth(), dim.getHeight());
			// The image of the button to click
			Pattern image = new Pattern("/myImages/btAccept.png");
			Match match = region.find(image);
			// System.out.println("Found image match");
			match.click();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
