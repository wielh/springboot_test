package com.example.demo;

import java.util.List;

public interface UserDao {
	
	public List<User> select_all();
	
	public User select_one(String key);
}
