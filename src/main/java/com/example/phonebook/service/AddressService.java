package com.example.phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.phonebook.entity.Address;
import com.example.phonebook.repository.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	AddressRepository addressRepo;

	public void saveAddress(Address address) {
		addressRepo.save(address);
	}

	public void deleteAddress(Address address) {
		addressRepo.delete(address);
	}

}
