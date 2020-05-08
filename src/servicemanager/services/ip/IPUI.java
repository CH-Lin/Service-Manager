package servicemanager.services.ip;

import javax.swing.*;
import servicemanager.abstracts.AbstractUI;

public class IPUI extends AbstractUI {

	private static final long serialVersionUID = 1L;

	private JLabel JL_Address = new JLabel();

	private JLabel JL_User = new JLabel();

	private JLabel JL_Password = new JLabel();

	private JLabel JL_Time = new JLabel();

	private JTextField Text_A = new JTextField();

	private JTextField Text_U = new JTextField();

	private JTextField Text_T = new JTextField();

	private JPasswordField Password = new JPasswordField();

	private JCheckBox JC_IP = new JCheckBox();

	/**
	 * This is the default constructor
	 */
	public IPUI() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		super.initialize("IP Writer");
		setSize(335, 150);
		JL_Address.setText("Address:");
		JL_User.setText("User:");
		JL_Password.setText("Password:");
		JL_Time.setText("Time period:");
		Password.setEchoChar('*');
		JC_IP.setOpaque(false);
		JC_IP.setText("Auto Run");
		JL_User.setBounds(10, 45, 50, 21);
		JL_Address.setBounds(10, 20, 50, 21);
		JL_Password.setBounds(10, 70, 50, 21);
		JL_Time.setBounds(10, 95, 100, 21);
		Text_U.setBounds(75, 45, 240, 21);
		Text_A.setBounds(75, 20, 240, 21);
		Text_T.setBounds(75, 95, 240, 21);
		Text_U.setOpaque(false);
		Text_A.setOpaque(false);
		Text_T.setOpaque(false);
		Password.setOpaque(false);
		Password.setBounds(75, 70, 240, 20);
		JC_IP.setBounds(10, 120, 80, 21);
		add(Text_U);
		add(JL_User);
		add(JL_Address);
		add(Text_A);
		add(JL_Password);
		add(Password);
		add(JL_Time);
		add(Text_T);
		add(JC_IP);
	}

	public void load() {
		IPProperties xsp = new IPProperties();
		Text_A.setText(xsp.getFTPServer());
		Text_U.setText(xsp.getFTPUser());
		Password.setText(xsp.getFTPPassword());
		Text_T.setText("" + xsp.getFTPPeriod());
		JC_IP.setSelected(xsp.isIPRun());

	}

	public void set() {
		IPProperties i = new IPProperties();
		i.setIP(Text_A.getText(), Text_U.getText(), new String(Password
				.getPassword()), Text_T.getText(), JC_IP.isSelected());
		IPProperties.write();
	}
}
