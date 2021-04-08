package testPdfSplitter;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class TestPdfSplitter {
	private static PDDocument doc = null;
	private static File file = null;
	private static int start = 0, end = 0;

	private static void splitPdf(int startIndex, int endIndex) {
		if (doc.getNumberOfPages() > endIndex) {
			System.out.println(doc.getDocumentInformation().getTitle());
			try {
				Splitter splitter = new Splitter();
				splitter.setSplitAtPage(endIndex - startIndex + 1);
				List<PDDocument> splittedList = splitter.split(doc);
				start = 1;
				end = endIndex;
				for (PDDocument doc : splittedList) {
					System.out.println("Start: " + start + " End: " + (start + (doc.getNumberOfPages() - 1)) + " Num pag: " + doc.getNumberOfPages());
					doc.save(file.getParent() + System.getProperty("file.separator")
							+ file.getName().substring(0, file.getName().length() - 4) + "_" + start + "-" + (start + (doc.getNumberOfPages() - 1)) + ".pdf");
					start = end + 1;
					end = end + endIndex;
					doc.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		JFileChooser fc = new JFileChooser();

		fc.setFileFilter(new FileNameExtensionFilter("Documento (PDF)", "pdf"));
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			if (!file.getName().contains(".pdf")) {
				throw new Exception("Selected incorrect file type");
			} else {
				// Loading an existing PDF document
				// File file = new File("C:/pdfBox/splitpdf_IP.pdf");
				doc = Loader.loadPDF(file);
				splitPdf(1, 3);
				
				System.out.println("PDF splitted");
			}
		} else {
			System.out.println("Operation canceled by the user");
		}

		

		// Instantiating Splitter class
		// Splitter splitter = new Splitter();

		// splitting the pages of a PDF document
		// List<PDDocument> Pages = splitter.split(doc);

		// Creating an iterator
		// Iterator<PDDocument> iterator = Pages.listIterator();

		// Saving each page as an individual document
		//int i = 1;

		/*
		 * while (iterator.hasNext()) { PDDocument pd = iterator.next();
		 * pd.save(file.getParent() + System.getProperty("file.separator") +
		 * file.getName().substring(0, file.getName().length() - 4) + "_split_" + i++ +
		 * ".pdf"); }
		 */
		
	}

}
