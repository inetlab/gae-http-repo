package com.onpositive.repo.server;

public class RepoType {

	public static final RepoType Plain = new RepoType();
	public static final RepoType GitHub = new RepoType("/raw/master");
	public static final RepoType Assembla = new RepoType("/browser", "?format=txt");
	
	String prefix;
	String suffix;
		
	private RepoType() {
	}
	
	private RepoType(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
		
	private RepoType(String prefix) {
		this(prefix, null);
	}
	
	public String getPrefix() {
		return prefix == null? "" : prefix;
	}
	
	public String getSuffix() {
		return suffix == null? "" : suffix;
	}
	
}
