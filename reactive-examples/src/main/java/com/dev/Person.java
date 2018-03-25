package com.dev;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	private String firstName;
	private String lastName;
	
	public String sayMyName() {
		return "My name is " + firstName + " " + lastName + ".";
	}
	
}
