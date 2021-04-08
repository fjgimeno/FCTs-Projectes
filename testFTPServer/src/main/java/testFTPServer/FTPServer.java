package testFTPServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class FTPServer {
	private static final String folder = "/upload/files/ftpserver";
	private static FtpServer server = null;

	public static void main(String[] args) throws FtpException {
		boolean created = makeDirectory();
		if (created) {
			PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
			UserManager userManager = userManagerFactory.createUserManager();
			BaseUser user = new BaseUser();
			user.setName("username");
			user.setPassword("password");
			user.setHomeDirectory(folder);
			userManager.save(user);
			List<Authority> authorities = new ArrayList<Authority>();
			authorities.add(new WritePermission());
			user.setAuthorities(authorities);
			userManager.save(user);

			ListenerFactory listenerFactory = new ListenerFactory();
			listenerFactory.setPort(2221);

			FtpServerFactory factory = new FtpServerFactory();
			factory.setUserManager(userManager);
			factory.addListener("default", listenerFactory.createListener());

			server = factory.createServer();
			// Comentar la linia siguiente en caso de usar con un boton upload
			server.start();
			System.out.println("Server started, stoped: " + server.isStopped());
		} else {
			System.out.println("Something went bad");
		}
	}

	public static FtpServer getServer() {
		return server;
	}

	private static boolean makeDirectory() {
		try {
			Path path = Paths.get(folder);
			// java.nio.file.Files;
			Files.createDirectories(path);
			System.out.println("Directory is created! " + Paths.get(folder));
			return true;
		} catch (IOException e) {
			System.err.println("Failed to create directory!" + e.getMessage());
			return false;

		}
	}
}
	
	//Code that should be used if implementing an upload button (In the corresponding class where the button is implemented)
	/* 
	//Runs a new thread with the FTP server
	new Thread(new Runnable() {
		public void run() {
			FTPServer ftps;
			try {
				ftps = new FTPServer();
				FtpServer server = ftps.getServer();
				server.start();
			} catch (FtpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}).start();
	
	//Upload button success example with ftp transfer
	upload.addSucceededListener(event -> {
			Component component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
			output.removeAll();
			showOutput(event.getFileName(), component, output);
			System.out.println("File uploaded successfully!" + component.toString());
	
			// Executes FTP client and uploads file.
			FtpClient ftpc = new FtpClient(event.getFileName(), buffer.getInputStream());
	});
	*/
	// END OF CODE
	
	//ALTERNATE FTPSERVER CLASS IF GOING TO USE THIS CLASS ALONGSIDE VAADIN	
	/*
	private static final String folder = "/upload/files/ftpserver";
	private static FtpServer server = null;

	public FTPServer() throws FtpException {
		boolean created = makeDirectory();
		if (created) {
			PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
			UserManager userManager = userManagerFactory.createUserManager();
			BaseUser user = new BaseUser();
			user.setName("username");
			user.setPassword("password");
			user.setHomeDirectory(folder);
			userManager.save(user);
			List<Authority> authorities = new ArrayList<Authority>();
			authorities.add(new WritePermission());
			user.setAuthorities(authorities);
			userManager.save(user);

			ListenerFactory listenerFactory = new ListenerFactory();
			listenerFactory.setPort(2221);

			FtpServerFactory factory = new FtpServerFactory();
			factory.setUserManager(userManager);
			factory.addListener("default", listenerFactory.createListener());

			server = factory.createServer();
			// server.start();
			System.out.println("Server started, stoped: " + server.isStopped());
		} else {
			System.out.println("Something went bad");
		}
	}

	public static FtpServer getServer() {
		return server;
	}

	private static boolean makeDirectory() {
		try {
			Path path = Paths.get(folder);
			// java.nio.file.Files;
			Files.createDirectories(path);
			System.out.println("Directory is created! " + Paths.get(folder));
			return true;
		} catch (IOException e) {
			System.err.println("Failed to create directory!" + e.getMessage());
			return false;

		}
	}
	 */
	

