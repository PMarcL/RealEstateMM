package org.RealEstateMM.restapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.message.UserIsNotTheRecipient;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.message.MessageService;
import org.RealEstateMM.services.message.dtos.MessageDTO;

@Path("/message")
public class MessageResource {

	private MessageService messageService;
	private SessionService sessionService;

	public MessageResource() {
		this.messageService = ServiceLocator.getInstance().getService(MessageService.class);
		this.sessionService = ServiceLocator.getInstance().getService(SessionService.class);
	}

	@PUT
	@Path("/{id}")
	public Response readMessage(@Context HttpHeaders headers, @PathParam("token") String messageId) {
		String token = headers.getHeaderString("Authorization");
		if (token == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			String pseudo = sessionService.validate(token);
			messageService.readMessage(messageId, pseudo);
			return Response.ok().build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (UserIsNotTheRecipient e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("/unread")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnreadMessages(@Context HttpHeaders headers) {
		String token = headers.getHeaderString("Authorization");
		if (token == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			String pseudo = sessionService.validate(token);
			List<MessageDTO> messages = messageService.getUnreadMessages(pseudo);
			return Response.ok().entity(messages).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Path("/contactseller")
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
