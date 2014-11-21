import java.util.Date;
import javax.net.ssl.SSLSession;

/**
 * @author rabbitfighter
 *
 */
public class SessionInfo {
	private String peerHost;
	private String cipherSuite;
	private String protocol;
	private String sessionId;
	private long creationTime;
	private long lastAccessTime;

	public SessionInfo(SSLSession session) {
		this.setPeerHost(session.getPeerHost());
		this.setCipherSuite(session.getCipherSuite());
		this.setProtocol(session.getProtocol());
		this.setSessionId(session.getId().toString());
		this.setCreationTime(session.getCreationTime());
		this.setLastAccessTime(session.getLastAccessedTime());
	}

	/***** Getters/Setters *****/

	public String getPeerHost() {
		return peerHost;
	}

	public void setPeerHost(String peerHost) {
		this.peerHost = peerHost;
	}

	public String getCipherSuite() {
		return cipherSuite;
	}

	public void setCipherSuite(String cipherSuite) {
		this.cipherSuite = cipherSuite;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long l) {
		this.creationTime = l;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long l) {
		this.lastAccessTime = l;
	}

	/***** End Getters/Setters *****/

	/**
	 * Prints the SessionInfo
	 */
	public void printSessionInfo() {
		System.out.println("Peer host is: " + this.getPeerHost());
		System.out.println("Cipher suite is: " + this.getCipherSuite());
		System.out.println("Protocol is: " + this.getProtocol());
		System.out.println("Session ID is: " + this.getSessionId());
		System.out.println("The creation time of this session is: "
				+ new Date(this.getCreationTime()));
		System.out.println("Last accessed time of this session is: "
				+ new Date(this.getLastAccessTime()));

	}

}
