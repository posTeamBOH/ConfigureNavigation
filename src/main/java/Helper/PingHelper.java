package Helper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * 后台数据检验ping
 */


public class PingHelper {

	private String ip;
	private String port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public  boolean isHostConnectable() {
		//排错
		if (ip.equals("") || !isNumberic(port)) {
			return false;
		}
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(this.ip, Integer.parseInt(port)), 10);
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

	//判断port是否为全数字的
	private boolean isNumberic(String port) {
		if (port.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(port).matches();
	}

	public static void main(String[] args) {
		System.out.println(new PingHelper().isNumberic(""));
	}
}
