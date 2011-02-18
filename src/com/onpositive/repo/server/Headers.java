package com.onpositive.repo.server;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Headers extends HashMap<String, String> {

	public Headers() {
	}
	
	public Headers(String key, String value) {
		put(key, value);
	}
	
}
