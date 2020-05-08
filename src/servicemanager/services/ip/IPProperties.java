package servicemanager.services.ip;

import servicemanager.abstracts.AbstractProperties;

public class IPProperties extends AbstractProperties {
	public boolean isIPRun() {
		return getBoolean("iprun");
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

		/***********************************************************************
		 * props.setProperty("ftp", ftp); props.setProperty("ftpuser", user);
		 * props.setProperty("ftppass", pass); props.setProperty("ipperiod",
		 * period);
		 * 
		 * if (ip) { props.setProperty("iprun", "1"); } else {
		 * props.setProperty("iprun", "0"); }
		 **********************************************************************/
	}
}
