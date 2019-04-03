package com.shark.util.util.scan;

import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/11/29 0029
 */
public abstract class AbstractScanner<T> implements Scanner<T> {
	String parentPath;
	boolean finished;
	boolean recursive;
	ScanType type;

	public AbstractScanner(String parentPath) {
		this(parentPath,true);
	}

	public AbstractScanner(String parentPath, boolean recursive) {
		this.parentPath = parentPath;
		this.recursive = recursive;
	}

	public Set<T> scan(Predicate<T>...predicate) {
		this.type= ScanType.MULTI_FILE;
		Set<T> files = Sets.newHashSet();
		scanFiles(parentPath, files, predicate);
		resetType();
		return files;
	}

	/**
	 * Scan all files denoted by this abstract pathname
	 *  @param url     file path
	 * @param classes story files get by scanning
	 * @param filters  filter files
	 */
	void scanFiles(String url, Set<T> classes, Predicate<T>...filters) {
		try {
			scanFiles(new File(url), classes, filters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scan all files denoted by this abstract pathname
	 *
	 * @param file    File
	 * @param classes story files get by scanning
	 * @param filters filter files
	 */
	void scanFiles(File file, Set<T> classes, Predicate<T>... filters) throws IOException {
		if (finished) return;
		// is directory
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (this.recursive) {
				for (File innerFile : files) {
					scanFiles(innerFile, classes, filters);
				}
			}else{
				if (files!=null) {
					for (File childFile : files) {
						validateFile(childFile, classes, filters);
					}
				}
			}
		}else {
			validateFile(file, classes, filters);
		}
	}

	abstract void validateFile(File file, Set<T> classes, Predicate<T>[] filters) throws IOException ;

	enum ScanType{
		ONE_FILE,MULTI_FILE
	}

	void resetType(){
		this.type=null;
	}
}
