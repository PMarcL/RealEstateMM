package org.RealEstateMM.restapi.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.message.MessageService;
import org.RealEstateMM.services.message.dtos.MessageDTO;

public class MessageRessource {

	private MessageService messageService;
	private SessionService sessionService;

	public MessageRessource(MessageService contactRequestService, SessionService sessionService) {
		this.messageService = contactRequestService;
		this.sessionService = sessionService;
	}

	@GET
	@Path("contactseller")
	@Produces(MediaType.APPLICATION_JSON)
	public Response contactSeller(@Context HttpHeaders headers, MessageDTO message) {
		String token = headers.getHeaderString("Authorization");
		if (token == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			String buyerPseudonym = sessionService.validate(token);
			messageService.contactSeller(buyerPseudonym, message);
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (UserNotFoundException | UserIsNotASellerException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		return Response.ok().build();
	}

}
