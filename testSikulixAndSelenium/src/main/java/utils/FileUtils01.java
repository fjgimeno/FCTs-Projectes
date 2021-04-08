package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URLDecoder;
import java.security.CodeSource;

public class FileUtils01 {

	/**
	 * Folder that contains jar file or folder that contains project folder
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getJarContainingFolder() throws Exception {
		Class<?> aClass = MethodHandles.lookup().lookupClass();
		CodeSource codeSource = aClass.getProtectionDomain().getCodeSource();

		File jarFile;

		if (codeSource.getLocation() != null) {
			jarFile = new File(codeSource.getLocation().toURI());
		} else { // It is not a jar file
			String path = aClass.getResource(aClass.getSimpleName() + ".class").getPath();
			String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
			jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
			jarFile = new File(jarFilePath);
		}
		String s = jarFile.getParentFile().getAbsolutePath();
		System.out.println("S------>:" + s);
		if (s.endsWith(File.separator + "target")) { // Maven target directory for compiled classes
			s = s.substring(0, s.lastIndexOf(File.separator));
			s = s.substring(0, s.lastIndexOf(File.separator));
		}
		return s;
	}

	public static byte[] readFile(String fileName) throws IOException {
		File file = new File(fileName);// filename should be with complete path
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		fis.read(b);
		fis.close();
		;
		return b;
	}

	public static void writeToFile(String fileName, String myString) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write(myString);
		// do stuff
		writer.close();
	}

	public static String getFilePath(boolean isExecutedFromJAR, String relativeFilePath) throws Exception {
		if (isExecutedFromJAR)
			return FileUtils01.getJarContainingFolder() + File.separator + relativeFilePath;
		else {
			return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
					+ "resources" + File.separator + relativeFilePath;
		}
	};

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * 
	 *                   Example
	 *                   ExcelFile=getRelativeFromJarFile("excel\MyExcel.xlsx")
	 */
	public static String getRelativeFromJarFile(String filePath) throws Exception {
		return FileUtils01.getJarContainingFolder() + File.separator + filePath;
	}

	/**
	 * test in main class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getJarContainingFolder());
			System.out.println(System.getProperty("java.class.path"));
			System.out.println(System.getProperty("user.dir"));
			byte[] b = readFile("/home/eduard/Audit.0.log");
			String sb = new String(b);
			System.out.println(sb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}