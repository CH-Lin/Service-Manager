package servicemanager.services.mysql;

import servicemanager.abstracts.AbstractProperties;

public class MySQLProperties extends AbstractProperties {
	public boolean isMysqlRun() {
		return getBoolean("mysqlRun");
	}

	public boolean isMySQLKeep() {
		return getBoolean("mysqlKeep");
	}

	public String getMysqlPath() {
		return getString("sqlpath", "C:\\MySQL\\bin\\mysqld-nt");
	}

	public String getMysqlIniPath() {
		return getString("inipath", "C:\\MySQL\\my.ini");
	}

	public void setMySQL(String sql, String ini, boolean mysql, boolean Mkeep) {

		String sKey[] = { "sqlpath", "inipath" };
		String sValue[] = { sql, ini };
		String bKey[] = { "mysqlRun", "mysqlKeep" };
		boolean bValue[] = { mysql, Mkeep };

		set(bKey, bValue, sKey, sValue);
		/***********************************************************************
		 * props.setProperty("sqlpath", sql); props.setProperty("inipath", ini);
		 * if (mysql) { props.setProperty("mysqlRun", "1"); } else {
		 * props.setProperty("mysqlRun", "0"); }
		 * 
		 * if (Mkeep) { props.setProperty("mysqlKeep", "1"); } else {
		 * props.setProperty("mysqlKeep", "0"); }
		 **********************************************************************/
	}
}
