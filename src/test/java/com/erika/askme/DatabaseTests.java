package com.erika.askme;

import com.erika.askme.dao.questiondao;
import com.erika.askme.model.Question;
import com.erika.askme.dao.questiondao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTests {
	@Autowired
	private questiondao questiondata;
	@Test
	public void databaseinsert() {
		List<Question> tmp= questiondata.selectLatestQuestions(11,0,5);
		System.out.println("finished");
		System.out.println(tmp.size());
		for(Question a:tmp)
			System.out.println(a.getTitle());
	}
}
