package servicemanager.services.filelist;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.io.*;
import java.util.*;

public class FileListUI extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField Path = null;

	private JButton Start = null;

	private PrintStream out = null, err = null;

	private JPanel SelPanel = null;

	private JButton JB_sel = null;

	/**
	 * This is the default constructor
	 */
	public FileListUI(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setMinimumSize(new Dimension(300, 200));
		setTitle("File List");
		this.setLayout(new BorderLayout());
		this.add(getStart(), BorderLayout.CENTER);
		this.setVisible(true);
		this.add(getSelPanel(), BorderLayout.NORTH);
	}

	/**
	 * This method initializes Path
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getPath() {
		if (Path == null) {
			Path = new JTextField();
			Path.setEditable(false);
		}
		return Path;
	}

	/**
	 * This method initializes Start
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getStart() {
		if (Start == null) {
			Start = new JButton("Start");
			Start.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOutput(Path.getText());
					new Directory(Path.getText(), 0);
					resetoutput();
				}
			});
		}
		return Start;
	}

	/**
	 * This method initializes Sel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSelPanel() {
		if (SelPanel == null) {
			SelPanel = new JPanel();
			SelPanel.setLayout(new BorderLayout());
			SelPanel.add(getPath(), BorderLayout.CENTER);
			SelPanel.add(getJB_sel(), BorderLayout.EAST);
		}
		return SelPanel;
	}

	/**
	 * This method initializes JB_sel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_sel() {
		if (JB_sel == null) {
			JB_sel = new JButton("...");
			JB_sel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser(System
							.getProperty("user.dir"));
					fileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fileChooser.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						Path.setText(fileChooser.getSelectedFile()
								.getAbsolutePath());
					}
				}
			});
		}
		return JB_sel;

	}

	private void setOutput(String start) {
		out = System.out;
		err = System.err;

		FileSystemView fv = FileSystemView.getFileSystemView();
		File f = new File(start);
		String DisplayName;

		DisplayName = fv.getSystemDisplayName(f);

		try {
			File log = null;
			try {

				log = new File("logs" + File.separator
						+ DisplayName.replace(":", ""));
			} catch (StringIndexOutOfBoundsException e) {
				log = new File("logs");
			}
			if (!log.exists()) {
				log.mkdirs();
			}
			String date = Calendar.getInstance().getTime().toString().replace(
					':', ' ');
			System.setOut(new PrintStream(new FileOutputStream(log
					.getAbsolutePath()
					+ File.separator + date + "_Lists.txt"), false, "UTF-8"));
			System.setErr(new PrintStream(new FileOutputStream(log
					.getAbsolutePath()
					+ File.separator + date + "_err.txt"), false, "UTF-8"));

			if (fv.isRoot(f))
				System.out.println("Start from " + DisplayName);
			else {
				System.out.println("Start from " + f.getAbsolutePath());
			}
			System.out.println();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void resetoutput() {
		System.err.close();
		System.out.close();
		System.setErr(err);
		System.setOut(out);
	}

	class Directory {

		// LinkedList<?> ll = new LinkedList();

		public Directory(String path, int layer) {
			File f = new File(path);
			if (f.isDirectory()) {
				for (int i = 0; i < layer - 1; i++) {
					System.out.print("\t");
				}
				System.out.println(f.getAbsolutePath());
				String files[] = f.list();
				try {
					for (String file : files) {

						File f2 = new File(f.getAbsolutePath() + File.separator
								+ file);
						if (f2.isDirectory()) {
							new Directory(f.getAbsolutePath() + File.separator
									+ file, layer + 1);

						} else {
							for (int i = 0; i < layer; i++) {
								System.out.print("\t");
							}
							System.out.println(f.getAbsolutePath()
									+ System.getProperty("file.separator")
									+ file);
						}
					}
					System.out.println();
				} catch (NullPointerException e) {

				}
			}
		}
	}
}
