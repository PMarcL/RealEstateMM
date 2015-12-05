package org.RealEstateMM.domain.message;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;

public class MessageFactory {

	public Message createContactSellerMessage(User buyer, User seller, String buyersMessage) {
		UserInformations buyerInfo = buyer.getUserInformations();

		String message = buyersMessage + "\n\n" + buyerInfo.firstName + " " + buyerInfo.lastName + "\n"
				+ buyerInfo.emailAddress + " " + buyerInfo.phoneNumber;

		return new Message(buyer.getPseudonym(), seller.getPseudonym(), message, true);
	}

}
