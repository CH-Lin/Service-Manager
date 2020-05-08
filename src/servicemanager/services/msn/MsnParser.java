package servicemanager.services.msn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.dom.DOMProcessingInstruction;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MsnParser {

	private final static String sp = File.separator;

	private final static DOMProcessingInstruction domProcess = new DOMProcessingInstruction(
			"xml-stylesheet", "href=\"MessageLog.xsl\" type=\"text/xsl\"");

	private static HashMap<String, Message> messages;

	public MsnParser() {
		messages = new HashMap<String, Message>();
	}

	public void clear() {
		messages = new HashMap<String, Message>();
	}

	public void parse(String[] path) throws DocumentException {
		for (String p : path)
			parse(p);
	}

	public void parse(String path) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document d = saxReader.read(new File(path));
		Element root = d.getRootElement();

		String elements[] = { "Message", "InvitationResponse", "Invitation" };

		List<?> list;
		for (String element : elements) {
			list = root.elements(element);
			for (Object o : list.toArray()) {
				Message m = new Message((Element) o);
				messages.put(m.toString(), m);
			}
		}
	}

	public void output(String output) throws IOException {

		Document doc = new DocumentFactory().createDocument();
		DOMElement log = new DOMElement("Log");
		log.addAttribute("FirstSessionID", "1");
		doc.setRootElement(log);

		int session = 0;
		String now = null;
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");

		for (Message o : sort()) {
			if (now == null) {
				++session;
				now = df.format(o.getDate());
			}
			if (!now.equals(df.format(o.getDate()))) {
				++session;
				now = df.format(o.getDate());
			}

			o.setSessionID(session);
			Element element = o.element;
			log.add(element.createCopy());
		}
		log.addAttribute("LastSessionID", "" + session);
		write(doc, output);
	}

	public void output(String path, String file) throws IOException {

		SimpleDateFormat dayFormat = new SimpleDateFormat("M/d/yyyy");
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy MM");

		Document doc = new DocumentFactory().createDocument();
		DOMElement log = new DOMElement("Log");
		log.addAttribute("FirstSessionID", "1");
		doc.setRootElement(log);

		int session = 0;
		String day = null, month = null;

		for (Message o : sort()) {
			if (day == null) {
				++session;
				day = dayFormat.format(o.getDate());
				month = monthFormat.format(o.getDate());
			}
			if (!day.equals(dayFormat.format(o.getDate()))) {
				++session;
				day = dayFormat.format(o.getDate());
			}

			if (!month.equals(monthFormat.format(o.getDate()))) {

				log.addAttribute("LastSessionID", "" + session);
				new File(path + sp + month).mkdirs();
				write(doc, path + sp + month + sp + file);

				month = monthFormat.format(o.getDate());

				doc = new DocumentFactory().createDocument();
				log = new DOMElement("Log");
				log.addAttribute("FirstSessionID", "1");
				doc.setRootElement(log);
				session = 1;
			}

			o.setSessionID(session);
			Element element = o.element;
			log.add(element.createCopy());

		}

		log.addAttribute("LastSessionID", "" + session);
		new File(path + sp + month).mkdirs();
		write(doc, path + sp + month + sp + file);
	}

	private void write(Document doc, String path) throws IOException {
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path));
		xmlWriter.write(domProcess);
		xmlWriter.write(doc);
		xmlWriter.close();
	}

	private Message[] sort() {

		Message[] message = new Message[messages.values().size()];
		Object[] o = messages.values().toArray();
		for (int i = 0; i < o.length; i++)
			message[i] = (Message) o[i];
		Arrays.sort(message, new MessageSort());

		return message;
	}

	private class MessageSort implements Comparator<Message> {

		public int compare(Message arg0, Message arg1) {
			if (arg0.getDate().getTime() > arg1.getDate().getTime())
				return 1;
			else if (arg0.getDate().getTime() < arg1.getDate().getTime())
				return -1;
			return 0;
		}
	}

	private class Message {

		public Element element;

		public Message(Element element) {
			this.element = element;

			try {
				SimpleDateFormat df = null;
				String date = element.attribute("Date").getValue();
				if (date.startsWith("20")) {
					df = new SimpleDateFormat("yyyy/M/d");
				} else
					df = new SimpleDateFormat("M/d/yyyy");

				Date d = df.parse(date);
				date = String.format("%tY/%tm/%td", d, d, d);
				element.attribute("Date").setValue(date);

				String time = element.attribute("Time").getValue();
				if (time.indexOf("下") != -1) {
					time = time.replaceAll("下午", "").replaceAll(" ", "");
					time += " PM";
				}
				if (time.indexOf("上") != -1) {
					time = time.replaceAll("上午", "").replaceAll(" ", "");
					time += " AM";
				}
				df = new SimpleDateFormat("h:mm:ss a");
				Date t = df.parse(time);
				time = String.format("%tI:%tM:%tS %Tp", t, t, t, t);
				element.attribute("Time").setValue(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public Date getDate() {

			try {
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy/MM/dd:hh:mm:ss a");
				String time = element.attribute("Date").getValue() + ":"
						+ element.attribute("Time").getValue();
				return df.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void setSessionID(int id) {
			element.attribute("SessionID").setValue("" + id);
		}

		public String toString() {
			String date = "", time = "", from = "", to = "", text = "";
			try {
				date = element.attribute("Date").getValue();
			} catch (NullPointerException e) {
			}
			try {
				time = element.attribute("Time").getValue();
			} catch (NullPointerException e) {
			}
			try {
				from = element.element("From").element("User").attribute(
						"FriendlyName").getValue();
			} catch (NullPointerException e) {
			}
			try {
				to = element.element("To").element("User").attribute(
						"FriendlyName").getValue();
			} catch (NullPointerException e) {
			}
			try {
				text = element.element("Text").getText();
			} catch (NullPointerException e) {
			}

			return date + " " + time + " " + from + " " + to + " " + text;
		}
	}

}
