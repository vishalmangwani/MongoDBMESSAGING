package com.sample.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sample.pack.LogginInUser;

@Component
public class DBSeeder implements CommandLineRunner{
	private UserRepository userrepo;
	
	public DBSeeder(UserRepository userrepo) {
		this.userrepo = userrepo;
	}

	@Override
	public void run(String... args) throws Exception {
		LogginInUser us=new LogginInUser();
		us.setUsername("Vishal");
		us.setPassword("password");
		this.userrepo.save(us);
		this.userrepo.deleteByUsername("Vishal");
	}

}
