package com.onpositive.repo.server;

public class Response {
	
	int code;
	Headers headers;
	String text;
	
	public Response(int code, Headers headers, String text) {
		super();
		this.code = code;
		this.headers = headers;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public Headers getHeaders() {
		return headers;
	}

	public String getText() {
		return text;
	}
			
}
