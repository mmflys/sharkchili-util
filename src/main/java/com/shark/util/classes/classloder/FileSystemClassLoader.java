package com.shark.util.classes.classloder;

import com.shark.util.Exception.ClassLoaderException;
import com.shark.util.util.scan.UrlScanner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class FileSystemClassLoader extends AbstractSharkClassLoader {

	public FileSystemClassLoader(String parentPath) {
		this.parentUrl = parentPath;
	}

	@Override
	URL searchUrl(String className) throws MalformedURLException {
		className=className.replace('.', File.separatorChar);
		// Search file from the parent directory
		File directory=new File(parentUrl);
		if (!directory.isDirectory()) {
			throw new ClassLoaderException("parent url is`t a directory");
		}else {
			// whether the class is in the root path
			String path=parentUrl+File.separatorChar+className+ ".class";
			File firstFile=new File(path);
			if (firstFile.exists()) {
				return firstFile.toURI().toURL();
			}else {
				// 形如com.fisybyby.FileUtil.class
				className=className.replace('.', File.separatorChar)+".class";
				// scan directory
				return new UrlScanner(parentUrl).search(className);
			}
		}
	}
}
