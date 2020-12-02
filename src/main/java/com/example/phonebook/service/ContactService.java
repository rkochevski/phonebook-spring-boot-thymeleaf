package com.example.phonebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.SearchCriteria;
import com.example.phonebook.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	ContactRepository contactRepo;

	public String displayWelcomePage(Model model) {
		SearchCriteria criteria = new SearchCriteria();
		model.addAttribute("criteria", criteria);
		return "index";
	}

	public String displayCreateContactPage(Model model) {
		Contact contact = new Contact();
		model.addAttribute("contact", contact);
		return "createContactForm";
	}

	public String createNewContact(Contact contact) {
		saveContact(contact);
		return "redirect:/contacts/" + contact.getId();
	}

	private void saveContact(Contact contact) {
		contactRepo.save(contact);
	}
	
	private void saveContactsAddress(Contact contact) {
		Address address = new Address();
		address.setStreet(contact.getAddress().getStreet());
		address.setCity(contact.getAddress().getCity());
		addressService.saveAddress(address);
	}

	public String displayContactDetailsPage(Integer id, Model model) {
		Contact contact = getContactById(id);
		model.addAttribute("contact", contact);
		return "contactDetails";
	}

	private Contact getContactById(Integer id) {
		return contactRepo.findById(id).get();
	}

	public String findByNumberOrSurname(Model model, SearchCriteria criteria) {
		if (criteria.getCriteria() == (String) null) {
			criteria.setCriteria("");
		}
		List<Contact> contacts = findByPhoneNumberOrSurname(criteria.getCriteria(), criteria.getCriteria());
		
		if (contacts.isEmpty()) {
			return "redirect:/";
		}
		else if (contacts.size() == 1) {
			Contact contact = contacts.iterator().next();
			return "redirect:/contacts/" + contact.getId();
		}
		else {
			model.addAttribute("contacts", contacts);
			return "searchResults";
		}
	}

	private List<Contact> findByPhoneNumberOrSurname(String phoneNubmer, String surname) {
		return contactRepo.findByPhoneNumberOrSurname(phoneNubmer, surname);
	}

	public String displayContactsListBySurnamePage(Model model) {
		List<Contact> contacts = contactRepo.findByOrderBySurnameAsc();
		model.addAttribute("contacts", contacts);
		return "contactsList";
	}

	public String displayFilterByNumberPage(Model model) {
		Contact contact = new Contact();
		model.addAttribute("contact", contact);
		return "filterByNumber";
	}

	private List<Contact> getAll() {
		return contactRepo.findAll();
	}

	public String findByPhoneNumber(Model model, Contact contact) {
		if (contact.getPhoneNumber() == null) {
			contact.setPhoneNumber("");
		}
		
		List<Contact> contacts = getByPhoneNumber(contact.getPhoneNumber());
		
		if (contacts.isEmpty()) {
			List<Contact> allContacts = getAllContacts();
			model.addAttribute("contacts", allContacts);
			return "contactsByNumberResults";
		}
		else {
			model.addAttribute("contacts", contacts);
			return "contactsByNumberResults";
		}
	}

	private List<Contact> getByPhoneNumber(String phoneNumber) {
		return contactRepo.findByPhoneNumber(phoneNumber);
	}

	private List<Contact> getAllContacts() {
		return contactRepo.findAll();
	}

	public String findPaginatedContacts(int pageNo, int pageSize, String sortField, String sortDir, Model model) {
		Page<Contact> page = findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Contact> contacts = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("contacts", contacts);
		return "contactsList";
	}

	private Page<Contact> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
	
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return contactRepo.findAll(pageable);
	}
	
	public String findPaginatedContactNumbers(int pageNo, int pageSize, String sortField, String sortDir, Model model, String phoneNumber) {
		Page<Contact> page = findPaginatedNumbers(pageNo, pageSize, sortField, sortDir, phoneNumber);
		List<Contact> contacts = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("contacts", contacts);
		return "contactsByNumberResults";
	}
	
	private Page<Contact> findPaginatedNumbers(int pageNo, int pageSize, String sortField, String sortDirection, String phoneNumber) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
	
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return contactRepo.findAllByPhoneNumber(phoneNumber, pageable);
	}

	public String displayUpdateContactPage(Integer id, Model model) {
		Contact contact = getContactById(id);
		model.addAttribute("contact", contact);
		return "updateContactForm";
	}

	public String updateContact(Contact contact) {
		saveContact(contact);
		return "redirect:/contacts/" + contact.getId();
	}

	public String displayDeleteContactConfirmationPage(Integer id, Model model) {
		Contact contact = getContactById(id);
		model.addAttribute("contact", contact);
		return "deleteContactConfirmation";
	}

	public String deleteContactById(Integer id) {
		Contact contact = getContactById(id);
		Address address = contact.getAddress();
		addressService.deleteAddress(address);
		deleteContact(contact);
		return "redirect:/";
	}

	private void deleteContact(Contact contact) {
		contactRepo.delete(contact);
	}
	

}
