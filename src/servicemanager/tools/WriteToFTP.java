package servicemanager.tools;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Calendar;

public class WriteToFTP {
	public static void write(String server, String user, String pass,
			String fileName, String text) throws IOException {
		String ips = "";
		try {

			URL url = new URL("ftp://" + user + ":" + pass + "@" + server + "/"
					+ fileName);

			URLConnection urlc = url.openConnection();
			OutputStreamWriter os = new OutputStreamWriter(urlc
					.getOutputStream());

			System.out.printf("%-28tc Write data....................%n",
					Calendar.getInstance());
			os.write(text);
			System.out.printf("%s%n%-28tc Close Connection..............%n",
					ips, Calendar.getInstance());
			os.close();

		} catch (ConnectException e) {
			System.out.printf("%-28tc Connection refused............%n",
					Calendar.getInstance());
			throw e;
		} catch (UnknownHostException e) {
			System.out.printf("%-28tc Unknown Host:%s%n", Calendar
					.getInstance(), server);
			throw e;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		System.gc();

	}
}
