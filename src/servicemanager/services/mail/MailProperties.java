package servicemanager.services.mail;

import servicemanager.abstracts.AbstractProperties;

public class MailProperties extends AbstractProperties {
	public long getMailPeriod() {
		return getLong("mailperiod", "3600000");
	}

	public String getMailUser() {
		return getString("mailuser", "xeno.player");
	}

	public String getMailPass() {
		return getString("mailpass", "ruri7467");
	}

	public String getPOP3Server() {
		return getString("pop3", "msa.hinet.net");
	}

	public String getFirefoxPath() {
		return getString("firefoxpath",
				"D:\\software\\FirefoxPortable\\FirefoxPortable.exe");
	}

	public boolean isMailRun() {
		return getBoolean("mailrun");
	}

	public void setMail(String pop3, String mailuser, String mailpass,
			String firefox, String t, boolean mailrun) {

		String sKey[] = { "pop3", "mailuser", "mailpass", "firefoxpath",
				"mailperiod" };
		String sValue[] = { pop3, mailuser, mailpass, firefox, t };
		String bKey[] = { "mailrun" };
		boolean bValue[] = { mailrun };

		set(bKey, bValue, sKey, sValue);

	}
}
