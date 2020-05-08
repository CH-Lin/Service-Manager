package servicemanager.services.filechecktools;

import java.awt.Dimension;
import java.awt.Toolkit;
import servicemanager.abstracts.AbstractServices;

public class FileCheckTool extends AbstractServices {

	private FileCheckToolUI fc;

	public FileCheckTool(String name) {
		super(name);// serviceName = "File Check Tools";
	}

	public boolean start() {
		return false;
	}

	public boolean stop() {
		return false;
	}

	public boolean open() {
		if (fc == null)
			fc = new FileCheckToolUI(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = fc.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		fc.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		fc.setModal(true);
		fc.pack();
		fc.toFront();

		if (!fc.isVisible()) {
			fc.setVisible(true);
		}
		return true;
	}
}
