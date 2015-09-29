package org.RealEstateMM.persistence.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlUserCollection {

	@XmlElement(name = "user")
	private Map<String, XmlUser> users;

	public XmlUserCollection() {
		users = new HashMap<String, XmlUser>();
	}

	public boolean contains(String pseudonym) {
		return users.containsKey(pseudonym);
	}

	public void add(XmlUser newUser) {
		users.put(newUser.getPseudonym(), newUser);
	}

	public XmlUser getUser(String pseudonym) {
		return users.get(pseudonym);
	}

}
