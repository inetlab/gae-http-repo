package com.onpositive.repo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

public abstract class HttpRetriever {
	/**
	 * Returns body or specific header
	 * @param url
	 * @param headers
	 * @param header
	 * @return
	 * @throws IOException
	 */
	abstract String retrieve(URL url, Headers headers, String header) throws IOException;
	
	public String retrieveAuthToken(URL login, Auth auth) throws IOException {
		if(auth == null) throw new IllegalArgumentException("No auth provided for auth token retrivial");
		Headers headers = new Headers("Authorization", "Basic " + auth.getBase64());
		String header = retrieve(login, headers, "Set-Cookie");
		String[] cookies = header.split(";");
		for(String s: cookies) {
			System.out.println("Cookie: " + s);
			if(s.startsWith("trac_auth=")) {
				s = s.substring("trac_auth=".length());
				System.out.println("Auth: " + s);
				return s;
			}
		}
		return null;
	}
	
	public String retrieve(URL url, Auth auth, URL login) throws IOException {
		Headers headers = new Headers();
		if(auth != null) {
			if(login != null) {
				String token = retrieveAuthToken(login, auth);
				headers.put("Cookie", "trac_auth=" + token);
			} else {
				headers.put("Authorization", "Basic " + auth.getBase64());
			}
		}
		String text = retrieve(url, headers, null);
		return text;
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

}
