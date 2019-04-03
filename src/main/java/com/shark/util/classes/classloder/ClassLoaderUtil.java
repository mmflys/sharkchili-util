package com.shark.util.classes.classloder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @Author: Shark Chili
 * @Date: 2018/9/7 0007
 */
public class ClassLoaderUtil {

	public static byte[] getByteDate(URL url){
		try {
			InputStream ins = url.openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			return getByteData(ins, out, buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] getByteData(InputStream ins, ByteArrayOutputStream out, byte[] buffer) throws IOException {
		int bytesNumRead;
		while ((bytesNumRead = ins.read(buffer)) != -1) {
			out.write(buffer, 0, bytesNumRead);
		}
		return out.toByteArray();
	}

}
