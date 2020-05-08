package servicemanager;

import java.awt.*;
import javax.swing.*;
import servicemanager.abstracts.AbstractUI;
import java.awt.event.*;
import java.awt.Dimension;
import java.io.File;

public class ServiceManagerPreferences extends JDialog implements
		ActionListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JPanel dlg = new JPanel();

	private AbstractUI ui;

	private JButton JB_CLOSE = new JButton();

	private int x, y, space = 20;

	public ServiceManagerPreferences() {
		super(new Frame(), "Preferences", false);

		try {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
			pack();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		setUndecorated(true);
		getContentPane().add(dlg);
		setAlwaysOnTop(true);
		setResizable(false);
		addMouseListener(this);
		addMouseMotionListener(this);
		background();
		JB_CLOSE.setOpaque(false);
		JB_CLOSE.setText("Close");
		JB_CLOSE.addActionListener(this);
		dlg.setLayout(null);
		dlg.setBackground(Color.white);
		dlg.add(JB_CLOSE);
	}

	private void background() {
		ImageIcon im = new ImageIcon("configs" + File.separator + "bg.jpg");
		Image image = (Image) this.getGraphicsConfiguration()
				.createCompatibleImage(im.getIconWidth(), im.getIconHeight(),
						Transparency.TRANSLUCENT);
		Graphics2D g = (Graphics2D) image.getGraphics();
		Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				.3f);
		g.setComposite(alpha);
		g.drawImage(im.getImage(), 0, 0, null);
		g.dispose();
		ImageIcon defaultIcon = new ImageIcon(image);
		JLabel l = new JLabel();
		l.setIcon(defaultIcon);
		l.setBounds(0, 0, 450, 700);
		dlg.add(l);
	}

	public void Open(AbstractUI ui) {
		try {
			dlg.remove(this.ui);
		} catch (NullPointerException e) {
		}
		this.ui = ui;
		ui.setBounds(space, space, ui.getWidth(), ui.getHeight());
		ui.load();
		dlg.add(ui);
		JB_CLOSE.setBounds(ui.getWidth() + space * 2, 40, 60, 21);
		setSize(ui.getWidth() + space * 3 + JB_CLOSE.getWidth(), ui.getHeight()
				+ space * 2);
		setMinimumSize(new Dimension(ui.getWidth() + space * 3
				+ JB_CLOSE.getWidth(), ui.getHeight() + space * 2));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Close") {
			ui.set();
			dispose();
		}
	}

	public void mouseDragged(MouseEvent e) {
		this.setBounds(getX() - x + e.getX(), getY() - y + e.getY(),
				getWidth(), getHeight());

	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
