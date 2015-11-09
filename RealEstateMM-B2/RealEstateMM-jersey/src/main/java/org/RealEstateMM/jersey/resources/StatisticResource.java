package org.RealEstateMM.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.RealEstateMM.jersey.responses.statistics.NumberOfActiveUserResponse;
import org.RealEstateMM.jersey.responses.statistics.NumberOfPropertiesSoldThisYearResponse;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.statistics.StatisticService;

@Path("/stats")
public class StatisticResource {

	private StatisticService statisticService;

	public StatisticResource() {
		this.statisticService = ServiceLocator.getInstance().getService(StatisticService.class);
	}

	public StatisticResource(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfPropertiesSoldThisYear() {
		int numberOfPropertiesSoldThisYearResponse = statisticService.getNumberOfPropertiesSoldThisYear();
		NumberOfPropertiesSoldThisYearResponse entity = new NumberOfPropertiesSoldThisYearResponse(
				numberOfPropertiesSoldThisYearResponse);
		return Response.ok().entity(entity).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfActiveUser() {
		int numberOfActiveSeller = statisticService.getNumberOfActiveSeller();
		int numberOfActiveBuyer = statisticService.getNumberOfActiveBuyer();
		NumberOfActiveUserResponse entity = new NumberOfActiveUserResponse(numberOfActiveSeller, numberOfActiveBuyer);
		return Response.ok().entity(entity).build();
	}

}
