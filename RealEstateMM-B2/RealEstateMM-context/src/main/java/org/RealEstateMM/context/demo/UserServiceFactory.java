package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.emailsender.gmail.GmailSender;
import org.RealEstateMM.domain.encoder.Base64Encoder;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCodeFactory;
import org.RealEstateMM.domain.user.emailconfirmation.EmailMessageFactory;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.services.user.UserService;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.UserServiceSecurity;
import org.RealEstateMM.services.user.dtos.UserAssembler;
import org.RealEstateMM.services.user.validation.UserInformationsValidator;
import org.RealEstateMM.services.user.validation.UserServiceAntiCorruption;

public class UserServiceFactory {

	public UserServiceHandler create(String baseUrl, UserRepository userRepository) {
		UserAssembler userAssembler = new UserAssembler(new UserRoleFactory());
		ConfirmationCodeFactory confirmCodeFactory = new ConfirmationCodeFactory(new Base64Encoder());
		EmailMessageFactory messageFactory = new EmailMessageFactory(baseUrl);
		UserEmailAddressValidator validator = new UserEmailAddressValidator(confirmCodeFactory, messageFactory,
				new GmailSender());
		Users users = new Users(userRepository, validator);

		UserServiceHandler service = new UserService(userAssembler, users);
		UserServiceSecurity userSecurity = new UserServiceSecurity(service, new UserAuthorizations(userRepository));
		return new UserServiceAntiCorruption(userSecurity, new UserInformationsValidator());
	}

}
