package testFTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
 
/**
 * A program that demonstrates how to upload files from local computer
 * to a remote FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FtpClient {
 
    public static void main(String[] args) {
        String server = "localhost";
        int port = 2221;
        String user = "username";
        String pass = "password";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("C:\\Users\\FJGimeno\\Desktop\\file1.txt");
 
            String firstRemoteFile = "file1.txt";
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            ftpClient.doCommand("SYST", "");
            System.out.println("Server says: " + ftpClient.getReplyCode() + " - " + ftpClient.getReplyString());
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                	System.out.println("Disconecting");
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /* 
     public FtpClient(String firstRemoteFile, InputStream inputStream) {
		String server = "localhost";
		int port = 2221;
		String user = "username";
		String pass = "password";

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// APPROACH #1: uploads first file using an InputStream
			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			System.out.println("Server says: " + ftpClient.getReplyCode() + " - " + ftpClient.getReplyString());
			ftpClient.doCommand("SYST", "");
			System.out.println("Server says: " + ftpClient.getReplyCode() + " - " + ftpClient.getReplyString());
			inputStream.close();
			if (done) {
				System.out.println("The first file is uploaded successfully.");
			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					System.out.println("Disconecting");
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
    */ 
 
}