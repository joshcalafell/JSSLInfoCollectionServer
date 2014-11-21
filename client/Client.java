import java.io.*;
import javax.net.ssl.*;

/**
 * @author rabbitfighter
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
			SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(hostName,
					portNumber);
			socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
			// Print writer for output stream
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			// Buffered reader for input
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			String line = null;
			/*
			 * While there is input, send it to the server
			 */
			while ((line = in.readLine()) != null) {
				System.out.println("Input: " + line);
				out.println(line);
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}