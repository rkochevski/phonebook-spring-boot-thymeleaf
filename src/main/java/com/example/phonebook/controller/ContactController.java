package com.example.phonebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.SearchCriteria;
import com.example.phonebook.service.ContactService;

@Controller
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/")
	public String viewWelcomePage(Model model) {
		return contactService.displayWelcomePage(model);
	}
	
	@GetMapping("/contacts/new")
	public String viewCreateContactPage(Model model) {
		return contactService.displayCreateContactPage(model);
	}
	
	@PostMapping("/contacts/create")
	public String createNewContact(@ModelAttribute("contact") Contact contact) {
		return contactService.createNewContact(contact);
	}
	
	@GetMapping("/contacts/{id}")
	public String viewContactDetailsPage(@PathVariable("id") Integer id, Model model) {
		return contactService.displayContactDetailsPage(id, model);
	}
	
	@GetMapping("/contacts/findByNumberOrSurname")
	public String findByPhoneNumberOrSurname(Model model, SearchCriteria criteria) {
		return contactService.findByNumberOrSurname(model, criteria);
	}
	
	@GetMapping("/contacts/list")
	public String viewContactsListBySurnamePage(Model model) {
		return findPaginated(1, "surname", "asc", model);
	}
	
	@GetMapping("/contacts/filter")
	public String viewFilterByNumberPage(Model model) {
		return contactService.displayFilterByNumberPage(model);
	}
	
	@GetMapping("/contacts/filterByNumber")
	public String findByPhoneNumber(Model model, Contact contact) {
		return findPaginatedNumbers(1, "surname", "asc", model, contact);
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		return contactService.findPaginatedContacts(pageNo, pageSize, sortField, sortDir, model);
	}
	
	@GetMapping("/filter/page/{pageNo}")
	public String findPaginatedNumbers(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model, Contact contact) {
		int pageSize = 5;
		String phoneNumber = contact.getPhoneNumber();
		return contactService.findPaginatedContactNumbers(pageNo, pageSize, sortField, sortDir, model, phoneNumber);
	}
	
}
