package com.example.demo;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

// reference:
// install lombok: https://polinwei.com/lombok-install-in-eclipse/
// what is autowired: 

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private final JdbcTemplate jdbcTemplate;
	
	public List<User> select_all() {
	    return jdbcTemplate.query(
	       "SELECT * FROM user",
	        BeanPropertyRowMapper.newInstance(User.class));
	}
	
	public User select_one(String key) {
	    List<User> result = jdbcTemplate.query(
	       "SELECT * FROM user where user='"+key+"'",
	        BeanPropertyRowMapper.newInstance(User.class));
	  
	    return result.size()>0?result.get(0):null;
	}
}
