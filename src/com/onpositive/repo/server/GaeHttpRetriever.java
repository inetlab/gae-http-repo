package com.onpositive.repo.server;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class GaeHttpRetriever extends HttpRetriever {

	String findHeader(List<HTTPHeader> headers, String header) {
		for(HTTPHeader h: headers) {
			if(h.getName().equals(header)) {
				return h.getValue();
			}
		}
		return null;
	}
	
	@Override
	String retrieve(URL url, Headers headers, String header) throws IOException {
    HTTPMethod method = HTTPMethod.GET;
    FetchOptions fetchOptions = FetchOptions.Builder.disallowTruncate().doNotValidateCertificate();
    HTTPRequest request = new HTTPRequest(url, method, fetchOptions);

		System.out.println("URL: " + url);
		if(headers != null) {
			for(String key: headers.keySet()) {
				String value = headers.get(key);
				HTTPHeader h= new HTTPHeader(key, value);
				request.setHeader(h);
				//connection.setRequestProperty(key, value);
				System.out.println("  " + key + ": " + value);
			}
		}
		
    HTTPResponse response =	URLFetchServiceFactory.getURLFetchService().fetch(request);
    if(response.getResponseCode() != 200) {
    	System.err.println("GET error code: " + response.getResponseCode() + " URL: "+ url);
    	return null;
    }
    byte[] content = response.getContent();
		String text = new String(content);
		
		List<HTTPHeader> responseHeaders = response.getHeaders();
		int code = response.getResponseCode();
		System.out.println("Result: " + code);
		if(code != 200 && code != 302) return null;
		if(header != null) {
			text = findHeader(responseHeaders, header);
		}
		return text;
	}
 	
	public static void main(String[] args) throws Exception {
		//URL url = new URL("https://github.com/aav/csfr.components/raw/master/readme.txt");
		//Auth auth = new Auth("inetlab", "shine65");
		URL url = new URL("http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk/browser/com.pluginedge.helpdesk/src/com/pluginedge/server/SimpleFilter.java?format=txt");
		URL login = new URL("http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk/login");
		Auth auth = new Auth("pavel", "eclipse");

		HttpRetriever retriever = new GaeHttpRetriever();
		String text = retriever.retrieve(url, auth, login);
		System.out.println("Text: \n" + text);
	}

}
