package org.RealEstateMM.persistence.xml;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;

public class XmlUserAssembler {

	public XmlUser fromUser(User user) {
		XmlUser newUser = new XmlUser();
		UserInformations userInfo = user.getUserInformations();

		newUser.setPseudonym(userInfo.pseudonym);
		newUser.setPassword(userInfo.password);
		newUser.setFirstName(userInfo.firstName);
		newUser.setLastName(userInfo.lastName);
		newUser.setEmail(userInfo.emailAddress);
		newUser.setPhoneNumber(userInfo.phoneNumber);
		newUser.setUserType(user.getUserTypeDescription());
		newUser.setLock(user.isLocked());

		return newUser;
	}

	public User toUser(XmlUser xmlUser) {
		UserInformations userInfo = new UserInformations(xmlUser.getPseudonym(), xmlUser.getPassword(),
				xmlUser.getFirstName(), xmlUser.getLastName(), xmlUser.getEmail(), xmlUser.getPhoneNumber());
		UserType userType = new UserType(xmlUser.getUserType());

		User user = new User(userInfo, userType);
		return user;
	}

}
