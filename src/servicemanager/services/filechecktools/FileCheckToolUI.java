package servicemanager.services.filechecktools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JButton;

import servicemanager.tools.FileChecker;

public class FileCheckToolUI extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane = null;

	private JLabel JL_File = null;

	private JLabel content = null;

	private JTextField JT_File = null;

	private JButton Select = null;

	private JButton crc32 = null;

	private JButton md5 = null;

	/**
	 * @param owner
	 */
	public FileCheckToolUI(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setSize(400, 200);
		setMinimumSize(new Dimension(400, 200));
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("File Checker");
		contentPane = (JPanel) getContentPane();
		JT_File = new JTextField();
		JL_File = new JLabel();
		content = new JLabel();
		Select = new JButton();
		crc32 = new JButton();
		md5 = new JButton();
		Select.setOpaque(false);
		crc32.setOpaque(false);
		md5.setOpaque(false);
		Select.addActionListener(this);
		crc32.addActionListener(this);
		md5.addActionListener(this);
		JL_File.setText("File Path");
		Select.setText("...");
		crc32.setText("CRC32");
		md5.setText("MD5");
		JL_File.setBounds(new Rectangle(20, 20, 60, 20));
		JT_File.setBounds(new Rectangle(80, 20, 260, 22));
		Select.setBounds(new Rectangle(345, 20, 20, 20));
		content.setBounds(new Rectangle(20, 100, 345, 20));
		crc32.setBounds(new Rectangle(110, 60, 72, 20));
		md5.setBounds(new Rectangle(20, 60, 72, 20));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);
		contentPane.setSize(new Dimension(379, 101));
		contentPane.add(JL_File, null);
		contentPane.add(JT_File, null);
		contentPane.add(Select, null);
		contentPane.add(content, null);
		contentPane.add(crc32, null);
		contentPane.add(md5, null);
	}

	public void clear() {
		JT_File.setText("");
		content.setText("");
	}

	public void MD5(File f) throws IOException {
		content.setText(FileChecker.MD5(f));
	}

	public void CRC32(File f) throws IOException {
		content.setText(FileChecker.CRC32(f));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "...") {
			JFileChooser ch = new JFileChooser();
			ch.showOpenDialog(this);
			File f = ch.getSelectedFile();
			if (f != null) {
				String file = f.getAbsolutePath();
				if (file != null) {
					JT_File.setText(file);
				}
			}
		} else
			try {
				File f = new File(JT_File.getText());
				if (f.exists() && f.isFile()) {
					if (e.getActionCommand() == "CRC32") {
						CRC32(f);
					} else if (e.getActionCommand() == "MD5") {
						MD5(f);
					}
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	}
}
