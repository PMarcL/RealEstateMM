package org.RealEstateMM.services.dtos.user;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformation;
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
		dto.setEmail(user.getEmail().toString());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setPhoneNumber(user.getPhoneNumber().toString());
		dto.setPseudonym(user.getPseudonym());
		dto.setPassword(user.getPassword());
		dto.setUserType(user.getUserTypeDescription());
		return dto;
	}

	public User fromDTO(UserDTO userDTO) {
		UserInformation userInfo = new UserInformation(userDTO.getPseudonym(), userDTO.getPassword(),
				userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhoneNumber());
		UserType type = factory.makeUserType(userDTO.getUserType());
		return new User(userInfo, type);
	}
}
