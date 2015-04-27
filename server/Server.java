import java.io.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.*;

/**
 * Server is the server side of the client-server program. It creates a new
 * thread for every incoming socket connection that gets accepted. Each thread
 * creates an SSL socket factory, and individual socket for the client and
 * server to be able to talk to each other.
 * 
 * @author Joshua Michael Waggoner
 *
 */
public class Server extends Thread {

	private SSLSocket socket;
	private File currentFile;
	private FileWriter currentFileWriter;;
	private BufferedWriter currentBufferedWriter;
	private final String regex = "^[a-zA-Z0-9]+$";
	Pattern pattern;
	Matcher matcher;

	public Server(SSLSocket socket) {
		this.setSocket(socket);
	}

	public static void main(String args[]) {

		/*
		 * Must take arguments defining port number, else display error
		 */
		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}

		// Get port number
		int portNumber = Integer.valueOf(args[0]);

		try {

			// New server socket factory
			SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			// New SSL server socket
			SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(portNumber);
			System.out.println("Server started at port <" + portNumber + ">");

			while (true) {

				// Create a new sslSocket for incoming connection
				SSLSocket sslSocket = (SSLSocket)(sslServerSocket.accept());
				// Start a new Server thread

				// Start the session
				(new Server(sslSocket)).start();

			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	public void run() {

		try {

			printSessionInfo(this.getSocket().getSession());

			// Socket's input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(
			socket.getInputStream()));

			// Print writer for output stream
			PrintWriter out = new PrintWriter(this.getSocket()
				.getOutputStream(), true);

			String line = null;

			int questionsLeft = 6;

			while ((line = in .readLine()) != null) {

				switch (questionsLeft) {

					case 6:
						System.out.println(line);
						out.println("Please enter your user name (No spaces or special characters): ");
						questionsLeft--;
						break;

					case 5:
						// Validate input with regex
						pattern = Pattern.compile(regex);
						matcher = pattern.matcher(line);
						boolean valid = matcher.find();
						if (valid) {
							System.out.println("New User added: " + "\"" + line + "\"");
							this.setCurrentFile(new File(line + ".txt"));
							this.setCurrentFileWriter(new FileWriter(this.getCurrentFile().getAbsoluteFile()));
							this.setCurrentBufferedWriter(new BufferedWriter(this.getCurrentFileWriter()));
							this.getCurrentBufferedWriter().write(
								"User name: " + line);
							out.println("Please enter your full name: ");
							questionsLeft--;
							break;
						} else {
							System.out.println("Client attempted to create an invalid username");
							out.println("Invalid username. Try again");
							questionsLeft = 5;
							break;
						}

					case 4:
						this.getCurrentBufferedWriter().write(
							"\nFull name: " + line);
						out.println("Please enter your address: ");
						questionsLeft--;
						break;

					case 3:
						this.getCurrentBufferedWriter().write("\nAddress: " + line);
						out.println("Please enter your phone number: ");
						questionsLeft--;
						break;

					case 2:
						this.getCurrentBufferedWriter().write(
							"\nPhone number: " + line);
						out.println("Please enter your email address: ");
						questionsLeft--;
						break;

					case 1:
						this.getCurrentBufferedWriter().write(
							"\nEmail Address: " + line + "\n");
						out.println("Add more users (\"yes\" or any for no): ");
						questionsLeft--;
						break;

					case 0:
						if (line.compareToIgnoreCase("yes") == 0) {
							/*
							 * Repeat steps
							 */
							out.println("Please enter your user name:");
							this.getCurrentBufferedWriter().close();
							this.getCurrentFileWriter().close();
							questionsLeft = 5;
							break;

						} else {
							/*
							 * Close all connections and this thread
							 */
							out.println("SHUTDOWN");
							this.getCurrentBufferedWriter().close();
							this.getCurrentFileWriter().close();
							System.out.println("Connection closed at peer port <" + socket.getPort() + ">");
							this.getSocket().close();
							break;

						}

				} // end switch

			}
		} catch (Exception e) {
			System.err.println(e);
		}
	} // end run()

	/***** Getters/Setters *****/

	public SSLSocket getSocket() {
		return socket;
	}

	public void setSocket(SSLSocket socket) {
		this.socket = socket;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public FileWriter getCurrentFileWriter() {
		return currentFileWriter;
	}

	public void setCurrentFileWriter(FileWriter currentFileWriter) {
		this.currentFileWriter = currentFileWriter;
	}

	public BufferedWriter getCurrentBufferedWriter() {
		return currentBufferedWriter;
	}

	public void setCurrentBufferedWriter(BufferedWriter currentBufferedWriter) {
		this.currentBufferedWriter = currentBufferedWriter;
	}

	/***** End Getters/Setters *****/

	/**
	 * Prints the session info
	 * 
	 * @param sslSession
	 */
	public void printSessionInfo(SSLSession sslSession) {
		if (sslSession.isValid()) {
			System.out.println("\nNew connection established at peer port <" + sslSession.getPeerPort() + ">");
			System.out.println("Peer host is: " + sslSession.getPeerHost());
			System.out.println("Cipher suite is: " + sslSession.getCipherSuite());
			System.out.println("Protocol is: " + sslSession.getProtocol());
			System.out.println("Session ID is: " + sslSession.getId());
			System.out.println("The creation time of this session is: " + new Date(sslSession.getCreationTime()));
			System.out.println("Last accessed time of this session is: " + new Date(sslSession.getLastAccessedTime()));

		} else {
			System.out.println("\nSession is invalid");
		}
	}

} // EOF