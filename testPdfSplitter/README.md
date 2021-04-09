# FCTs-Projectes

## TestPDFSplitter

~~~java
```
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
```
~~~
