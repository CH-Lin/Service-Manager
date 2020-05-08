package servicemanager.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

public class LogTool {
	public static void debug(boolean arg) {

		if (arg) {
			setLog();
		}

	}

	public static void debug(String[] args) {
		if (args.length > 0)
			for (String arg : args) {
				if (!arg.startsWith("debug=") || arg.indexOf("on") == -1)
					setLog();
			}
		else {
			setLog();
		}
	}

	private static void setLog() {
		try {
			File log = new File("logs");
			if (!log.exists()) {
				log.mkdir();
			}
			String date = Calendar.getInstance().getTime().toString().replace(
					':', ' ');
			System.setOut(new PrintStream(new FileOutputStream("logs"
					+ File.separator + "log_" + date + ".txt", false)));
			System.setErr(new PrintStream(new FileOutputStream("logs"
					+ File.separator + "err_" + date + ".txt", false)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
