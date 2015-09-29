package org.RealEstateMM.persistence.xml;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.usertype.UserType;
import org.RealEstateMM.domain.user.usertype.UserTypeFactory;

public class XmlUserAssembler {

	private UserTypeFactory userTypeFactory;

	public XmlUserAssembler(UserTypeFactory factory) {
		userTypeFactory = factory;
	}

	public XmlUser fromUser(User user) {
		XmlUser newUser = new XmlUser();
		UserInformations userInfo = user.getUserInformations();

		newUser.setPseudonym(userInfo.pseudonym);
		newUser.setPassword(userInfo.password);
		newUser.setFirstName(userInfo.firstName);
		newUser.setLastName(userInfo.lastName);
		newUser.setEmail(userInfo.email);
		newUser.setPhoneNumber(userInfo.phoneNumber);
		newUser.setUserType(user.getUserTypeDescription());

		return newUser;
	}

	public User toUser(XmlUser xmlUser) {
		UserInformations userInfo = new UserInformations(xmlUser.getPseudonym(), xmlUser.getPassword(),
				xmlUser.getFirstName(), xmlUser.getLastName(), xmlUser.getEmail(), xmlUser.getPhoneNumber());
		UserType userType = userTypeFactory.makeUserType(xmlUser.getUserType());

		User user = new User(userInfo, userType);
		return user;
	}

}
