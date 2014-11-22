import java.io.*;
import java.util.Scanner;

import javax.net.ssl.*;

/**
 * @author rabbitfighter
 *
 */
public class Client {

	@SuppressWarnings("resource")
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

			/*
			 * Initially send this message to start the handshake and get a
			 * response from the server
			 */
			out.println("Client established connection at port " + portNumber);
			/*
			 * While there is input, print the input from server, and then send
			 * the response back
			 */
			while (fromServer.hasNext()) {
				// Print the line from the server
				System.out.println(fromServer.nextLine());
				// When the user has pressed [ENTER], print out to the server
				if ((line = in.readLine()) != null) {
					out.println(line);
				}
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}