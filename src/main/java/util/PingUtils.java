package util;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 后台数据检验ping
 */


public class PingUtils {

	private String ip;
	private int port;

	public void setIp(String ip) {
		this.ip = ip;
	}


	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public  boolean isHostConnectable() {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(this.ip, this.port), 10);
		} catch (IOException e) {
			return false;
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
