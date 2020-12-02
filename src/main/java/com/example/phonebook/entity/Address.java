package com.example.phonebook.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	@Id
	@SequenceGenerator(name="mysequence", initialValue=21)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mysequence")
	private Integer id;
	
	private String street;
	
	private String city;

}
