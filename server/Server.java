import java.io.*;
import java.util.Date;
import java.util.Scanner;
import javax.net.ssl.*;

/**
 * Client side of the InfoCollection server program
 * 
 * @author Joshua Michael Waggoner
 *
 */
public class Client {

	public static void main(String args[]) {

		/*
		 * Must take arguments defining port number and host name, else display
		 * error
		 */
		if (args.length != 2) {
			System.err
					.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		// Host name and port number
		String hostName = args[0];
		int portNumber = Integer.valueOf(args[1]);

		try {
			// Create new SSL Socket Factory
			SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			// New SSL Socket
			SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(
					hostName, portNumber);
			System.out.println("Client connected at port <" + portNumber
					+ "> on <" + hostName + ">");

			// Print writer for output stream
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			// Buffered reader for input
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			// For receiving input
			Scanner fromServer = new Scanner(socket.getInputStream());

			// For input
			String line = null;
			String serverLine = null;

			// Print to the server
			out.println("Client established connection at port " + portNumber);
			printSessionInfo(socket.getSession());
			/*
			 * While there is input, print the input from server, and then send
			 * the response back
			 */
			while ((serverLine = fromServer.nextLine()) != null) {
				if (serverLine.compareTo("SHUTDOWN") == 0) {
					/*
					 * Shutdown the program and print status
					 */
					System.out.println("Connection going down at <"
							+ socket.getPort() + ">");
					socket.close();
					out.close();
					in.close();
					fromServer.close();
					System.exit(1);

				} else {
					// Print the line from the server
					System.out.println(serverLine);
					// When the user has pressed [ENTER], print out to the
					// server
					if ((line = in.readLine()) != null) {
						out.println(line);
					}
				}
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints the
	 * 
	 * @param sslSession
	 */
	public static void printSessionInfo(SSLSession sslSession) {
		System.out
				.println("\n**********************************************************************");
		System.out.println("New connection established at <"
				+ sslSession.getPeerPort() + ">");
		System.out.println("Peer host is: " + sslSession.getPeerHost());
		System.out.println("Cipher suite is: " + sslSession.getCipherSuite());
		System.out.println("Protocol is: " + sslSession.getProtocol());
		System.out.println("Session ID is: " + sslSession.getId());
		System.out.println("The creation time of this session is: "
				+ new Date(sslSession.getCreationTime()));
		System.out.println("Last accessed time of this session is: "
				+ new Date(sslSession.getLastAccessedTime()));


	}
}
