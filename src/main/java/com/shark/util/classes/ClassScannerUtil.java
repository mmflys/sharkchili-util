package com.shark.util.classes;

import com.google.common.collect.Sets;
import com.shark.util.util.PredicateUtil;
import com.shark.util.util.StringUtil;
import com.shark.util.util.scan.UrlScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Scan class from package.
 * @Author: SuLiang
 * @Date: 2018/9/7 0011
 */
public class ClassScannerUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(ClassScannerUtil.class);

	/**
	 * Scan class from class path
	 * @param classPathFilter filter class path
	 * @param filters filter class
	 * @return a set of class
	 */
	public static Set<Class> scanClassFromClassPath(Predicate<String> classPathFilter,Predicate<Class>...filters){
		LOGGER.debug("scan class from class path start");
		Set<Class> result= Sets.newHashSet();
		String classPaths = System.getProperty("java.class.path");
		String[] classPathArray=classPaths.split(";");
		for (String classPath : classPathArray) {
			if (!classPathFilter.test(classPath)){
				continue;
			}
			LOGGER.debug("scan class from class path: {}",classPath);
			classPath= StringUtil.setSeparator(classPath,'/');
			Set<URL> urls= UrlScanner.create(classPath).scan(u->u.getPath().endsWith(".class"));
			for (URL url : urls) {
				String classFilePath=url.getPath().substring(url.getPath().indexOf(classPath)+classPath.length());
				// normal class
				if (classFilePath.startsWith("/")){
					classFilePath=classFilePath.substring(1);
				}
				// jar class
				if (classFilePath.startsWith("!/")){
					classFilePath=classFilePath.substring(2);
				}
				classFilePath=classFilePath.substring(0,classFilePath.lastIndexOf('.'));
				String classPackageName=classFilePath.replace('/', '.');
				try {
					Class c=Thread.currentThread().getContextClassLoader().loadClass(classPackageName);
					if (PredicateUtil.test(c, filters)){
						result.add(c);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Scan class by package name.
	 *
	 * @param packageName package name
	 * @return the classes of the package
	 */
	public static Set<Class<?>> scanClass(String packageName) {
		LOGGER.debug("scan package : {}",packageName);
		Set<Class<?>> classes = new LinkedHashSet<>();
		String packageDirName = packageName.replace('.', '/');
		// 是否循环迭代
		boolean recursive = true;
		try {
			//获取该目录下的所有资源的url
			Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if (protocol.equals("file")) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findClassInPackage(packageName, filePath, recursive, classes);
				} else if (protocol.equals("jar")) {
					packageName = scanJarFile(packageName, classes, packageDirName, recursive, url);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * Scan jar file,and get package name.
	 * @param packageName package name(eg: com.shark.util)
	 * @param classes storage class
	 * @param packageDirName package directory name
	 * @param recursive whether recursive or not
	 * @param url url
	 * @return package name
	 */
	private static String scanJarFile(String packageName, Set<Class<?>> classes, String packageDirName, boolean recursive, URL url) {
		// 如果是jar包文件,定义一个JarFile
		JarFile jar;
		try {
			// 获取jar
			jar = ((JarURLConnection) url.openConnection()).getJarFile();
			// 从此jar包 得到一个枚举类
			Enumeration<JarEntry> entries = jar.entries();
			// 同样的进行循环迭代
			while (entries.hasMoreElements()) {
				// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				// 如果是以/开头的
				if (name.charAt(0) == '/') {
					// 获取后面的字符串
					name = name.substring(1);
				}
				// 如果前半部分和定义的包名相同
				if (name.startsWith(packageDirName)) {
					int idx = name.lastIndexOf('/');
					// 如果以"/"结尾 是一个包
					if (idx != -1) {
						// 获取包名 把"/"替换成"."
						packageName = name.substring(0, idx).replace('/', '.');
					}
					// 如果可以迭代下去 并且是一个包
					if ((idx != -1) || recursive) {
						// 如果是一个.class文件 而且不是目录
						if (name.endsWith(".class") && !entry.isDirectory()) {
							// 去掉后面的".class" 获取真正的类名
							String className = name.substring(packageName.length() + 1, name.length() - 6);
							try {
								// 添加到classes
								classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
							} catch (ClassNotFoundException e) {
								LOGGER.error("Not find class file: ", className);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// log.error("在扫描用户定义视图时从jar包获取文件出错");
			e.printStackTrace();
		}
		return packageName;
	}

	/**
	 * Find all class in the package.
	 *
	 * @param packageName package name
	 * @param packagePath package path
	 * @param recursive   whether or not recursive
	 * @param classes     class set
	 */
	private static void findClassInPackage(String packageName, String packagePath, boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findClassInPackage(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					LOGGER.error("Not find class file: ", className);
				}
			}
		}
	}

	/**
	 * Scan class from package for given predicate
	 * @param packageName package name
	 * @param predicate filter condition
	 * @return classes
	 */
	public static Set<Class<?>> scanClassWithPredicate(String packageName, Predicate<Class<?>> predicate){
		return scanClass(packageName).stream().filter(predicate).collect(Collectors.toSet());
	}

	/**
	 * Filter class
	 * @param aClass class
	 * @return class is interface or abstract class return false,else return true
	 */
	public static boolean filterClass(Class<?> aClass) {
		return !aClass.getSimpleName().equals("") && !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers());
	}
}
