import java.net.*;
import java.io.*;

import javax.net.ssl.*;

public class Client {
	public static void main(String args[]) {

		if (args.length != 2) {
			System.err
					.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int portNumber = Integer.valueOf(args[1]);

		try {
			SSLSocketFactory foo = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			SSLSocket socket = (SSLSocket) foo.createSocket(hostName, portNumber);
			socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println("Input: " + line);
				out.println(line);
			}
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
	}
}