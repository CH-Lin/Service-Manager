package servicemanager.tools;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class FreeSpaceTools {

	public static LinkedList<File> getFreeSpaceInfo() {
		LinkedList<File> list;
		list = new LinkedList<File>();

		String dir[] = { "C:/", "D:/", "E:/", "F:/", "G:/", "H:/", "I:/",
				"J:/", "K:/", "L:/", "M:/", "N:/", "O:/", "P:/", "Q:/", "R:/",
				"S:/", "T:/", "U:/", "V:/", "W:/", "X:/", "Y:/", "Z:/" };

		for (String path : dir) {
			File f = new File(path);
			if (f.exists() & f.getFreeSpace() > 0) {
				list.add(f);
			}
		}

		Collections.sort(list, new FreeSpaceComparator<File>());
		return list;
	}
}

class FreeSpaceComparator<T> implements Comparator<T> {

	public int compare(T arg0, T arg1) {
		// TODO Auto-generated method stub
		if (((File) arg0).getFreeSpace() > ((File) arg1).getFreeSpace())
			return -1;
		else if (((File) arg0).getFreeSpace() < ((File) arg1).getFreeSpace())
			return 1;
		return 0;
	}

}
