package servicemanager.abstracts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public abstract class AbstractUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	protected Border border;

	public AbstractUI() {
		super();
	}

	protected void initialize(String title) {
		border = new TitledBorder(title);
		setLayout(null);
		setBorder(border);
		setOpaque(false);
	}

	public abstract void load();

	public abstract void set();

	public void actionPerformed(ActionEvent arg0) {
	}
}
