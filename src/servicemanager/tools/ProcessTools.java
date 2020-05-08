package servicemanager.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessTools {
	public static void killProcess(String process) throws InterruptedException,
			IOException {
		Runtime.getRuntime().exec(
				"C:\\WINDOWS\\system32\\taskkill.exe /F /IM " + process)
				.waitFor();
	}

	public static boolean isProcessExist(String process) {
		String cmd = "C:\\WINDOWS\\system32\\tasklist.exe /FO table /NH /FI \"IMAGENAME eq "
				+ process + "\"";
		BufferedReader br;
		String s = "", bf = "";
		try {
			br = new BufferedReader(new InputStreamReader(Runtime.getRuntime()
					.exec(cmd).getInputStream()));
			while ((bf = br.readLine()) != null) {
				s += bf;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (s.indexOf(process.toLowerCase()) != -1)
			return true;
		else
			return false;
	}
}
