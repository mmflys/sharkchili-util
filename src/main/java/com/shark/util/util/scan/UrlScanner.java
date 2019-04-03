package com.shark.util.util.scan;

import com.google.common.collect.Sets;
import com.shark.util.Exception.FileException;
import com.shark.util.util.PredicateUtil;
import com.shark.util.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Scan url.
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/11/29 0029
 */
public class UrlScanner extends AbstractScanner<URL> {

	public UrlScanner(String parentPath) {
		super(parentPath);
	}

	public UrlScanner(String parentPath, boolean recursive) {
		super(parentPath, recursive);
	}

	@Override
	public URL search(String targetName) {
		this.type= ScanType.ONE_FILE;
		Set<URL> files = Sets.newHashSet();
		targetName=StringUtil.setSeparator(targetName, '/');
		String finalTargetName = targetName;
		scanFiles(parentPath, files, f->f.getPath().endsWith(finalTargetName));
		Optional<URL> result=files.stream().findFirst();
		if (!result.isPresent()){
			throw new FileException("not find this file %s",targetName);
		}
		resetType();
		return result.get();
	}

	@Override
	void validateFile(File file, Set<URL> classes, Predicate<URL>[] filters) throws IOException {
		if (file.getName().endsWith(".jar")) {
			boolean isSearch=this.type==ScanType.ONE_FILE;
			classes.addAll(parseJar(file,isSearch,filters));
			// whether finish or not
			if (classes.isEmpty()&&isSearch){
				this.finished=true;
			}
		}else {
			for (Predicate<URL> filter : filters) {
				if (!filter.test(file.toURI().toURL())) return;
			}
			classes.add(file.toURI().toURL());
			// whether end recursive or not
			if (type == ScanType.ONE_FILE) {
				this.finished = true;
			}
		}
	}

	/**
	 * Parse jar file,get url from the jar file
	 * @param file jra file
	 * @param filters filter
	 * @return a set of url from the jar file
	 * @throws IOException if url is error
	 */
	public static Set<URL> parseJar(File file,boolean isSearch, Predicate<URL>...filters) throws IOException {
		JarFile jarFile = new JarFile(file);
		Set<URL> urls = Sets.newHashSet();
		// 从此jar包 得到一个枚举类
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			String packageName = null;
			// 如果是以/开头的
			if (name.charAt(0) == '/') {
				// 获取后面的字符串
				name = name.substring(1);
			}
			int idx = name.lastIndexOf('/');
			// 如果以"/"结尾 是一个包
			if (idx != -1) {
				// 获取包名 把"/"替换成"."
				packageName = name.substring(0, idx).replace('/', '.');
			}
			String classPath = "jar:file:/" + file.getCanonicalPath().replace("\\", "/") + "!/" + name;
			URL url = new URL(classPath);
			boolean skip=false;
			if (!PredicateUtil.test(url, filters)){
				continue;
			}
			if (skip) continue;
			urls.add(url);
			if (isSearch){
				break;
			}
		}
		return urls;
	}

	/**
	 * Aet q instance of UrlScanner
	 * @param parentUrl url
	 * @return a instance of UrlScanner
	 */
	public static Scanner<URL> create(String parentUrl){
		return new UrlScanner(parentUrl);
	}
}
