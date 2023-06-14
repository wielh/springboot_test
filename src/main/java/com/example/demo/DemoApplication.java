package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class DemoApplication {
	@Autowired User user;
	@Autowired UserDao userDao;
	
	// note that cache and loading_cache is thread_safe
	static Cache<String, User> graphs = Caffeine.newBuilder()
			.maximumSize(100)
		    .expireAfterWrite(Duration.ofDays(1))
		    .build();
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
	
	@PostMapping("/hello")
    public String post_helloWorld() {
        return "Hello, World!";
    }
	
	@GetMapping("/user_test")
    public String user_test() {
		user.setEmail("wielh@gmail");
        return "Email:"+user.getEmail();
    }
	
	
	@GetMapping("/db_test")
    public String db_test() {
		List<User> result = userDao.select_all();
        return "Number of user:"+result.size();
    }

	@GetMapping("/memory_test")
    public String memory_test() {
		String key = "google_user:115469924550884140473";
        User value = getValueFromCache(key);
        if(value!=null) {
        	String result = String.format(
        		"Get data from cache,\n"+
        		"email=%s,\n,googleID=%s\n,idenetity=%s\n", 
        		value.getEmail(),value.getGoogle_ID(),value.getIdentity()
        	);
        	return result;
        }
        
        value = userDao.select_one(key);
        if (value != null) {
        	graphs.put(key, value);
        	String result = String.format(
        		"Get data from db,\n"+
        		"email=%s,\n,googleID=%s\n,idenetity=%s\n", 
        		value.getEmail(),value.getGoogle_ID(),value.getIdentity()
        	);
        	return result;
        }
        return "cannot find user via cache and db";
    }
	
	private User getValueFromCache(String key) {
        return graphs.getIfPresent(key);
    }
	
}
