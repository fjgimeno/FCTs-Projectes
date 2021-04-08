# FCTs-Projectes

## FCTDiaryAuto

Selenium chromedriver is needed in order for the app to work, you will need to install it in the following directory:

	C:\Program Files\Google\Chrome\Application\chromedriver.exe
	
Alternatively, change line 49 of "FCTDiaryAuto.java" class to the apropiate route. (Do not forget to scape backslashes. Ex. "\\\\").

	
[Download it from here!](https://chromedriver.storage.googleapis.com/90.0.4430.24/chromedriver_win32.zip)
	
Execute the app with two args in order for it to work:

	arg1: username
	
	arg2: password
	
Example of how you should execute the app once the jar has been built:

	java -jar .\FCTDiaryAuto.jar username password
	
An example of a Spreadsheet to use with the programm has also been included("ExampleFCTDiaryAuto Spreadsheet.csv").

Do not edit the layout of the spreadsheet unless needed.

Always groud each week in a group including 5 days, or the amount of days weekly you work.

Example:

	FECHA;Desc. General;Orientaciones;Observaciones;Horas		/*Do not forget to add this line first in the csv*/
	29 de Marzo de 2021;;;;0
	30 de Marzo de 2021;;;;0
	31 de Marzo de 2021;;;;0
	01 de Abril de 2021;;;;0
	02 de Abril de 2021;;;;0
	;;;;														/*Do not forget to add this line too in the csv, as it's used to separate each week and the app won't work without it*/

	