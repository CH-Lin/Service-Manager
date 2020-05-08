package servicemanager.services.tomcat;

import servicemanager.abstracts.AbstractProperties;

public class TomcatProperties extends AbstractProperties {
	public String getTomcatName() {
		return getString("tomcat", "Tomcat6");
	}

	public boolean isTomcatRun() {
		return getBoolean("tomcatRun");
	}

	public boolean isTomcatKeep() {
		return getBoolean("tomcatKeep");
	}

	public void setTomcat(String Tomcat, boolean tomcat, boolean Tkeep) {

		String sKey[] = { "tomcat" };
		String sValue[] = { Tomcat };
		String bKey[] = { "tomcatRun", "tomcatKeep" };
		boolean bValue[] = { tomcat, Tkeep };

		set(bKey, bValue, sKey, sValue);

		/***********************************************************************
		 * props.setProperty("tomcat", Tomcat); if (tomcat) {
		 * props.setProperty("tomcatRun", "1"); } else {
		 * props.setProperty("tomcatRun", "0"); }
		 * 
		 * if (Tkeep) { props.setProperty("tomcatKeep", "1"); } else {
		 * props.setProperty("tomcatKeep", "0"); }
		 **********************************************************************/
	}
}
