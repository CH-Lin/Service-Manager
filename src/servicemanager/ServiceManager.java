package servicemanager;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import servicemanager.abstracts.*;
import servicemanager.services.freespace.FreeSpaceProperties;
import servicemanager.services.ip.IPProperties;
import servicemanager.services.mail.MailProperties;
import servicemanager.services.mysql.MySQLProperties;
import servicemanager.services.tomcat.TomcatProperties;
import servicemanager.tools.LogTool;
import snoozesoft.systray4j.*;

public class ServiceManager implements SysTrayMenuListener {

	private static SysTrayMenuIcon icons;

	private static SysTrayMenu menu;

	private ServiceManagerPreferences dlg;

	private HashMap<String, AbstractServices> servicesMap;

	private HashMap<String, AbstractUI> uiMap;

	private String prefix = "servicemanager.services.";

	public ServiceManager() {
		icons = new SysTrayMenuIcon("configs" + File.separator + "logo.ico");
		icons.addSysTrayMenuListener(this);
		servicesMap = new HashMap<String, AbstractServices>();
		uiMap = new HashMap<String, AbstractUI>();

		loadTrayMenu();

		dlg = new ServiceManagerPreferences();
		System.out.printf("%-28tc Service Manager start%n", Calendar
				.getInstance());
		autoRun();
	}

	private void loadTrayMenu() {

		SysTrayMenuItem itemExit = new SysTrayMenuItem("Exit", "exit");
		itemExit.addSysTrayMenuListener(this);
		menu = new SysTrayMenu(icons, "Service Manager");
		menu.addItem(itemExit);
		menu.addSeparator();

		Vector<SysTrayMenuItem> items = null;
		AbstractServices services = null;
		AbstractUI ui = null;

		try {
			System.out.printf("%-28tc Start to initial service%n", Calendar
					.getInstance());

			SAXReader saxReader = new SAXReader();
			Document d = saxReader.read(new File("configs" + File.separator
					+ "services.xml"));
			Element root = d.getRootElement();
			prefix = root.attributeValue("prefix");
			List<?> list = root.elements("Service");
			for (Object o : list.toArray()) {
				try {
					Element e = (Element) o;
					items = new Vector<SysTrayMenuItem>();

					String name = e.attributeValue("name");
					if (name == null) {
						continue;
					}

					String servicespath = e.attributeValue("servicespath");
					if (servicespath != null) {
						Class<?> c = Class.forName(prefix + servicespath);
						services = (AbstractServices) c.getConstructor(
								String.class).newInstance(name);
						System.out.printf("%-28tc Initial Service %s%n",
								Calendar.getInstance(), services.getName());
					}

					String servicesui = e.attributeValue("servicesui");
					if (servicesui != null) {
						Class<?> c = Class.forName(prefix + servicesui);
						ui = (AbstractUI) c.getConstructor().newInstance();
						String n = prefix + servicesui + "Setting";
						SysTrayMenuItem open = new SysTrayMenuItem("Setting", n);
						open.addSysTrayMenuListener(this);
						uiMap.put(n, ui);
						items.add(open);
					}
					String servicesopen = e.attributeValue("servicesopen");
					if (servicesopen != null) {
						Class<?> c = Class.forName(prefix + servicesopen);
						ui = (AbstractUI) c.getConstructor().newInstance();
						String n = prefix + servicesopen + "Open";
						SysTrayMenuItem open = new SysTrayMenuItem("Open", n);
						open.addSysTrayMenuListener(this);
						uiMap.put(n, ui);
						items.add(open);
					}
					String service = e.getText().toLowerCase();
					if (service.indexOf("stop") != -1) {
						String n = services.getName() + " Stop";
						SysTrayMenuItem stop = new SysTrayMenuItem("Stop", n);
						stop.addSysTrayMenuListener(this);
						stop.setEnabled(false);
						servicesMap.put(n, services);
						items.add(stop);
					}
					if (service.indexOf("start") != -1) {
						String n = services.getName() + " Start";
						SysTrayMenuItem start = new SysTrayMenuItem("Start", n);
						start.addSysTrayMenuListener(this);
						servicesMap.put(n, services);
						items.add(start);
					}
					if (service.indexOf("open") != -1) {
						String n = services.getName() + " Open";
						SysTrayMenuItem open = new SysTrayMenuItem("Open", n);
						open.addSysTrayMenuListener(this);
						servicesMap.put(n, services);
						items.add(open);
					}
				} catch (Exception e) {
					services = null;
					e.printStackTrace();
				}
				if (services != null) {
					SubMenu subMenu = new SubMenu(services.getName(), items);
					menu.addItem(subMenu);
					System.out.printf("%-28tc Initial Service %s [ok]%n",
							Calendar.getInstance(), services.getName());
				}
			}

			menu.showIcon();

			System.out.printf("%-28tc System Tray...............[OK]%n",
					Calendar.getInstance());
		} catch (DocumentException e1) {
			System.out.printf("%-28tc \\configs\\services.xml not found%n",
					Calendar.getInstance());
			JOptionPane.showMessageDialog(null,
					"\\configs\\services.xml not found");
		}
	}

	private void autoRun() {
		// Auto run
		if (new IPProperties().isIPRun()) {
			AbstractServices as = servicesMap.get("IP Start");
			if (as != null) {
				if (as.start()) {
					setStart(as.getName());
				}
			}
		}
		if (new MailProperties().isMailRun()) {
			AbstractServices as = servicesMap.get("Mail Start");
			if (as != null) {
				if (as.start()) {
					setStart(as.getName());
				}
			}
		}
		if (new MySQLProperties().isMysqlRun()) {
			AbstractServices as = servicesMap.get("MySQL Start");
			if (as != null) {
				if (as.start()) {
					setStart(as.getName());
				}
			}
		}
		if (new TomcatProperties().isTomcatRun()) {
			AbstractServices as = servicesMap.get("Tomcat Start");
			if (as != null) {
				if (as.start()) {
					setStart(as.getName());
				}
			}
		}
		if (new FreeSpaceProperties().isFreeRun()) {
			AbstractServices as = servicesMap.get("Free Space Start");
			if (as != null) {
				if (as.start()) {
					setStart(as.getName());
				}
			}
		}
	}

	public void menuItemSelected(SysTrayMenuEvent e) {
		if (e.getActionCommand().equals("exit")) {
			if (!new MySQLProperties().isMySQLKeep()) {
				AbstractServices as = servicesMap.get("MySQL Stop");
				if (as != null) {
					if (as.stop()) {
						setStop(as.getName());
					}
				}
			}
			if (!new TomcatProperties().isTomcatKeep()) {
				AbstractServices as = servicesMap.get("Tomcat Stop");
				if (as != null) {
					if (as.stop()) {
						setStop(as.getName());
					}
				}
			}

			System.out.printf("%-28tc exit%n", Calendar.getInstance());
			System.exit(0);
		} else {
			String cmd = e.getActionCommand();
			AbstractServices as = servicesMap.get(cmd);
			if (as != null) {
				if (cmd.indexOf("Start") != -1) {
					if (as.start()) {
						setStart(as.getName());
					}
				} else if (cmd.indexOf("Stop") != -1) {
					if (as.stop()) {
						setStop(as.getName());
					}
				} else if (cmd.indexOf("Open") != -1) {
					as.open();
				}
			}
			AbstractUI au = uiMap.get(cmd);
			if (au != null) {
				if (cmd.indexOf("Setting") != -1 || cmd.indexOf("Open") != -1) {
					dlg.Open(au);
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();
					Dimension frameSize = dlg.getSize();
					if (frameSize.height > screenSize.height) {
						frameSize.height = screenSize.height;
					}
					if (frameSize.width > screenSize.width) {
						frameSize.width = screenSize.width;
					}
					dlg.setLocation((screenSize.width - frameSize.width) / 2,
							(screenSize.height - frameSize.height) / 2);
					dlg.setModal(true);
					dlg.pack();
					if (!dlg.isVisible()) {
						dlg.setVisible(true);
					}
				}
			}
		}
	}

	private void setStart(String label) {
		SubMenu sub = (SubMenu) menu.getItem(label);
		SysTrayMenuItem s = sub.getItem("Start");
		s.setEnabled(false);
		SysTrayMenuItem s2 = sub.getItem("Stop");
		s2.setEnabled(true);
	}

	private void setStop(String label) {
		SubMenu sub = (SubMenu) menu.getItem(label);
		SysTrayMenuItem s = sub.getItem("Start");
		s.setEnabled(true);
		SysTrayMenuItem s2 = sub.getItem("Stop");
		s2.setEnabled(false);
	}

	public void iconLeftClicked(SysTrayMenuEvent e) {
	}

	public void iconLeftDoubleClicked(SysTrayMenuEvent e) {
	}

	public static void main(String[] args) {
		LogTool.debug(args);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception exception) {
					exception.printStackTrace();
				}

				new ServiceManager();
			}
		});
	}
}