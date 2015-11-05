package org.RealEstateMM.persistence.xml.user;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRole;
import org.RealEstateMM.domain.user.UserRoleFactory;

public class XmlUserAssembler {

	private UserRoleFactory roleFactory;

	public XmlUserAssembler(UserRoleFactory roleFactory) {
		this.roleFactory = roleFactory;
	}

	public XmlUser fromUser(User user) {
		XmlUser newUser = new XmlUser();
		UserInformations userInfo = user.getUserInformations();

		newUser.setPseudonym(userInfo.pseudonym);
		newUser.setPassword(userInfo.password);
		newUser.setFirstName(userInfo.firstName);
		newUser.setLastName(userInfo.lastName);
		newUser.setEmail(userInfo.emailAddress);
		newUser.setPhoneNumber(userInfo.phoneNumber);
		newUser.setUserType(user.getRoleDescription().toString());
		newUser.setLocked(user.isLocked());

		return newUser;
	}

	public User toUser(XmlUser xmlUser) {
		UserInformations userInfo = buildUserInformations(xmlUser);
		UserRole userType = roleFactory.create(xmlUser.getUserType());

		User user = new User(userInfo, userType);
		if (userIsUnlocked(xmlUser)) {
			user.unlock();
		}

		return user;
	}

	private UserInformations buildUserInformations(XmlUser xmlUser) {
		UserInformations userInfo = new UserInformations(xmlUser.getPseudonym(), xmlUser.getPassword(),
				xmlUser.getFirstName(), xmlUser.getLastName(), xmlUser.getEmail(), xmlUser.getPhoneNumber());
		return userInfo;
	}

	private boolean userIsUnlocked(XmlUser xmlUser) {
		return !xmlUser.getLocked();
	}

}
