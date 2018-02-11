package com.erika.askme;

import com.erika.askme.dao.userdao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.erika.askme.*"})
@MapperScan("com.erika.askme.dao")

public class AskmeApplication {
	@Autowired
	private userdao userdata;
	public static void main(String[] args) {
		SpringApplication.run(AskmeApplication.class, args);
	}
}
