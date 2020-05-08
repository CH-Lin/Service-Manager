package servicemanager.services.ip;

import java.net.*;
import java.util.Calendar;
import servicemanager.abstracts.AbstractServices;
import servicemanager.tools.WriteToFTP;

public class IP extends AbstractServices implements Runnable {

	private String ips = "";

	private Boolean start;

	private Thread ip;

	private IPProperties xsp;

	public IP(String name) {
		super(name);// serviceName = "IP";
		this.xsp = new IPProperties();

	}

	public void exit() {
		start = false;
	}

	public void run() {
		start = true;
		while (start) {
			try {
				InetAddress[] addr = InetAddress.getAllByName(InetAddress
						.getLocalHost().getHostName());

				for (int i = 0; i < addr.length; i++) {
					if (!addr[i].getHostAddress().startsWith("192")
							&& !addr[i].getHostAddress().equalsIgnoreCase(ips)) {

						ips = addr[i].getHostAddress();
						System.out.printf(
								"%-28tc Write IP Address..............%n",
								Calendar.getInstance());

						WriteToFTP.write(xsp.getFTPServer(), xsp.getFTPUser(),
								xsp.getFTPPassword(), "IP", ips);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(xsp.getFTPPeriod());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.gc();
		}
	}

	public boolean start() {
		if (ip == null) {
			ip = new Thread(this);
			ip.start();
			System.out.printf("%-28tc IP Service Start..........[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean stop() {
		if (ip != null) {
			exit();
			ip = null;
			System.out.printf("%-28tc IP Service Stop...........[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean open() {
		return false;
	}
}
