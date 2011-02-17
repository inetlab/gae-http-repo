package com.onpositive.repo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;


public class Repository {

	private final URL base;
	private final RepoType type;
	private final Auth auth;
	
	
	//enum WebBackend {
	//	Plain, ViewVC
	//}
	
	public Repository(URL base, RepoType type, Auth auth) {
		this.base = base;
		this.type = type;
		this.auth = auth;
	}
	
	public Repository(URL url) {
		this(url, RepoType.Plain, null);
	}

  public String getText(BufferedReader reader) throws IOException {
    StringBuilder answer = new StringBuilder();
    char buf[] = new char[8192];
    int len;
    while ((len = reader.read(buf)) != -1) {
    	answer.append(buf, 0, len);
    }
    return answer.toString();
  }

  public String getText(URL url) throws IOException {
  	BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
  	String text = getText(reader);
  	reader.close();
  	return text;
  }
  
  URL buildUrl(String path) throws MalformedURLException {
  	String baseurl = base.toString();
  	URL url = new URL(baseurl + type.getPrefix() + path + type.getSuffix());
		return url;
  }
  
	public String get(String path) throws IOException {
		URL url = buildUrl(path);
		String text = GaeHttpRetriever.retrieve(url, auth);
		return text;
	}
	
}
