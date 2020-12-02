package com.example.phonebook;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.repository.ContactRepository;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.flyway.enabled=false"
})
public class ContactRepositoryTest {
	
	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	void returnsPage() {
		assertThat(contactRepo
						.findAll(PageRequest.of(0, 4))
						.getContent()
						.size())
				.isEqualTo(4);
	}
	
	@Test
	void whenSaved_thenFindsByPhoneNumberOrSurname() {
		Address address = new Address();
		address.setId(1);
		address.setCity("Skopje");
		address.setStreet("Partizanska");
		
		Contact contact = new Contact();
		contact.setId(1);
		contact.setName("Risto");
		contact.setSurname("Kochevski");
		contact.setPhoneNumber("+389070987456");
		contact.setAddress(address);
		contactRepo.save(contact);
		
		assertThat(contactRepo.findByPhoneNumberOrSurname("+389070987456", "Kochevski")).isNotNull();
	}
	
	@Test
	void whenSaved_thenFindsByPhoneNumber() {
		Address address = new Address();
		address.setId(2);
		address.setCity("Skopje");
		address.setStreet("Partizanska");
		
		Contact contact = new Contact();
		contact.setId(2);
		contact.setName("Risto");
		contact.setSurname("Kochevski");
		contact.setPhoneNumber("+389070123456");
		contact.setAddress(address);
		contactRepo.save(contact);
		
		assertThat(contactRepo.findByPhoneNumber("+389")).isNotNull();
	}
	
	@Test
	void injectedComponentsAreNotNull() {
		assertThat(dataSource).isNotNull();
		assertThat(jdbcTemplate).isNotNull();
		assertThat(entityManager).isNotNull();
		assertThat(contactRepo).isNotNull();
	}

}
