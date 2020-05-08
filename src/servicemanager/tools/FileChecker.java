package servicemanager.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class FileChecker {
	public static String CRC32(File f) throws IOException {

		FileInputStream fis = new FileInputStream(f);

		CRC32 c = new CRC32();

		byte[] digest = new byte[10240];
		int read = 0;
		while ((read = fis.read(digest)) != -1) {
			c.update(digest, 0, read);

		}
		fis.close();
		return Long.toHexString(c.getValue()).toUpperCase();
	}

	public static String MD5(File f) throws IOException {

		FileInputStream fis = new FileInputStream(f);

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
		}

		byte[] digest = new byte[10240];
		int read = 0;
		while ((read = fis.read(digest)) != -1) {
			md.update(digest, 0, read);
		}
		fis.close();
		byte[] out = md.digest();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < out.length; ++i) {
			final byte b = out[i];
			final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
			buffer.append(value < 16 ? "0" : "");
			buffer.append(Integer.toHexString(value));
		}

		return buffer.toString().toUpperCase();
	}
}
