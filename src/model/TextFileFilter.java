package model;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		
		String fileName = pathname.getName().toLowerCase();
		
		if (fileName.endsWith(".txt"))
			return true;
		
		return false;
	}
}
