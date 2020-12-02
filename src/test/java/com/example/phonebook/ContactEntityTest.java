package com.example.phonebook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.service.ContactService;

@ExtendWith(MockitoExtension.class)
public class ContactEntityTest {
	
	@Mock
	private ContactRepository contactRepo;
	
	@InjectMocks
	private ContactService contactService;
	
	@Test
	void addressIsCreatedWhenSavingContact() {
		
		Address address = new Address();
		address.setCity("Skopje");
		address.setStreet("Partizanska");
		
		Contact contact = new Contact();
		contact.setId(101);
		contact.setName("TestName");
		contact.setSurname("TestSurname");
		contact.setPhoneNumber("+389070123456");
		contact.setAddress(address);
		
		contactService.createNewContact(contact);
		assertThat(contact.getAddress()).isNotNull();
	}

}
