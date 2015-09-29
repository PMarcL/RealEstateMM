package org.RealEstateMM.services.dtos.user;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.usertype.UserType;
import org.RealEstateMM.domain.user.usertype.UserTypeFactory;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class UserAssembler {

	private UserTypeFactory factory;

	public UserAssembler() {
		factory = ServiceLocator.getInstance().getService(UserTypeFactory.class);
	}

	public UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		UserInformations userInfo = user.getUserInformations();
		dto.setEmail(userInfo.email);
		dto.setFirstName(userInfo.firstName);
		dto.setLastName(userInfo.lastName);
		dto.setPhoneNumber(userInfo.phoneNumber);
		dto.setPseudonym(user.getPseudonym());
		dto.setPassword(userInfo.password);
		dto.setUserType(user.getUserTypeDescription());
		return dto;
	}

	public User fromDTO(UserDTO userDTO) {
		UserInformations userInfo = new UserInformations(userDTO.getPseudonym(), userDTO.getPassword(),
				userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhoneNumber());
		UserType type = factory.makeUserType(userDTO.getUserType());
		return new User(userInfo, type);
	}
}
