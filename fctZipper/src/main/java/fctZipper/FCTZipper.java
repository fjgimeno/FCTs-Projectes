package fctZipper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * @author FJGimeno
 */
public class FCTZipper {
	final static JFileChooser fc = new JFileChooser();
	private static File file = null;
	private static File directory = null;
	private static boolean exit = false;

	/**
	 * This method uncompresses a given zip file into a target directory
	 *
	 * @param targetDirectory The destination folder of the extracted files
	 * @param file The file to uncompress
	 * @return Integer value, 0 = everything went alright, 1 = something's off.
	 */
	public static int unzip(File file, File targetDirectory) throws IOException, IllegalAccessException {
		InputStream inputStream = new FileInputStream(file);
		try (ZipArchiveInputStream zis = new ZipArchiveInputStream(new BufferedInputStream(inputStream))) {
			ZipArchiveEntry entry = null;
			while ((entry = zis.getNextZipEntry()) != null) {
				File entryDestination = new File(targetDirectory, entry.getName());
				// prevent zipSlip
				if (!entryDestination.getCanonicalPath()
						.startsWith(targetDirectory.getCanonicalPath() + File.separator)) {
					throw new IllegalAccessException("Entry is outside of the target dir: " + entry.getName());
				}
				if (entry.isDirectory()) {
					entryDestination.mkdirs();
				} else {
					entryDestination.getParentFile().mkdirs();
					try (OutputStream out = new FileOutputStream(entryDestination)) {
						IOUtils.copy(zis, out);
					}
				}
			}
			return 0; // 0 = everything was done
		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
			return 1; // 1 = something bad happened / programm failed.
		}
	}

	/**
	 * This Recursive method gives a list of all files inside of a directory, including its
	 * subdirectories.
	 *
	 * @param sourceFolder The source folder of files
	 * @param files        The files list
	 * @return Arraylist of all the found files
	 */
	public static ArrayList<File> getFileList(File sourceFolder, ArrayList<File> fil) {
		ArrayList<File> fileArray = fil;
		File[] files = sourceFolder.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				getFileList(file, fileArray);
			} else {
				fileArray.add(file);
			}
		}
		return fileArray;
	}

	/**
	 * This method zips a given folder content and generates the zip file in the
	 * parent directory.
	 *
	 * @param zipN         The given name for the zip file
	 * @param sourceFolder The source folder of files to be compressed
	 * @param zipName      The constructed address of the zip
	 * @return nothing
	 */
	public static void zip(String zipN, File sourceFolder, boolean keepParentFolder) {
		String zipName = sourceFolder.getParent() + System.getProperty("file.separator") + zipN;
		try {
			/* Create Output Stream that will have final zip files */
			OutputStream zip_output = new FileOutputStream(new File(zipName));
			/*
			 * Create Archive Output Stream that attaches File Output Stream / and specifies
			 * type of compression
			 */
			ArchiveOutputStream logical_zip = new ArchiveStreamFactory()
					.createArchiveOutputStream(ArchiveStreamFactory.ZIP, zip_output);

			ArrayList<File> files = new ArrayList<File>();
			files = getFileList(sourceFolder, files);
			// File[] files = sourceFolder.listFiles();
			for (File fil : files) {
				if (keepParentFolder) {
					// Zips content while keeping file tree (including parent folder)
					logical_zip.putArchiveEntry(new ZipArchiveEntry(fil.getAbsolutePath().replace(
							sourceFolder.getParentFile().getAbsolutePath() + System.getProperty("file.separator"),
							"")));
				} else {
					// Zips content while keeping file tree (not including parent folder)
					logical_zip.putArchiveEntry(new ZipArchiveEntry(fil.getAbsolutePath()
							.replace(sourceFolder.getAbsolutePath() + System.getProperty("file.separator"), "")));
				}

				/* Copy input file */
				System.out.println("Path: " + fil.getAbsolutePath());
				IOUtils.copy(new FileInputStream(new File(fil.getAbsolutePath())), logical_zip);
				logical_zip.closeArchiveEntry();
			}
			/* Close Archieve entry, write trailer information */
			// logical_zip.closeArchiveEntry();
			logical_zip.finish();
			logical_zip.close();
			zip_output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// set to false in case you want to uncompress
		boolean compressOr = true;
		String windowTitle = "Choose destination directory";
		if (args.length == 1) {
			compressOr = false;
			windowTitle = "Choose folder to compress";
		}
		do {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle(windowTitle);
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				directory = fc.getSelectedFile();
				if (!directory.isDirectory()) {
					directory = null;
					throw new Exception("Selected incorrect file type, you must select a directory");
				} else if (!compressOr) {
					do {
						fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						fc.setFileFilter(new FileNameExtensionFilter("Compressed Archive(ZIP)", "zip"));
						fc.setDialogTitle("Choose a zip file");
						int returnVal2 = fc.showOpenDialog(null);

						if (returnVal2 == JFileChooser.APPROVE_OPTION) {
							file = fc.getSelectedFile();
							if (!file.getName().toLowerCase().contains(".zip")) {
								file = null;
								throw new Exception("Selected incorrect file type, you must select a zip file");
							} else {
								int result = unzip(file, directory);
								if (result == 0) {
									System.out.println("\"" + file.getName() + "\" file was extracted successfully!");
								} else {
									System.out.println(
											"\"" + file.getName() + "\" file had an error while extracting it!");
								}
								exit = true;
							}
						} else {
							System.out.println("Programm was canceled by the user.");
							// System.exit(0);
							exit = true;
						}
					} while (file == null && exit == false);
				} else {
					// zip("testZip.zip", new File("C:\\Users\\FJGimeno\\Desktop\\pdfSplit"), true);
					zip("testZip.zip", directory, true);
				}
			} else {
				System.out.println("Programm was canceled by the user.");
				// System.exit(0);
				exit = true;
			}
		} while (directory == null && exit == false);
	}
}
