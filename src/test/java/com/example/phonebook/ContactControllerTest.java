package com.example.phonebook;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.phonebook.controller.ContactController;
import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.repository.AddressRepository;
import com.example.phonebook.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(properties = { "spring.jpa.hibernate.ddl-auto=create", "spring.liquibase.enabled=false",
"spring.flyway.enabled=false" })
@AutoConfigureMockMvc
public class ContactControllerTest {
	
	@Mock
	private ContactRepository contactRepo;
	
	@Mock
	AddressRepository addressRepository;
	
	@Mock
	ContactController contactController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	@Test
	void verifyInputValidation() throws Exception {

		Address address = new Address();
		address.setId(202);
		address.setCity("Skopje");
		address.setStreet("Partizanska");
		
		Contact contact = new Contact();
		contact.setId(202);
		contact.setName("TestName");
		contact.setSurname("TestSurname");
		contact.setPhoneNumber(null);
		contact.setAddress(address);

		mockMvc.perform(post("/contacts/update", contact).contentType("application/json")
				.content(objectMapper.writeValueAsString(contact.getPhoneNumber()))).andExpect(status().isBadRequest());

	}

}
