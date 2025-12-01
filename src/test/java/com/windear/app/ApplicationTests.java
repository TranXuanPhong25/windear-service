package com.windear.app;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Integration test - requires full application context and database connections")
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
