package servicemanager.services.freespace;

import servicemanager.abstracts.AbstractProperties;

public class FreeSpaceProperties extends AbstractProperties {

	public boolean isFreeRun() {
		return getBoolean("freerun");
	}

	public String getFTPServer() {
		return getString("ftp", "");
	}

	public String getFTPUser() {
		return getString("ftpuser", "");
	}

	public String getFTPPassword() {
		return getString("ftppass", "");
	}

	public long getFTPPeriod() {
		return getLong("ipperiod", "3600000");
	}

	public void setIP(String ftp, String user, String pass, String period,
			boolean ip) {
		String sKey[] = { "ftp", "ftpuser", "ftppass", "ipperiod" };
		String sValue[] = { ftp, user, pass, period };
		String bKey[] = { "iprun" };
		boolean bValue[] = { ip };

		set(bKey, bValue, sKey, sValue);
	}
}
