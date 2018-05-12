package com.sample.project;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.sample.pack.LogginInUser;

@Repository
public interface UserRepository extends MongoRepository<LogginInUser, String>{
	public void deleteByUsername(String username);
	public LogginInUser findByUsername(String username);
	public List<LogginInUser> findByUsernameNotIn(String username);
}
