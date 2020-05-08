package servicemanager.services.tomcat;

import java.util.Calendar;
import servicemanager.abstracts.AbstractServices;
import servicemanager.tools.ProcessTools;

public class Tomcat extends AbstractServices {

	private TomcatProperties xsp = new TomcatProperties();

	public Tomcat(String name) {
		super(name);// serviceName = "Tomcat";
	}

	public boolean start() {
		if (!ProcessTools.isProcessExist(xsp.getTomcatName() + ".exe")) {
			try {
				Runtime.getRuntime().exec("net start " + xsp.getTomcatName())
						.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (ProcessTools.isProcessExist(xsp.getTomcatName() + ".exe")) {
			System.out.printf("%-28tc Tomcat stsrt..............[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean stop() {
		if (ProcessTools.isProcessExist(xsp.getTomcatName() + ".exe")) {
			try {
				ProcessTools.killProcess(xsp.getTomcatName() + "*");
				System.out.printf("%-28tc Tomcat stop...............[OK]%n",
						Calendar.getInstance());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public boolean open() {
		return false;
	}
}
