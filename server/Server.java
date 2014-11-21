
import java.net.*;
import java.io.*;

import javax.net.ssl.*;

public class Server extends Thread{
	
	
	private SSLSocket socket;

	public Server(SSLSocket sock) {
		this.socket = sock;
	}

	public static void main (String args[]) {
		
		 if (args.length != 1) {
	            System.err.println("Usage: java EchoServer <port number>");
	            System.exit(1);
	        }
	        
	        int portNumber = Integer.valueOf(args[0]);

		try {
			SSLServerSocketFactory foo = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			SSLServerSocket socket = (SSLServerSocket)foo.createServerSocket(portNumber);
			//String[] f = socket.getEnabledCipherSuites();
			//for (int i = 0; i < f.length; i++) {
				//System.err.println(f[i]);
			//}
			socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
			while (true) {
				SSLSocket bar = (SSLSocket)(socket.accept());
				(new Server(bar)).start();
				/*
				 * Random shit
				 */
				System.out.println("Peer host: " + bar.getSession().getPeerHost());
				System.out.println("Cipher Suite: " + bar.getSession().getCipherSuite());
				System.out.println("Creattion time: " + bar.getSession().getCreationTime());
				System.out.println("Peer port: " +bar.getSession().getPeerPort());
				System.out.println("Protocol: " + bar.getSession().getProtocol());
	
			}
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
	}

	public void run() {
	
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println("Read: " + line);
				
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}