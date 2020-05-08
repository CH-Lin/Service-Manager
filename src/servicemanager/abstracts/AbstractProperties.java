package servicemanager.abstracts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JOptionPane;

public abstract class AbstractProperties {
	protected static Properties props;

	static {
		load();
	}

	public AbstractProperties() {
	}

	protected static String getString(String key) {
		return props.getProperty(key);
	}

	protected static String getString(String key, String defaultKey) {
		return props.getProperty(key, defaultKey);
	}

	protected static Long getLong(String key) {
		return getLong(key, "0");
	}

	protected static Long getLong(String key, String defaultKey) {
		long l = 0;
		try {
			l = Long.parseLong(props.getProperty(key, defaultKey));
		} catch (NumberFormatException e) {
		}
		return l;
	}

	protected static boolean getBoolean(String key) {
		if (getString(key, "0").equalsIgnoreCase("1")) {
			return true;
		}
		return false;
	}

	protected static void set(String bKey[], boolean bValue[], String sKey[],
			String sValue[]) {
		if (sValue.length != sKey.length || bKey.length != bValue.length) {
			throw new IllegalArgumentException("key no. not equal to value no.");
		}

		for (int i = 0; i < sValue.length; i++) {
			props.setProperty(sKey[i], sValue[i]);
		}

		for (int i = 0; i < bValue.length; i++) {
			if (bValue[i]) {
				props.setProperty(bKey[i], "1");
			} else {
				props.setProperty(bKey[i], "0");
			}
		}
	}

	public static void write() {
		try {
			props.store(new FileOutputStream("configs" + File.separator
					+ "config.ini"), "Service Manager");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void load() {
		props = new Properties();
		try {
			System.out.printf("%-28tc Load Properties...............%n",
					Calendar.getInstance());
			props.load(new FileInputStream("configs" + File.separator
					+ "config.ini"));
			System.out.printf("%-28tc Load Properties...........[OK]%n",
					Calendar.getInstance());
		} catch (FileNotFoundException e) {
			System.out.printf("%-28tc Properties file not found!!!%n", Calendar
					.getInstance());
			JOptionPane.showMessageDialog(null, "Properties file not found!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
