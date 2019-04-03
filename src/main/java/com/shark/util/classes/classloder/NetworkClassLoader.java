package com.shark.util.classes.classloder;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkClassLoader extends AbstractSharkClassLoader {

	public NetworkClassLoader(String rootUrl) {
		this.parentUrl = rootUrl;
	}

	URL searchUrl(String className) throws MalformedURLException {
		if (this.parentUrl.endsWith("/")){
			return new URL(this.parentUrl + className.replace('.', '/') + ".class");
		}else {
			return new URL(this.parentUrl + "/" + className.replace('.', '/') + ".class");
		}
	}
}
