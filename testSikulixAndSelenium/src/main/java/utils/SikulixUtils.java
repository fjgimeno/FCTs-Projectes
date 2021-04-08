package utils;

import java.io.File;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

public class SikulixUtils {

	/**
	 * Finds text in a region
	 * 
	 * @param leftTop
	 * @param widthHeight
	 * @param text
	 * @throws Exception
	 */
	public static void click(Point leftTop, Dimension widthHeight, String text) throws Exception {
		click(leftTop.getX(), leftTop.getY(), widthHeight.getWidth(), widthHeight.getHeight(), text);
	}

	public static void click(int left, int top, int width, int height, String text) throws FindFailed {
		Region region = new Region(left, top, width, height);
		// Match match=region.findWord("ACCVCA-120");
		// Match match=region.findT(text);
		//Match match = region.findLine(text);
		//region.highlight(1);
		//Region reg = region.findWord(text).highlight(2, "magenta");
		//reg.mouseMove();
		//reg.mouseDown(Button.LEFT);
		//reg.mouseUp();
		Match match = region.findWord(text);
		match.click();
	}

	/**
	 * Clicks on an image given by its path (from resource folder) and a region to
	 * look for the image
	 * 
	 * @param leftTop
	 * @param widthHeight
	 * @param isExecutedFromJar
	 * @param resourceFolder
	 * @param fileName
	 * @throws Exception
	 */
	public static void click(Point leftTop, Dimension widthHeight, boolean isExecutedFromJar, String subResourceFolder, String fileName) throws Exception {
		click(leftTop.getX(), leftTop.getY(), widthHeight.getWidth(), widthHeight.getHeight(), isExecutedFromJar,
				subResourceFolder, fileName);
	}

	public static void click(int left, int top, int width, int height, boolean isExecutedFromJar, String subResourceFolder, String fileName) throws Exception {
		String imgPath = "";
		boolean hasFolder = true;
		if (hasFolder && subResourceFolder != null)
			hasFolder = false;
		if (hasFolder && subResourceFolder.trim().length() == 0)
			hasFolder = false;

		if (!hasFolder)
			imgPath = FileUtils01.getFilePath(isExecutedFromJar, subResourceFolder.trim() + File.separator + fileName);
		else
			imgPath = FileUtils01.getFilePath(isExecutedFromJar, fileName);

		Region region = new Region(left, top, width, height);
		Pattern image = new Pattern(imgPath);

		// Match match=region.findWord("ACCVCA-120");
		Match match = region.find(image);
		match.click();

	}

	public static void clickCertificate() {
		String certificateZkhan = "C:/zkhan.PNG";
		String okButton = "C:/ok.PNG";
		Screen screen = new Screen();
		try {
			screen.click(certificateZkhan);
			screen.click(okButton);
		} catch (FindFailed findFailed) {
			findFailed.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}