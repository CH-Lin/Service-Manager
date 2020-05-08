package servicemanager.services.freespace;

import java.io.File;
import java.io.IOException;
import java.util.*;
import servicemanager.abstracts.AbstractServices;
import servicemanager.tools.FreeSpaceTools;
import servicemanager.tools.WriteToFTP;

public class FreeSpace extends AbstractServices implements Runnable {

	private Thread free;

	private FreeSpaceProperties xsp;

	public FreeSpace(String name) {
		super(name);// serviceName = "Free Space";
		this.xsp = new FreeSpaceProperties();

	}

	private Boolean start;

	public void exit() {
		start = false;
	}

	public void run() {
		start = true;
		while (start) {
			sendFreeSpace();
			try {
				Thread.sleep(xsp.getFTPPeriod());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.gc();
		}
	}

	public boolean start() {
		if (free == null) {
			free = new Thread(this);
			free.start();
			System.out.printf("%-28tc Free Space Service Start..[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean stop() {
		if (free != null) {
			exit();
			free = null;
			System.out.printf("%-28tc Free Space Service Stop...[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean open() {
		return false;
	}

	private void sendFreeSpace() {
		System.out.printf("%-28tc Sending Free Space Info.......%n", Calendar
				.getInstance());
		LinkedList<File> list = FreeSpaceTools.getFreeSpaceInfo();
		StringBuilder info = new StringBuilder();
		for (File f : list) {
			long free = f.getFreeSpace();
			info.append("" + f.getAbsolutePath() + " " + free + "Bytes  "
					+ (free / 1024 / 1024) + "MG  "
					+ (free / 1024 / 1024 / 1024) + "GB\n");
		}

		try {
			WriteToFTP.write(xsp.getFTPServer(), xsp.getFTPUser(), xsp
					.getFTPPassword(), "FREESAPCE", info.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
