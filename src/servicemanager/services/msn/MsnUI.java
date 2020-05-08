package servicemanager.services.msn;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.border.TitledBorder;

public class MsnUI extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JCheckBox jCheckSplit = null;

	private JList sourceList = null;

	private JLabel JL_Output = null;

	private JTextField JT_Output = null;

	private JButton JB_sel = null;

	private JButton JB_Parser = null;

	private JButton JB_ADD = null;

	private JButton JB_RM = null;

	/**
	 * @param owner
	 */
	public MsnUI(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 250);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			JL_Output = new JLabel();
			JL_Output.setBounds(new Rectangle(20, 190, 50, 20));
			JL_Output.setText("Output");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJCheckSplit(), null);
			jContentPane.add(JL_Output, null);
			jContentPane.add(getJT_Output(), null);
			jContentPane.add(getJB_sel(), null);
			jContentPane.add(getJB_Parser(), null);
			jContentPane.add(getJB_ADD(), null);
			jContentPane.add(getJB_RM(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setOpaque(false);
			jScrollPane.setBounds(new Rectangle(20, 20, 300, 140));
			jScrollPane.setViewportView(getSourceList());
			jScrollPane.setBorder(BorderFactory
					.createTitledBorder(BorderFactory.createLineBorder(
							new Color(153, 153, 255), 1), "Source",
							TitledBorder.DEFAULT_JUSTIFICATION,
							TitledBorder.DEFAULT_POSITION, new Font(
									"DFPPOPCorn-W12", Font.PLAIN, 12),
							new Color(153, 153, 255)));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jCheckSplit
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckSplit() {
		if (jCheckSplit == null) {
			jCheckSplit = new JCheckBox();
			jCheckSplit.setOpaque(false);
			jCheckSplit.setBounds(new Rectangle(335, 23, 65, 21));
			jCheckSplit.setText("Split");
			jCheckSplit.setSelected(true);
		}
		return jCheckSplit;
	}

	/**
	 * This method initializes sourceList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getSourceList() {
		if (sourceList == null) {
			sourceList = new JList();
		}
		return sourceList;
	}

	/**
	 * This method initializes JT_Output
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJT_Output() {
		if (JT_Output == null) {
			JT_Output = new JTextField();
			JT_Output.setBounds(new Rectangle(70, 190, 245, 20));
		}
		return JT_Output;
	}

	/**
	 * This method initializes JB_sel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_sel() {
		if (JB_sel == null) {
			JB_sel = new JButton();
			JB_sel.setOpaque(false);
			JB_sel.setBounds(new Rectangle(328, 193, 15, 15));
			JB_sel.setText("...");
			JB_sel.addActionListener(this);
		}
		return JB_sel;
	}

	/**
	 * This method initializes JB_Parser
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_Parser() {
		if (JB_Parser == null) {
			JB_Parser = new JButton();
			JB_Parser.setText("Parse");
			JB_Parser.setOpaque(false);
			JB_Parser.setBounds(new Rectangle(340, 120, 85, 20));
			JB_Parser.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ListModel model = sourceList.getModel();
					String paths[] = new String[model.getSize()];
					for (int i = 0; i < model.getSize(); i++) {
						paths[i] = (String) model.getElementAt(i);
					}

					Msn msn = new Msn("MSN");
					// start parser
					msn.start(paths, JT_Output.getText(), jCheckSplit
							.isSelected());

				}
			});
		}
		return JB_Parser;
	}

	/**
	 * This method initializes jB_ADD
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_ADD() {
		if (JB_ADD == null) {
			JB_ADD = new JButton();
			JB_ADD.setOpaque(false);
			JB_ADD.setBounds(new Rectangle(340, 50, 85, 20));
			JB_ADD.setText("ADD");
			JB_ADD.addActionListener(this);
		}
		return JB_ADD;
	}

	/**
	 * This method initializes JB_RM
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_RM() {
		if (JB_RM == null) {
			JB_RM = new JButton();
			JB_RM.setOpaque(false);
			JB_RM.setBounds(new Rectangle(340, 85, 85, 20));
			JB_RM.setText("Remove");
			JB_RM.addActionListener(this);
		}
		return JB_RM;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("ADD")
				|| e.getActionCommand().equalsIgnoreCase("...")) {
			JFileChooser fchooser = new JFileChooser(System
					.getProperty("user.dir"));
			fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fchooser.showOpenDialog(this);
			File f = fchooser.getSelectedFile();
			if (f != null) {
				String file = f.getAbsolutePath();
				if (file != null) {
					if (e.getSource() == JB_ADD) {
						ListModel model = sourceList.getModel();
						Vector<Object> v = new Vector<Object>();
						for (int i = 0; i < model.getSize(); i++) {
							v.add(model.getElementAt(i));
						}
						v.add(file);
						sourceList.setListData(v);
					} else {
						JT_Output.setText(file);
					}
				}
			}
		} else if (e.getActionCommand().equalsIgnoreCase("Remove")) {
			int sel = sourceList.getSelectedIndex();
			if (sel != -1) {
				ListModel model = sourceList.getModel();
				Vector<Object> v = new Vector<Object>();
				for (int i = 0; i < model.getSize(); i++) {
					v.add(model.getElementAt(i));
				}
				v.remove(sel);
				sourceList.setListData(v);
			}
		}

	}
}
