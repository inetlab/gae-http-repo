package com.onpositive.repo.server;

import java.io.IOException;
import java.net.URL;


public class RepoTest {

	static Auth shu 	= new Auth("inetlab", "shine65");
	static Auth pavel = new Auth("pavel", "eclipse");

	
	static RepoFile tests[] = {
		//new RepoFile("http://svn.php.net/repository", "/php/php-src/branches/PHP_4_3_0/configure.in"),
		//new RepoFile("http://code.djangoproject.com/svn", "/django/trunk/setup.py"),
		//new RepoFile("http://redmine.rubyforge.org/svn/branches/1.1-stable", "/config/settings.yml"),
		//new RepoFile("http://ivtvdriver.org/svn/ivtv/trunk", "/test/vbi.c"),
		//new RepoFile("http://svn.enlightenment.org/svn/e", "/trunk/eio/src/Makefile.am"), // Trac
		//new RepoFile("https://github.com/aav/csfr.components", "/readme.txt", RepoType.GitHub, shu, null), // GitHub /raw/master
		//new RepoFile("https://github.com/andreyvit/groovy", "/groovy/build.xml", RepoType.GitHub, null, null), // GitHub /raw/master
		//new RepoFile("http://trac-git.assembla.com/my-git-example", "/README", RepoType.Assembla, null, null), // Assembla GIT
		//new RepoFile("http://lirc.cvs.sourceforge.net/viewvc/lirc", "/lirc/autogen.sh"), // CVS, SourceForge
		new RepoFile("http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk", "/com.pluginedge.helpdesk/src/com/pluginedge/server/SimpleFilter.java", RepoType.Trac, pavel, "http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk/login"), // Trac, Git

		//URL url = new URL("http://pluginedge.repositoryhosting.com/trac/pluginedge_ghelpdesk/browser/com.pluginedge.helpdesk/src/com/pluginedge/server/SimpleFilter.java?format=txt");

	};
		
	public static void main(String[] args) throws IOException {
		for(RepoFile test: tests) {
			URL url = new URL(test.base);
			URL login = test.loginURL == null? null : new URL(test.loginURL); 
			Repository repo = new Repository(url, test.type, test.auth, login);
			String text = repo.get(test.path);
			System.out.println("URL: " + repo.buildUrl(test.path));
			int maxlen = Math.min(text.length(), 100);
			System.out.println("Text: \n" + text.substring(0,maxlen) + "...");
			System.out.println();
		}
	}
	
}
