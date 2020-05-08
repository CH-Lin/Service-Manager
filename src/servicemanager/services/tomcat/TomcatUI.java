package servicemanager.services.tomcat;

import javax.swing.*;
import servicemanager.abstracts.AbstractUI;

public class TomcatUI extends AbstractUI {

	private static final long serialVersionUID = 1L;

	private JTextField Text_Tomcat = new JTextField();

	private JLabel JL_Tomcat = new JLabel();

	private JCheckBox JC_T_AUTO = new JCheckBox();

	private JCheckBox JC_T_KEEP = new JCheckBox();

	/**
	 * This is the default constructor
	 */
	public TomcatUI() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		super.initialize("Tomcat");
		setSize(335, 80);
		JL_Tomcat.setText("Tomcat Service:");
		JC_T_KEEP.setOpaque(false);
		JC_T_KEEP.setText("Keep Running");
		JC_T_AUTO.setOpaque(false);
		JC_T_AUTO.setText("Auto Run");
		JL_Tomcat.setBounds(10, 20, 80, 21);
		Text_Tomcat.setBounds(85, 20, 230, 21);
		JC_T_KEEP.setBounds(130, 50, 100, 21);
		JC_T_AUTO.setBounds(10, 50, 80, 21);
		Text_Tomcat.setOpaque(false);
		add(JL_Tomcat);
		add(Text_Tomcat);
		add(JC_T_KEEP);
		add(JC_T_AUTO);
	}

	public void load() {
		TomcatProperties xsp = new TomcatProperties();
		Text_Tomcat.setText(xsp.getTomcatName());
		JC_T_AUTO.setSelected(xsp.isTomcatRun());
		JC_T_KEEP.setSelected(xsp.isTomcatKeep());

	}

	public void set() {
		TomcatProperties t = new TomcatProperties();
		t.setTomcat(Text_Tomcat.getText(), JC_T_AUTO.isSelected(), JC_T_KEEP
				.isSelected());
		TomcatProperties.write();
	}

}
