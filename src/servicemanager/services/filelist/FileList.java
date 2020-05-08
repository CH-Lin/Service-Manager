package servicemanager.services.filelist;

import java.awt.Dimension;
import java.awt.Toolkit;

import servicemanager.abstracts.AbstractServices;

public class FileList extends AbstractServices {
	private FileListUI ui;

	public FileList(String name) {
		super(name);// serviceName = "File List";
	}

	@Override
	public boolean open() {
		if (ui == null)
			ui = new FileListUI(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = ui.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		ui.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		ui.setModal(true);
		ui.pack();
		ui.toFront();

		if (!ui.isVisible()) {
			ui.setVisible(true);
		}
		return true;
	}

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}

}
