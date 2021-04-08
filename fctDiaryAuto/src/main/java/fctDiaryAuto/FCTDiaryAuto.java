package fctDiaryAuto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JFileChooser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

/**
 * @author FJGimeno *
 */
public class FCTDiaryAuto {
	final static JFileChooser fc = new JFileChooser();
	private static File file = null;

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception("You need to supply exactly two arguments, \"username\" followed by \"password\" !!");
		}
		String nia = args[0];
		String password = args[1];
		fc.setFileFilter(new FileNameExtensionFilter("Spreadsheet(see repo.)", "csv"));
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			if (!file.getName().contains(".csv")) {
				throw new Exception("Selected incorrect file type");
			}

			System.setProperty("file.encoding", "UTF-8");
			// 1. Get WebDriver
			String chromeDriverPath = "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
			options.setCapability("unicodeKeyboard", true);
			options.addArguments("lang=es");
			options.addArguments("--headless", "--disable-gpu", "--window-size=1280,720", "--ignore-certificate-errors",
					"--silent");
			WebDriver driver = new ChromeDriver(options);

			driver.get("https://fct.edu.gva.es/index.php?op=2)");
			driver.findElement(By.xpath("//input[contains (@name, 'usuario')]")).sendKeys(nia);
			driver.findElement(By.xpath("//input[contains (@name, 'password')]")).sendKeys(password);
			driver.findElement(By.xpath("//input[contains (@name,'login')]")).click();
			driver.findElement(By.xpath("//*[contains (@href,'/index.php?op=2)')]")).click(); // "FCTs" tab

			System.out.println("Completant el diari, no tancar el programa fins que este finalitze.");
			modDiario(driver); // Filling "diario"
			driver.close(); // Closes the driver once everything is done
		} else {
			System.out.println("Programm was canceled by the user.");
		}

	}

	public static void modDiario(WebDriver driver) {
		List<String[]> r = null;

		CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(conPuntoYComa).build()) {
			reader.spliterator();
			r = reader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
		int cont = 0, semana = 0;

		for (String[] str : r) {
			Select drpSemana = new Select(driver.findElement(By.id("semanaDiario")));
			drpSemana.selectByIndex(semana);

			if (!str[0].equals("FECHA") && !str[0].equals("")) {
				if (semana == 0 && cont == 4) {
					cont = 0;
					semana++;
					drpSemana.selectByIndex(semana);
				}
				if (cont > 4) {
					cont = 0;
					semana++;
					drpSemana.selectByIndex(semana);
				} else {
					if (cont == 0) {
						System.out.println("Agregando info de la setmana " + (semana + 1));
					}
					System.out.println("Dia: " + (cont + 1) + " Valor: .- - - " + str[1] + " - - -.");
					if (!str[1].equals("")) { // if the value of "descripcion" in the spreadsheet is empty, it skips that day.
						driver.findElement(By.id("modificar" + cont))
								.findElement(By.xpath("./img[contains (@src,'img/modificar.png')]")).click(); // Modifying_"diario2"
						driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
						if (driver.findElement(By.id("descripcion" + cont)).getText().equals("")) {
							// driver.findElement(By.id("descripcion" + cont)).clear();
							driver.findElement(By.id("descripcion" + cont)).sendKeys(str[1]);
							driver.findElement(By.id("orientacion" + cont)).clear();
							driver.findElement(By.id("orientacion" + cont)).sendKeys(str[2]);
							driver.findElement(By.id("observaciones" + cont)).clear();
							driver.findElement(By.id("observaciones" + cont)).sendKeys(str[3]);
							driver.findElement(By.id("tiempo" + cont)).clear();
							driver.findElement(By.id("tiempo" + cont)).sendKeys(str[4]);
							driver.findElement(By.id("aceptar" + cont))
									.findElement(By.xpath("./img[contains (@src,'img/aceptar.png')]")).click(); // Confirm_modification_"diario2"
						} else {
							driver.findElement(By.id("cancelar" + cont))
									.findElement(By.xpath("./img[contains (@src,'img/eliminar.png')]")).click(); // Cancel_modification_"diario2"
						}
					}
					cont++;
				}

			} else {
				cont = 0;
				semana++;
			}
		}

		System.out.println("----------------Finalizado!---------------");
	}

}
