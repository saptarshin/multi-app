package com.sample.reporting.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ContentToSave {
	
	@Id
	@GeneratedValue
	private Long id;

	private String traderId;
	private String firstName;
	private String lastName;
	private String nationality;
	private String countrOfResidence;
	private String dateOfBirth;
	
	protected ContentToSave() {}

	public ContentToSave(String traderId, String firstName, String lastName, String nationality,
			String countrOfResidence, String dateOfBirth) {
		this.traderId = traderId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.countrOfResidence = countrOfResidence;
		this.dateOfBirth = dateOfBirth;
	}

	public String getTraderId() {
		return traderId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public String getCountrOfResidence() {
		return countrOfResidence;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

}
