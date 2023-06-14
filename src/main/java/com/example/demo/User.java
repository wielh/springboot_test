package com.example.demo;

import lombok.Data;  
import org.springframework.stereotype.Component;

// reference: https://ithelp.ithome.com.tw/articles/10195592
@Component
@Data
public class User{
	private int index;
	private String username;
	private String hashed_password;
	private int balance;
	private int identity;
	private String google_ID; 
	private String email;
}
