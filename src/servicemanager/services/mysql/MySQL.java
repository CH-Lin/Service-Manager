package servicemanager.services.mysql;

import java.util.Calendar;
import servicemanager.abstracts.AbstractServices;
import servicemanager.tools.ProcessTools;

public class MySQL extends AbstractServices {

	private MySQLProperties xsp;

	public MySQL(String name) {
		super(name);// serviceName = "MySQL";
		this.xsp = new MySQLProperties();
	}

	public boolean start() {
		String path = xsp.getMysqlPath();
		path = path.substring(path.lastIndexOf("\\") + 1);
		if (!ProcessTools.isProcessExist(path)) {
			try {
				Runtime.getRuntime().exec(
						"\"" + xsp.getMysqlPath() + "\" --defaults-file=\""
								+ xsp.getMysqlIniPath() + "\"");
			} catch (Exception ex) {
				System.out.printf("%-28tc Cannot run program............ %s%n",
						Calendar.getInstance(), xsp.getMysqlPath());
			}
		}
		if (ProcessTools.isProcessExist(path)) {
			System.out.printf("%-28tc MySQL start...............[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean stop() {
		String path = xsp.getMysqlPath();
		path = path.substring(path.lastIndexOf("\\") + 1);
		if (ProcessTools.isProcessExist(path)) {
			try {
				ProcessTools.killProcess(path);
				System.out.printf("%-28tc MySQL stop................[OK]%n",
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
