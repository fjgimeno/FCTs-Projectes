package testPdfSplitter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class TestPdfSplitter {
	public static void splitPdf(File file, int nPages) {		
		try {
			PDDocument sourceDoc;
			sourceDoc = Loader.loadPDF(file);
			int start = 0, end = 0;
			if (sourceDoc.getNumberOfPages() > nPages) {
				try {
					Splitter splitter = new Splitter();
					// Indicates how many pages each "cut" from the document will have .
					// (if you have a document of 500 pages and split it in documents of 5 pages each, you will have 250 documents of 2 pages).
					splitter.setSplitAtPage(nPages);
					List<PDDocument> splittedList = splitter.split(sourceDoc);
					start = 1;
					end = nPages;
					for (PDDocument doc : splittedList) {
						doc.save(file.getParent() + System.getProperty("file.separator")
								+ file.getName().substring(0, file.getName().length() - 4) + "_" + start + "-" + (start + (doc.getNumberOfPages() - 1)) + ".pdf");
						// this variables are used to give each generated file a proper name (Ex: document_1-5.pdf, where 1 is "start" and 5 is "end")
						// they will increase their value each time the loop does on cycle.
						start = end + 1;
						end = end + nPages;
						// Closes the document once each pdf is saved
						doc.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		File file = null;
		JFileChooser fc = new JFileChooser();

		fc.setFileFilter(new FileNameExtensionFilter("Documento (PDF)", "pdf"));
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			if (!file.getName().contains(".pdf")) {
				throw new Exception("Selected incorrect file type");
			} else {
				// Calls the split PDF method, sending it the file to split, and the number of pages
				splitPdf(file, 2);
				// Shows a message once everything is done
				System.out.println("PDF splitted");
			}
		} else {
			System.out.println("Operation canceled by the user");
		}		
	}
}
