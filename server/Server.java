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
	private SessionInfo info;
	private File currentFile;
	private FileWriter currentFileWriter;;
	private BufferedWriter currentBufferedWriter;

	public Server(SSLSocket socket) {
		this.setSocket(socket);
		this.setInfo(new SessionInfo(socket.getSession()));
		this.getInfo().printSessionInfo();
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
			SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			// New SSL server socket
			SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory
					.createServerSocket(portNumber);
			System.out.println("Server started at port <" + portNumber + ">");

			while (true) {

				// Create a new sslSocket for incoming connection
				SSLSocket sslSocket = (SSLSocket) (sslServerSocket.accept());
				// Start a new Server thread

				// Start the session
				(new Server(sslSocket)).start();

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

			// Print writer for output stream
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			String line = null;

			int questionsLeft = 6;

			while ((line = in.readLine()) != null) {

				switch (questionsLeft) {

				case 6:
					//System.out.println(line);
					out.println("Please enter your user name: ");
					questionsLeft--;
					break;
				case 5:
					//System.out.println(line);
					/*
					 * Set a new current file, file writer, and buffered writer
					 */
					this.setCurrentFile(new File(line + ".txt"));
					this.setCurrentFileWriter(new FileWriter(this
							.getCurrentFile().getAbsoluteFile()));
					this.setCurrentBufferedWriter(new BufferedWriter(this
							.getCurrentFileWriter()));
					this.getCurrentBufferedWriter().write("User name: " + line);
					out.println("Please enter your full name: ");
					questionsLeft--;
					break;

				case 4:
					//System.out.println(line);
					this.getCurrentBufferedWriter().write("\nFull name: " + line);
					out.println("Please enter your address: ");
					questionsLeft--;
					break;
					
				case 3:
					//System.out.println(line);
					this.getCurrentBufferedWriter().write("\nAddress: " + line);
					out.println("Please enter your phone number: ");
					questionsLeft--;
					break;

				case 2:
					//System.out.println(line);
					this.getCurrentBufferedWriter().write("\nPhone number: " + line);
					out.println("Please enter your email address: ");
					questionsLeft--;
					break;

				case 1:
					//System.out.println(line);
					this.getCurrentBufferedWriter().write("\nEmail Address: " + line + "\n");
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
						 * Close all connections and exit
						 */
						in.close();
						out.close();
						socket.close();
						this.getCurrentBufferedWriter().close();
						this.getCurrentFileWriter().close();
						System.out.println("Connection closed... Goodbye :)");
						System.exit(1);
					}

					break;

				}// end switch

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

	public SessionInfo getInfo() {
		return info;
	}

	public void setInfo(SessionInfo info) {
		this.info = info;
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

}// EOF