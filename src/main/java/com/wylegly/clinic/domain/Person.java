package com.wylegly.clinic.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.pl.PESEL;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
public abstract class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	protected int id;
	
	@Column(name = "pesel")
	@PESEL
	protected String pesel;
	
	@Column(name = "surname")
	protected String surname;
	
	@Column(name = "first_name")
	protected String firstName;

	@Column(name = "second_name")
	protected String secondName;

	public Person() {
	
	}
	
	public Person(@PESEL String pesel, String surname, 
			String firstName, String secondName) {
		this.pesel = pesel;
		this.surname = surname;
		this.firstName = firstName;
		this.secondName = secondName;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", pesel=" + pesel 
				+ ", surname=" + surname + ", firstName=" + firstName
				+ ", secondName=" + secondName + "]";
	}
		
	
	
}
