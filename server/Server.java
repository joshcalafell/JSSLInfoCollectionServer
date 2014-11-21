import java.io.*;
import javax.net.ssl.*;

/**
 * Server is the server side of the client-server program. It creates a new
 * thread for every incoming socket connection that gets accepted. Each thread
 * creates an SSL socket factory, and individual socket for the client and
 * server to be able to talk to each other.
 * 
 * @author rabbitfighter
 *
 */
public class Server extends Thread {

	private SSLSocket socket;

	public Server(SSLSocket socket) {
		this.setSocket(socket);
	}

	public static void main(String args[]) {

		/*
		 * Must take arguments defining port number, else display error
		 */
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}

		// Get port number
		int portNumber = Integer.valueOf(args[0]);

		try {
			// New server socket factory
			SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			// New SSL server socket
			SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory
					.createServerSocket(portNumber);
			// Set the enabled cipher suites
			sslServerSocket.setEnabledCipherSuites(sslServerSocket
					.getSupportedCipherSuites());

			while (true) {
				// Create a new sslSocket for incoming connection
				SSLSocket sslSocket = (SSLSocket) (sslServerSocket.accept());
				// Start a new Server thread
				(new Server(sslSocket)).start();
				// Get the session info
				SessionInfo sessionInfo = new SessionInfo(
						sslSocket.getSession());
				// Print session info
				sessionInfo.printSessionInfo();
				
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	public void run() {

		try {
			// Socket's input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println("From client: " + line);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}// end run()

	/***** Getters/Setters *****/

	public SSLSocket getSocket() {
		return socket;
	}

	public void setSocket(SSLSocket socket) {
		this.socket = socket;
	}


}// EOF