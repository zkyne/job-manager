package com.zkyne.jobmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zkyne
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.zkyne.jobmanager.mapper")
public class JobManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobManagerApplication.class, args);
	}
}
