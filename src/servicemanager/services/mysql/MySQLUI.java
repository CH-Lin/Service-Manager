package servicemanager.services.mysql;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;
import servicemanager.abstracts.AbstractUI;

public class MySQLUI extends AbstractUI {

	private static final long serialVersionUID = 1L;

	private JLabel JL_Mysql_Path = new JLabel();

	private JLabel JL_Mysql_INI = new JLabel();

	private JTextField Text_Mysql_Path = new JTextField();

	private JTextField Text_Mysql_INI = new JTextField();

	private JButton JB_Mysql_Path = new JButton();

	private JButton JB_Mysql_INI = new JButton();

	private JCheckBox JC_M_AUTO = new JCheckBox();

	private JCheckBox JC_M_KEEP = new JCheckBox();

	/**
	 * This is the default constructor
	 */
	public MySQLUI() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		super.initialize("MySQL");
		setSize(335, 110);
		JL_Mysql_Path.setText("MySQL Path:");
		JL_Mysql_INI.setText("MySQL INI:");
		JC_M_AUTO.setOpaque(false);
		JC_M_AUTO.setText("Auto Run");
		JC_M_KEEP.setOpaque(false);
		JC_M_KEEP.setText("Keep Running");
		JB_Mysql_Path.setOpaque(false);
		JB_Mysql_Path.setText("...");
		JB_Mysql_INI.setOpaque(false);
		JB_Mysql_INI.setText("...");
		JB_Mysql_Path.addActionListener(this);
		JB_Mysql_INI.addActionListener(this);
		JL_Mysql_Path.setBounds(10, 20, 80, 21);
		JL_Mysql_INI.setBounds(10, 45, 80, 21);
		Text_Mysql_Path.setBounds(75, 20, 190, 21);
		Text_Mysql_INI.setBounds(75, 45, 190, 21);
		Text_Mysql_Path.setOpaque(false);
		Text_Mysql_INI.setOpaque(false);
		JB_Mysql_Path.setBounds(275, 20, 20, 20);
		JB_Mysql_INI.setBounds(275, 45, 20, 20);
		JC_M_KEEP.setBounds(130, 70, 100, 21);
		JC_M_AUTO.setBounds(10, 70, 80, 21);
		add(JC_M_KEEP);
		add(JL_Mysql_Path);
		add(JL_Mysql_INI);
		add(Text_Mysql_Path);
		add(Text_Mysql_INI);
		add(JB_Mysql_Path);
		add(JB_Mysql_INI);
		add(JC_M_AUTO);
	}

	public void load() {
		MySQLProperties xsp = new MySQLProperties();
		Text_Mysql_Path.setText(xsp.getMysqlPath());
		Text_Mysql_INI.setText(xsp.getMysqlIniPath());
		JC_M_AUTO.setSelected(xsp.isMysqlRun());
		JC_M_KEEP.setSelected(xsp.isMySQLKeep());

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "...") {
			JFileChooser ch = new JFileChooser();
			ch.showOpenDialog(this);
			File f = ch.getSelectedFile();
			if (f != null) {
				String file = f.getAbsolutePath();
				if (file != null) {
					if (e.getSource() == JB_Mysql_Path) {
						Text_Mysql_Path.setText(file);
					} else if (e.getSource() == JB_Mysql_INI) {
						Text_Mysql_INI.setText(file);
					}
				}
			}
		}
	}

	public void set() {
		MySQLProperties m = new MySQLProperties();
		m.setMySQL(Text_Mysql_Path.getText(), Text_Mysql_INI.getText(),
				JC_M_AUTO.isSelected(), JC_M_KEEP.isSelected());
		MySQLProperties.write();
	}

}
