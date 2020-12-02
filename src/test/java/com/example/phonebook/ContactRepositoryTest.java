package com.example.phonebook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.example.phonebook.repository.ContactRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ContactRepositoryTest {
	
	@Autowired
	private ContactRepository contactRepo;
	
	@Test
	void returnsPage() {
		assertThat(contactRepo
						.findAll(PageRequest.of(0, 4))
						.getContent()
						.size())
				.isEqualTo(4);
	}

}
