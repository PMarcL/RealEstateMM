package org.RealEstateMM.persistence.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class XmlUserCollection {

	private List<XmlUser> users;

	public XmlUserCollection() {
		users = new ArrayList<XmlUser>();
	}

	public boolean contains(String pseudonym) {
		Optional<XmlUser> foundUser = find(pseudonym);
		return foundUser.isPresent();
	}

	private Optional<XmlUser> find(String pseudonym) {
		for (XmlUser user : users) {
			if (user.getPseudonym().equals(pseudonym)) {
				return Optional.of(user);
			}
		}

		return Optional.empty();
	}

	public void add(XmlUser newUser) {
		users.add(newUser);
	}

	public XmlUser getUser(String pseudonym) {
		Optional<XmlUser> foundUser = find(pseudonym);
		return foundUser.get();
	}

	public List<XmlUser> getUsers() {
		return users;
	}

	@XmlElement(name = "user")
	public void setUsers(List<XmlUser> users) {
		this.users = users;
	}

	public void removeUserWithPseudonym(String pseudonym) {
		// TODO Auto-generated method stub
	}

}
