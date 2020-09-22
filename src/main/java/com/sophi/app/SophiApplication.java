package com.sophi.app;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SophiApplication{

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SophiApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		String[ ] pass = {"WxtsUQTE", "xcUKTupT","pvNYWwrQ","BsnARXqe","YVmQkhps","yxZjmEma","dEbPJfQm","fSyFPwSr","SqKgSZdh","BapRQqer","NMtcqeBa","qZfwWqcZ"};
//		for (String p : pass) {
//			System.out.println(p + " - " + passwordEncoder.encode(p));
//		}
//	}
	

}
