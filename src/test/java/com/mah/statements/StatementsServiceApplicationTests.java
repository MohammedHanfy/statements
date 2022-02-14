package com.mah.statements;

import com.mah.statements.rest.StatementsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatementsServiceApplicationTests {

	@Mock
	private StatementsController statementsController;

	@Test
	void contextLoads() {

		Assertions.assertNotNull(statementsController);
	}
}
