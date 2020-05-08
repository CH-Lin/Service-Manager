package servicemanager.services.mail;

import javax.swing.*;
import servicemanager.abstracts.AbstractUI;

import java.awt.event.*;
import java.io.File;

public class MailUI extends AbstractUI {

	private static final long serialVersionUID = 1L;

	private JLabel JL_MailServer = new JLabel();

	private JLabel JL_MailUser = new JLabel();

	private JLabel JL_MailPass = new JLabel();

	private JLabel JL_MailTime = new JLabel();

	private JLabel JL_FirefoxPath = new JLabel();

	private JTextField Text_MailServer = new JTextField();

	private JTextField Text_MailUser = new JTextField();

	private JTextField Text_Firefox = new JTextField();

	private JTextField Text_T = new JTextField();

	private JPasswordField Password = new JPasswordField();

	private JCheckBox auto = new JCheckBox();

	private JButton JB_CURL = new JButton();

	/**
	 * This is the default constructor
	 */
	public MailUI() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		super.initialize("Mail");
		setSize(335, 180);
		JL_MailTime.setText("Time period:");
		JL_MailServer.setText("Mail Server");
		JL_MailUser.setText("User:");
		JL_MailPass.setText("Password:");
		JL_FirefoxPath.setText("Firefox:");
		Password.setEchoChar('*');
		auto.setOpaque(false);
		auto.setText("Auto Run");
		JB_CURL.setOpaque(false);
		JB_CURL.setText("...");
		JB_CURL.addActionListener(this);
		JL_MailServer.setBounds(10, 20, 70, 21);
		JL_MailUser.setBounds(10, 45, 50, 21);
		JL_MailPass.setBounds(10, 70, 50, 21);
		JL_MailTime.setBounds(10, 95, 70, 21);
		JL_FirefoxPath.setBounds(10, 120, 50, 21);
		Text_MailUser.setBounds(75, 45, 240, 21);
		Text_MailServer.setBounds(75, 20, 240, 21);
		Text_T.setBounds(75, 95, 240, 21);
		Text_Firefox.setBounds(75, 120, 190, 21);
		Password.setBounds(75, 70, 240, 20);
		Text_MailUser.setOpaque(false);
		Text_MailServer.setOpaque(false);
		Text_T.setOpaque(false);
		Text_Firefox.setOpaque(false);
		Password.setOpaque(false);
		JB_CURL.setBounds(275, 120, 20, 20);
		auto.setBounds(10, 145, 80, 21);
		add(JL_MailServer);
		add(JL_MailUser);
		add(JL_MailPass);
		add(JL_MailTime);
		add(Text_MailUser);
		add(Text_MailServer);
		add(Text_T);
		add(Password);
		add(JL_FirefoxPath);
		add(Text_Firefox);
		add(JB_CURL);
		add(auto);
	}

	public void load() {
		MailProperties xsp = new MailProperties();
		Text_MailServer.setText(xsp.getPOP3Server());
		Text_MailUser.setText(xsp.getMailUser());
		Password.setText(xsp.getMailPass());
		Text_T.setText("" + xsp.getMailPeriod());
		Text_Firefox.setText(xsp.getFirefoxPath());
		auto.setSelected(xsp.isMailRun());

	}

	public void set() {
		MailProperties m = new MailProperties();
		m.setMail(Text_MailServer.getText(), Text_MailUser.getText(),
				new String(Password.getPassword()), Text_Firefox.getText(),
				Text_T.getText(), auto.isSelected());
		MailProperties.write();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "...") {
			JFileChooser ch = new JFileChooser();
			ch.showOpenDialog(this);
			File f = ch.getSelectedFile();
			if (f != null) {
				String file = f.getAbsolutePath();
				if (file != null) {
					if (e.getSource() == JB_CURL) {
						Text_Firefox.setText(file);
					}
				}
			}
		}
	}
}