package servicemanager.services.mail;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import servicemanager.abstracts.AbstractServices;

public class Mail extends AbstractServices implements Runnable {

	private Boolean start;

	private Thread mail;

	private final String dmhy = "http://share.dmhy.net/";

	private MailProperties xsp;

	public Mail(String name) {
		super(name);// serviceName = "Mail";
		this.xsp = new MailProperties();
	}

	public void exit() {
		start = false;
	}

	public void run() {
		start = true;
		System.out.printf("%-28tc Start Check Mail..............%n", Calendar
				.getInstance());
		while (start) {

			try {
				receive();
			} catch (MessagingException e) {
				System.out.printf("%-28tc Connect failed................%n",
						Calendar.getInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(xsp.getMailPeriod());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.gc();
		}
		System.out.printf("%-28tc Stop Check Mail...............%n", Calendar
				.getInstance());
	}

	public boolean start() {
		if (mail == null) {
			mail = new Thread(this);
			mail.start();
			System.out.printf("%-28tc Mail Service Start........[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean stop() {
		if (mail != null) {
			exit();
			mail = null;
			System.out.printf("%-28tc Mail Service Stop.........[OK]%n",
					Calendar.getInstance());
			return true;
		} else
			return false;
	}

	public boolean open() {
		return false;
	}

	private void receive() throws IOException, MessagingException {
		// 連接到 POP3 伺服主機
		System.out.printf("%-28tc Connect to Mail Server.......%n", Calendar
				.getInstance());
		Properties p = System.getProperties();
		p.put("mail.pop3.host", xsp.getPOP3Server());
		Store store = Session.getDefaultInstance(p, null).getStore("pop3");
		store
				.connect(xsp.getPOP3Server(), xsp.getMailUser(), xsp
						.getMailPass());
		Folder folder = store.getFolder("inbox");
		folder.open(Folder.READ_WRITE);
		Message[] message = folder.getMessages();

		int total = folder.getMessageCount();

		for (int i = 0; i < total; i++) {
			if (message[i].getSubject() != null) {
				Object o = message[i].getContent();
				String content = "";

				if (!(o instanceof Multipart))
					content += o;
				else {
					Multipart MP = (Multipart) o;
					for (int j = 0; j < MP.getCount(); j++) {
						MimeBodyPart MBP = (MimeBodyPart) MP.getBodyPart(j);

						if (MBP.getContentType().indexOf("text/plain") > -1) {
							content += MBP.getContent();
						}
					}
				}

				if (message[i].getSubject().equalsIgnoreCase("DMHY")) {
					BTAction(content, true);
				} else if (message[i].getSubject().equalsIgnoreCase("TORRENT")) {
					BTAction(content, false);
				} else if (message[i].getSubject().equalsIgnoreCase("COMMAND")) {

				}
			} else {

			}
		}

		store.close();
		System.out.printf("%-28tc Close Connection..............%n", Calendar
				.getInstance());
	}

	private void BTAction(String content, boolean p) throws MessagingException,
			IOException {
		LinkedList<String> list = new LinkedList<String>();
		StringTokenizer st = new StringTokenizer(content);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		for (String s : list) {
			if (s.startsWith("http")) {
				System.out.println(s);
				try {
					String t = (p) ? getDmhyTorrent(s) : s;
					System.out.println(t);
					addSeed(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getDmhyTorrent(String u) throws IOException {
		String path = "";
		URLConnection webPage = new URL(u).openConnection();
		InputStreamReader ISR = new InputStreamReader(webPage.getInputStream(),
				"UTF-8");
		int read;
		char[] page = new char[10000];
		while (true) {
			read = ISR.read(page);
			if (read == -1)
				break;
			path += new String(page, 0, read);

		}
		ISR.close();

		if (path.indexOf("btdown.php") != -1) {
			path = path.substring(path.indexOf("btdown.php"), path.length());
			path = path.substring(0, path.indexOf("\""));
			return dmhy + path;
		} else
			return dmhy;
	}

	private void addSeed(String torrentUrl) throws IOException,
			InterruptedException {
		String cmd = xsp.getFirefoxPath() + " \"" + torrentUrl + "\"";
		Runtime.getRuntime().exec(cmd);
		Thread.sleep(5000);
		Runtime.getRuntime().exec(cmd);
		Thread.sleep(15000);
	}
}
