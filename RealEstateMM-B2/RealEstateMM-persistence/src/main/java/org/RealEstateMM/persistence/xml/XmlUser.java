package org.RealEstateMM.persistence.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class XmlUser {

	private String pseudonym;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String userType;

	@XmlElement(name = "pseudonym")
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public String getPseudonym() {
		return this.pseudonym;
	}

	@XmlElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	@XmlElement(name = "firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	@XmlElement(name = "lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	@XmlElement(name = "email")
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	@XmlElement(name = "phoneNumber")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@XmlElement(name = "userType")
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return this.userType;
	}

}
