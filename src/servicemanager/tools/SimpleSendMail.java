package servicemanager.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleSendMail {
	public static void send(String to, String from, String server, String user,
			String pass, String text) throws MessagingException {

		InternetAddress[] address = null;

		address = InternetAddress.parse(to, false);
		Properties props = new Properties();
		props.put("mail.smtp.host", server);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		javax.mail.Session sessmail = javax.mail.Session.getInstance(props);
		MimeMessage msg = new MimeMessage(sessmail);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, address);
		msg.setSubject("Free Space Info.");
		msg.setSentDate(new Date());
		msg.setText(text, "Big5");

		Transport transport = sessmail.getTransport("smtp");
		System.out.printf("%-28tc Login to SMTP Server..........%n", Calendar
				.getInstance());
		transport.connect(server, user, pass);
		System.out
				.printf(
						"%-28tc Login to SMTP Server......[OK]%n%-28tc Send Free Space Info..........%n",
						Calendar.getInstance(), Calendar.getInstance());
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();

		System.out
				.printf(
						"%-28tc Send Free Space Info......[OK]%n%-28tc Login out.....................%n",
						Calendar.getInstance(), Calendar.getInstance());
	}
}
