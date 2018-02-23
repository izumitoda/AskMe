package com.erika.askme;

import com.erika.askme.dao.userdao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages={"com.erika.askme.*"})
@MapperScan("com.erika.askme.dao")

public class AskmeApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AskmeApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AskmeApplication.class, args);
	}
}
