package org.RealEstateMM.services.roles;

import org.RealEstateMM.services.dtos.user.UserDTO;

public interface RightManager {

	boolean isAllowedToEdit(UserDTO owner);

}
