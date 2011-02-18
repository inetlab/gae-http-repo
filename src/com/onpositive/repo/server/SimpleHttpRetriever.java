package com.onpositive.repo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SimpleHttpRetriever extends HttpRetriever {

  public String retrieve(URL url, Headers headers, String header) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		System.out.println("URL: " + url);
		if(headers != null) {
			for(String key: headers.keySet()) {
				String value = headers.get(key);
				connection.setRequestProperty(key, value);
				System.out.println("  " + key + ": " + value);
			}
		}
		InputStream stream = connection.getInputStream();
		String text = getText(new BufferedReader(new InputStreamReader(stream)));
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		System.out.println("Result: " + connection.getResponseMessage());
		int code = connection.getResponseCode();
		if(code != 200 && code != 302) return null;
		if(header != null) {
			List<String> list = headerFields.get(header);
			System.out.println(list);
			if(list == null) return null;
			if(list.size() > 1) throw new IOException("Multiple headers for " + header + " returnned");
			text = list.get(0);
		}
		stream.close();
		return text;
	}
  
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk/browser/com.pluginedge.helpdesk/src/com/pluginedge/server/SimpleFilter.java?format=txt");
		URL login = new URL("http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk/login");
		Auth auth = new Auth("pavel", "eclipse");
		SimpleHttpRetriever retriever = new SimpleHttpRetriever();
		String text = retriever.retrieve(url, auth, login);
		System.out.println("Text: \n" + text);
	}
	
}
