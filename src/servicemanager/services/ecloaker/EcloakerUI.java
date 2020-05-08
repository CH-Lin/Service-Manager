package servicemanager.services.ecloaker;

import servicemanager.abstracts.AbstractUI;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class EcloakerUI extends AbstractUI {

	private static final long serialVersionUID = 1L;
	private JPanel JP_control = null;
	private JLabel jl_email = null;
	private JLabel jl_text = null;
	private JLabel jl_alter = null;
	private JTextField jtext_email = null;

	private JTextField jt_text = null;

	private JTextField jtext_alter = null;
	private JButton jb_clear = null;
	private JButton jb_code = null;
	private JScrollPane jScroll_text = null;
	private JTextArea text_result = null;

	/**
	 * This method initializes
	 * 
	 */
	public EcloakerUI() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		super.initialize("E-MAIL Cloaker");
		this.setLayout(new BorderLayout());
		setSize(335, 285);

		this.add(getJP_control(), BorderLayout.NORTH);
		this.add(getJScroll_text(), BorderLayout.CENTER);
	}

	public void load() {
	}

	public void set() {
	}

	/**
	 * This method initializes JP_control
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJP_control() {
		if (JP_control == null) {
			jl_alter = new JLabel();
			jl_alter.setBounds(new Rectangle(10, 70, 90, 20));
			jl_alter.setText("Alternate Text");
			jl_text = new JLabel();
			jl_text.setText("Text");
			jl_text.setBounds(new Rectangle(10, 40, 90, 20));
			jl_email = new JLabel();
			jl_email.setText("E-mail Address");
			jl_email.setBounds(new Rectangle(10, 10, 90, 20));
			JP_control = new JPanel();
			JP_control.setLayout(null);
			JP_control.setPreferredSize(new Dimension(335, 130));
			JP_control.setOpaque(false);
			JP_control.add(jl_email, null);
			JP_control.add(getText_email(), null);
			JP_control.add(jl_text, null);
			JP_control.add(getJt_text(), null);
			JP_control.add(jl_alter, null);
			JP_control.add(getJtext_alter(), null);
			JP_control.add(getJb_clear(), null);
			JP_control.add(getJb_code(), null);
		}
		return JP_control;
	}

	/**
	 * This method initializes text_email
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getText_email() {
		if (jtext_email == null) {
			jtext_email = new JTextField(70);
			jtext_email.setBounds(new Rectangle(100, 10, 200, 22));
			jtext_email.setOpaque(false);
		}
		return jtext_email;
	}

	/**
	 * This method initializes jt_text
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJt_text() {
		if (jt_text == null) {
			jt_text = new JTextField(70);
			jt_text.setBounds(new Rectangle(100, 40, 200, 22));
			jt_text.setOpaque(false);
		}
		return jt_text;
	}

	/**
	 * This method initializes jtext_alter
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtext_alter() {
		if (jtext_alter == null) {
			jtext_alter = new JTextField(70);
			jtext_alter.setBounds(new Rectangle(100, 70, 200, 22));
			jtext_alter.setOpaque(false);
		}
		return jtext_alter;
	}

	/**
	 * This method initializes jScroll_text
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScroll_text() {
		if (jScroll_text == null) {
			jScroll_text = new JScrollPane();
			jScroll_text.setOpaque(false);
			jScroll_text.setViewportView(getText_result());
		}
		return jScroll_text;
	}

	/**
	 * This method initializes text_result
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getText_result() {
		if (text_result == null) {
			text_result = new JTextArea();
			text_result.setOpaque(false);
			text_result.setEditable(false);
		}
		return text_result;
	}

	/**
	 * This method initializes jb_clear
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJb_clear() {
		if (jb_clear == null) {
			jb_clear = new JButton();
			jb_clear.setBounds(new Rectangle(10, 105, 100, 22));
			jb_clear.setOpaque(false);
			jb_clear.setText("Clear");
			jb_clear.addActionListener(this);
		}
		return jb_clear;
	}

	/**
	 * This method initializes jb_code
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJb_code() {
		if (jb_code == null) {
			jb_code = new JButton();
			jb_code.setBounds(new Rectangle(120, 105, 100, 22));
			jb_code.setOpaque(false);
			jb_code.setText("make code");
			jb_code.addActionListener(this);
		}
		return jb_code;
	}

	public void clear() {
		jtext_email.setText("");
		jtext_alter.setText("");
		jt_text.setText("");
		text_result.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Clear")) {
			clear();
		} else {
			StringBuffer text = new StringBuffer("<a href=\"");
			for (char c : "mailto:".toCharArray()) {
				text.append("&#");
				text.append((int) c);
				text.append(";");
			}

			for (char c : jtext_email.getText().toCharArray()) {
				text.append("&#");
				text.append((int) c);
				text.append(";");
			}

			text.append("\" title=\"");

			for (char c : jtext_alter.getText().toCharArray()) {
				text.append("&#");
				text.append((int) c);
				text.append(";");
			}

			text.append("\">");

			for (char c : jt_text.getText().toCharArray()) {
				text.append("&#");
				text.append((int) c);
				text.append(";");
			}

			text.append("</a>");
			text_result.setText(text.toString());
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
