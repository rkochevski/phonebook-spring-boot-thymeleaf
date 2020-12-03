package com.example.phonebook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.phonebook.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

	List<Contact> findByPhoneNumberContainingOrSurnameContaining(String phoneNubmer, String surname);

	List<Contact> findByOrderBySurnameAsc();

	List<Contact> findByPhoneNumberLike(String phoneNumber);

	@Query("SELECT contact FROM Contact contact WHERE contact.phoneNumber LIKE :phoneNumber%")
	Page<Contact> findAllByPhoneNumber(@Param("phoneNumber")  String phoneNumber, Pageable pageable);

}
