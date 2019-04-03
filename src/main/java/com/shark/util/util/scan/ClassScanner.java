package com.shark.util.util.scan;

import com.google.common.collect.Sets;
import com.shark.util.Exception.FileException;
import com.shark.util.util.PredicateUtil;
import com.shark.util.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/6 0006
 */
public class ClassScanner extends AbstractScanner<Class> {

	private String classPath;

	public ClassScanner(String parentPath) {
		this(parentPath, true, parentPath);
	}

	public ClassScanner(String parentPath, boolean recursive) {
		this(parentPath, recursive, parentPath);
	}

	public ClassScanner(String parentPath, boolean recursive, String classPath) {
		super(parentPath, recursive);
		this.classPath = classPath;
	}

	@Override
	public Class search(String name) {
		this.type = ScanType.ONE_FILE;
		Set<Class> classes = Sets.newHashSet();
		scanFiles(parentPath, classes, c -> c.getName().equals(name));
		Optional<Class> result = classes.stream().findFirst();
		if (!result.isPresent()) {
			throw new FileException("not find this file %s", name);
		}
		resetType();
		return result.get();
	}

	@Override
	void validateFile(File file, Set<Class> classes, Predicate<Class>[] filters) throws IOException {
		if (file.getName().endsWith(".jar")) {
			boolean isSearch = this.type == ScanType.ONE_FILE;
			Set<URL> jarFileUrls = UrlScanner.parseJar(file, isSearch);
			for (URL url : jarFileUrls) {
				if (url.getPath().endsWith(".class")) {
					String myPath = url.getPath().substring(0, url.getPath().lastIndexOf('.'));
					String myPackageName = myPath.substring(myPath.indexOf("!/") + 2).replace('/', '.');
					loadClass(classes, filters, myPackageName);
				}
			}
			// whether finish or not
			if (classes.isEmpty() && isSearch) {
				this.finished = true;
			}
		} else {
			if (file.getCanonicalPath().endsWith(".class")) {
				String myPathExcludePostName = file.getCanonicalPath().substring(0, file.getCanonicalPath().lastIndexOf('.'));
				String myPackageName = myPathExcludePostName.substring(myPathExcludePostName.indexOf(classPath) + classPath.length() + 1).replace(File.separatorChar, '.');
				if (myPackageName.startsWith(".")) {
					myPackageName = myPackageName.substring(1);
				}
				loadClass(classes, filters, myPackageName);
			}
		}

	}

	private void loadClass(Set<Class> classes, Predicate<Class>[] filters, String myPackageName) {
		try {
			Class myClass = Thread.currentThread().getContextClassLoader().loadClass(myPackageName);
			if (PredicateUtil.test(myClass, filters)) {
				classes.add(myClass);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scan class from class path
	 *
	 * @param classPathFilter filter class path
	 * @param filters         filter class
	 * @return a set of class
	 */
	public static Set<Class> scanFromClasses(Predicate<String> classPathFilter, Predicate<Class>... filters) {
		String[] classPath = StringUtil.getClassPath();
		Set<Class> result = Sets.newHashSet();
		for (String path : classPath) {
			if (classPathFilter.test(path)) {
				System.out.println(path);
				Scanner<Class> scanner = new ClassScanner(path);
				result.addAll(scanner.scan(filters));
			}
		}
		return result;
	}

	/**
	 * Scan class from class path
	 * @param name package name
	 * @param filters filters
	 * @return a set of class
	 */
	public static Set<Class> scanFromClassPath(String name, Predicate<Class>... filters) {
		String[] classPath = StringUtil.getClassPath();
		Set<Class> result = Sets.newHashSet();
		for (String path : classPath) {
			path = StringUtil.setSeparator(path, '/');
			boolean isJar = path.endsWith(".jar");
			if (isJar) {
				scanClassFromJarByPackageName(name, result, path);
			} else {
				// whether is a directory or not
				String packagePath = path + "/" + name.replace('.', '/');
				try {
					String urlStr = "file:" + packagePath;
					URL url = new URL(urlStr);
					url.openStream();
					result.addAll(new ClassScanner(packagePath, true, path).scan(filters));
				} catch (IOException e) {
					// whether is a class or not
					packagePath += ".class";
					try {
						URL url = new URL("file:" + packagePath);
						url.openStream();
						Class myClass = Thread.currentThread().getContextClassLoader().loadClass(
								packagePath.substring(0, packagePath.lastIndexOf('.'))
										.replace('/', '.'));
						if (PredicateUtil.test(myClass, filters)) {
							return Sets.newHashSet(myClass);
						} else {
							return null;
						}
					} catch (IOException e1) {
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	private static void scanClassFromJarByPackageName(String name, Set<Class> result, String classPath) {
		try {
			String myUrl="jar:file:"+classPath+"!/"+name.replace('.', '/');
			new URL(myUrl).openStream();
			Set<URL> urls = new UrlScanner(classPath).scan(u ->{
				String jarClassPath=u.getPath().substring(u.getPath().indexOf(classPath)+ classPath.length()+2);
				return jarClassPath.startsWith(name.replace('.', '/')) && u.getPath().endsWith(".class");
			});
			for (URL url : urls) {
				String jarClassPackageName = url.getPath().substring(url.getPath().indexOf(classPath)+ classPath.length()+2,url.getPath().lastIndexOf('.'))
						.replace('/', '.');
				try {
					Class myclass = Thread.currentThread().getContextClassLoader().loadClass(jarClassPackageName);
					result.add(myclass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
		}
	}
}
