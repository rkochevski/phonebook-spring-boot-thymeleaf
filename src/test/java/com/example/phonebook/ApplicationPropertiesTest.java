package com.example.phonebook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(properties = "spring.datasource.url = jdbc:postgresql://localhost:5432/phonebook")
@TestPropertySource(locations = "/application.properties")
public class ApplicationPropertiesTest {
	
	@Value("${spring.datasource.url}")
	String url;
	
	@Test
	void verifyParameter() {
		assertThat(url).isEqualTo("jdbc:postgresql://localhost:5432/phonebook");
	}

}
