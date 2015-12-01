package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.user.Administrator;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.locator.ServiceLocator;

public class DataInjector {

	private UserRepository userRepository;

	public DataInjector() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
	}

	public void injectData() {
		if (isAdminExisting("ADMIN"))
			return;

		UserInformations adminInfo = new UserInformations("ADMIN", "admin1234", "Olivier", "Dugas",
				"olivierD@admin.com", "418 892-3940");
		User admin = new User(adminInfo, new Administrator());
		admin.unlock();
		try {
			userRepository.addUser(admin);
		} catch (ExistingUserException e) {
			e.printStackTrace();
		}
	}

	private boolean isAdminExisting(String adminPseudonym) {
		try {
			userRepository.getUserWithPseudonym(adminPseudonym);
			return true;
		} catch (UserNotFoundException e) {
			return false;
		}
	}
}
