package explorer.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileTreeUtilities {

	private FileTreeUtilities() {
	}

	public static boolean hasChildFolder(File f) {

		try {
			if (f.isFile())
				return false;

			File[] list = f.listFiles();
			if (list == null)
				return false;

			int s = list.length;
			for (int i = 0; i < s; i++) {
				if (list[i].isDirectory()) {
					return true;
				}
			}
			return false;
		} catch (Throwable e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean hasChildFiles(File f) {
		try {
			if (f.isFile())
				return false;

			File[] list = f.listFiles();
			if (list == null)
				return false;

			if (list.length > 0)
				return true;
			return false;
		} catch (Throwable e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean hasChildFiles(File f, String[] ff) {
		try {
			if (f.isFile())
				return false;

			File[] list = f.listFiles();
			boolean filteredFile;
			int fileNum = list.length;
			for (File fe : list) {
				if (fe.isDirectory()){ // 디렉토리 경우 자식으로 생각
					continue;
				}
				filteredFile = false;
				for (String s : ff) {
					if (fe.getName().toLowerCase().endsWith(s)){ // 필더된 파일의 경우 자식으로 생각
						filteredFile = true;
						break;
					}
				}
				if (!filteredFile){ 
					fileNum--;
				}
			}
			if (list == null)
				return false;
			if (fileNum == 0)
				return false;
			if (fileNum > 0)
				return true;
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	public static File[] getChildFolder(File f) {
		try {
			File[] files = f.listFiles();

			ArrayList<File> da = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					da.add(files[i]);
				}
			}
			Collections.sort(da);
			File[] list = new File[da.size()];
			da.toArray(list);

			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new File[] {};
		}
	}

	public static File[] getChildFiles(File f) {
		try {
			File[] files = f.listFiles();

			ArrayList<File> da = new ArrayList<File>();
			ArrayList<File> fa = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					da.add(files[i]);
				} else {
					fa.add(files[i]);
				}
			}
			Collections.sort(da);
			Collections.sort(fa);

			da.addAll(fa);
			File[] list = new File[da.size()];
			da.toArray(list);

			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new File[] {};
		}
	}

	public static File[] getChildFiles(File f, String[] ff) {
		try {
			File[] files = f.listFiles();

			ArrayList<File> da = new ArrayList<File>();
			ArrayList<File> fa = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					da.add(files[i]);
				} else {
					for (String s : ff) {
						if (files[i].getName().toLowerCase().endsWith(s)){
							fa.add(files[i]);
							break;
						}
					}
				}
			}
			Collections.sort(da);
			Collections.sort(fa);

			da.addAll(fa);
			File[] list = new File[da.size()];
			da.toArray(list);

			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new File[] {};
		}
	}
}