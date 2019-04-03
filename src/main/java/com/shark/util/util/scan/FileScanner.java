package com.shark.util.util.scan;

import com.google.common.collect.Sets;
import com.shark.util.Exception.FileException;
import com.shark.util.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/11/29 0029
 */
public class FileScanner extends AbstractScanner<File> {

	public FileScanner(String parentPath) {
		super(parentPath);
	}

	public FileScanner(String parentPath, boolean recursive) {
		super(parentPath, recursive);
	}

	/**
	 * Search file in within parent path.
	 * @param targetName file name or direct/file name (home/jar/love.js)
	 * @return one file
	 */
	@Override
	public File search(String targetName) {
		this.type= ScanType.ONE_FILE;
		Set<File> files = Sets.newHashSet();
		targetName=StringUtil.setLocalSeparator(targetName);
		String reg=File.separatorChar=='\\'?"\\\\":File.separatorChar+"";
		String[] dirs=targetName.split(reg);

		final String finalTargetName=targetName;
		Predicate<File> equal= file -> {
			// 若行如 home/java/test.jar
			if (dirs.length>1){
				return file.getPath().endsWith(finalTargetName);
			}else {
				return file.getName().equals(finalTargetName);
			}
		};
		scanFiles(parentPath, files,equal);
		Optional<File> result=files.stream().findFirst();
		if (!result.isPresent()){
			throw new FileException("not find this file %s",targetName);
		}
		resetType();
		return result.get();
	}

	@Override
	void validateFile(File file, Set<File> classes, Predicate<File>[] filters)  throws IOException {
		for (Predicate<File> filter : filters) {
			if (!filter.test(file)) return;
		}
		classes.add(file);
		// whether end recursive or not
		if (type== ScanType.ONE_FILE){
			this.finished=true;
		}
	}

	/**
	 * Aet q instance of FileScanner
	 * @param parentUrl url
	 * @return a instance of FileScanner
	 */
	public static Scanner<File> create(String parentUrl){
		return new FileScanner(parentUrl);
	}
}
