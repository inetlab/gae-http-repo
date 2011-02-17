package com.onpositive.repo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.repackaged.com.google.common.util.Base64;

public class GaeHttpRetriever {

  public static String getText(BufferedReader reader) throws IOException {
    StringBuilder answer = new StringBuilder();
    char buf[] = new char[8192];
    int len;
    while ((len = reader.read(buf)) != -1) {
    	answer.append(buf, 0, len);
    }
    return answer.toString();
  }

	public static String retrieve(URL url, Auth auth) throws IOException {
    HTTPMethod method = HTTPMethod.GET;
    FetchOptions fetchOptions = FetchOptions.Builder.disallowTruncate().doNotValidateCertificate();

    HTTPRequest request = new HTTPRequest(url, method, fetchOptions);
		if(auth != null) {
			String userAndPassword = "" + auth.getUsername() + ":" + auth.getPassword();
			String base64 = Base64.encode(userAndPassword.getBytes());
			// workaround base64 encoder bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4615330
			if(base64.endsWith("\n")) base64 = base64.substring(0, base64.length()-2);
			HTTPHeader header = new HTTPHeader("Authorization", "Basic " + base64);
			request.setHeader(header);
		}
    HTTPResponse response =	URLFetchServiceFactory.getURLFetchService().fetch(request);
    if(response.getResponseCode() != 200) {
    	System.err.println("GET error code: " + response.getResponseCode() + " URL: "+ url);
    	return null;
    }
    byte[] content = response.getContent();
		return new String(content);
	}

	public static void main(String[] args) throws Exception {
		URL url = new URL("https://github.com/aav/csfr.components/raw/master/readme.txt");
		Auth auth = new Auth("inetlab", "shine65");
		String text = retrieve(url, auth);
		System.out.println("Text: \n" + text);
	}


}
