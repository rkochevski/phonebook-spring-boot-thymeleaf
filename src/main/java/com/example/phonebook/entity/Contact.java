package com.example.phonebook.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "phoneNumber")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	
	@Id
	@SequenceGenerator(name="mysequence", initialValue=21)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mysequence")
	private Integer id;
	
	private String name;
	
	private String surname;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@NotNull
	private String phoneNumber;

}
