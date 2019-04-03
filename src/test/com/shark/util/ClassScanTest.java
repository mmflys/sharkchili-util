package com.shark.util;

import com.google.common.collect.Multimap;
import com.shark.util.util.scan.ClassScanner;
import com.shark.util.util.scan.FileScanner;
import com.shark.util.util.scan.Scanner;
import com.shark.util.util.scan.UrlScanner;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/11/27 0027
 */
public class ClassScanTest {

	@Test
	public static void testScanFiles() {
		String url="E:/Workspace/Git/com/fishbyby/sharkchili/sharkchili-feifei/target/classes/com/shark/feifei";
		Set<File> files= new FileScanner(url,true).scan(f->f.getName().endsWith(".class"));
		files.forEach(System.out::println);
	}

	@Test
	public static void testSearchFiles() {
		String url="E:/Workspace/Git/java/sharkchili/sharkchili-feifei";
		File file= new FileScanner(url).search("db/PoolConnection.class");
		System.out.println(file);
	}

	@Test
	public void testScanUrls(){
		String url="E:/Sys/apache-maven-3.5.4/repository/com/google/guava/guava/26.0-jre";
		Set<URL> urls=new UrlScanner(url).scan(u->u.getFile().endsWith(".class"));
		urls.forEach(System.out::println);
	}

	@Test
	public void testSearchUrl(){
		String url="E:/Sys/apache-maven-3.5.4/repository/com/google/guava/guava/26.0-jre";
		URL u=new UrlScanner(url).search("AbstractBiMap.class");
		System.out.println(u);
	}

	@Test
	public void testClassScan(){
		String parentPath="E:/Workspace/Git/com/fishbyby/sharkchili/sharkchili-util/target/classes";
		Set<Class> scanClass=new ClassScanner(parentPath).scan(Scanner.class::isAssignableFrom);
		System.out.println(scanClass);
	}

	@Test
	public void testClassSearch(){
		String parentPath="E:/Workspace/Git/com/fishbyby/sharkchili/sharkchili-util/target/classes";
		Class urlScanner=new ClassScanner(parentPath).search("com.shark.util.util.scan.UrlScanner");
		System.out.println(urlScanner);
	}

	@Test
	public void testClassSearchFromJar(){
		Set<Class> myClass=ClassScanner.scanFromClasses(s->true, c->c.getName().equals(Multimap.class.getName()));
		System.out.println(myClass);
	}
}
