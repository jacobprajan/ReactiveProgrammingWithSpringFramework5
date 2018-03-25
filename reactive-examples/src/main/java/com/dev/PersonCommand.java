package com.dev;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCommand {

	private String firstName;
	private String lastName;
	
	public PersonCommand(Person person) {
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
	}
	
	public String sayMyName() {
		return "My name is " + firstName + " " + lastName + ".";
	}
	
}
