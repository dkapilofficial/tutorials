package com.baeldung.spring.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {

	private long id;

	@NotNull
	@Size(min = 1)
	private String name;
	@NotNull
	@Size(min = 1)
	private String contactNumber;

	public Employee() {
		super();
	}

	//

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(final String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
