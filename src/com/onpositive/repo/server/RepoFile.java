/**
 * 
 */
package com.onpositive.repo.server;


public class RepoFile {
	String base;
	String path;
	Auth auth;
	
	RepoType type;
	
	public RepoFile(String base, String path, RepoType type, Auth auth) {
		super();
		this.base = base;
		this.path = path;
		this.type = type;
		this.auth = auth;
	}
	public RepoFile(String base, String path) {
		this(base, path, RepoType.Plain, null);
	}
}