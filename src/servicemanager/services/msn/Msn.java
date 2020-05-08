package servicemanager.services.msn;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.dom4j.DocumentException;
import servicemanager.abstracts.AbstractServices;

public class Msn extends AbstractServices {

	private final String sp = File.separator;

	public Msn(String name) {
		super(name);// serviceName = "MSN";
	}

	public boolean open() {
		MsnUI ui = new MsnUI(null);
		ui.setVisible(true);
		return true;
	}

	public boolean start(String paths[], String output, boolean split) {

		Locale.setDefault(new Locale("en", "US"));

		HashSet<String> files = new HashSet<String>();

		// list all files in directory paths[i] without duplicate
		for (int i = 0; i < paths.length; i++) {
			File f = new File(paths[i]);
			if (f.isDirectory()) {
				String dirs[] = f.list();
				for (String s : dirs) {
					if (s.endsWith("xml"))
						files.add(s);
				}
			}
		}

		MsnParser p = new MsnParser();

		Iterator<String> iterator = files.iterator();
		while (iterator.hasNext()) {
			String fils = iterator.next();
			System.out.println(fils);
			for (int i = 0; i < paths.length; i++) {

				File tar = new File(paths[i] + sp + fils);

				if (tar.exists())
					try {
						System.out.println(tar.getAbsolutePath());
						p.parse(paths[i] + sp + fils);
					} catch (DocumentException e) {
						e.printStackTrace();
					}
			}
			try {
				File f = new File(output);
				if (!f.exists())
					f.mkdirs();
				if (split)
					p.output(output, fils);
				else
					p.output(output + sp + fils);
				p.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finish");
		JOptionPane.showMessageDialog(null, "Finish");
		return false;
	}

	public boolean stop() {
		return false;
	}

	public boolean start() {
		return false;
	}

}
