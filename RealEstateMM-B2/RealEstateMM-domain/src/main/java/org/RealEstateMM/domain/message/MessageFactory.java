package org.RealEstateMM.domain.message;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;

public class MessageFactory {

	public Message createContactSellerMessage(User buyer, User seller, String buyersMessage) {
		UserInformations sellerInfo = seller.getUserInformations();
		UserInformations buyerInfo = buyer.getUserInformations();

		String message = "Hi " + sellerInfo.firstName + ",\n";
		message += buyerInfo.firstName + " " + buyerInfo.lastName + "would like to get in contact with you";

		if (message != null) {
			message += ":\n\n" + buyersMessage + "\n\n";
		}

		message += "Here are the buyer's informations:\n" + buyerInfo.firstName + " " + buyerInfo.lastName + "\n"
				+ buyerInfo.emailAddress + buyerInfo.phoneNumber;

		return new Message(buyer.getPseudonym(), seller.getPseudonym(), message, true);
	}

}
