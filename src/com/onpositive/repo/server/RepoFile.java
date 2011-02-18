/**
 * 
 */
package com.onpositive.repo.server;


public class RepoFile {
	String base;
	String path;
	Auth auth;
	
	RepoType type;
	String loginURL;
	
	public RepoFile(String base, String path, RepoType type, Auth auth, String loginURL) {
		super();
		this.base = base;
		this.path = path;
		this.type = type;
		this.auth = auth;
		this.loginURL = loginURL;
	}
	
	public RepoFile(String base, String path) {
		this(base, path, RepoType.Plain, null, null);
	}
}