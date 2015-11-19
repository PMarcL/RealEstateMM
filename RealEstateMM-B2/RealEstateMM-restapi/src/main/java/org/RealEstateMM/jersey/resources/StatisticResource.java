package org.RealEstateMM.jersey.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.jersey.responses.JsonFormatter;
import org.RealEstateMM.jersey.responses.statistics.NumberOfActiveUserResponse;
import org.RealEstateMM.jersey.responses.statistics.NumberOfPropertiesSoldThisYearResponse;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.statistics.StatisticService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/stats")
public class StatisticResource {

	private StatisticService statisticService;

	public StatisticResource() {
		this.statisticService = ServiceLocator.getInstance().getService(StatisticService.class);
	}

	@GET
	@Path("numberofsoldproperties")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfPropertiesSoldThisYear() {
		int numberOfPropertiesSoldThisYearResponse = statisticService.getNumberOfPropertiesSoldThisYear();
		NumberOfPropertiesSoldThisYearResponse entity = new NumberOfPropertiesSoldThisYearResponse(
				numberOfPropertiesSoldThisYearResponse);
		return Response.ok().entity(entity).build();
	}

	@GET
	@Path("numberofactiveusers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfActiveUser() {
		int numberOfActiveSeller = statisticService.getNumberOfActiveSeller();
		int numberOfActiveBuyer = statisticService.getNumberOfActiveBuyer();
		NumberOfActiveUserResponse entity = new NumberOfActiveUserResponse(numberOfActiveSeller, numberOfActiveBuyer);
		return Response.ok().entity(entity).build();
	}

	@GET
	@Path("numberofonsaleproperties")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfOnSalePropertiesByCategory() {
		Map<String, Integer> resultsPerCategory = new HashMap<String, Integer>();
		for (PropertyType type : PropertyType.values()) {
			int numberOfPropertiesOfType = statisticService.getNumberOfOnSalePropertiesPerType(type);
			resultsPerCategory.put(PropertyType.getStringFromType(type), numberOfPropertiesOfType);
		}
		try {
			String propertiesAsJson = new ObjectMapper().writeValueAsString(resultsPerCategory);
			return Response.ok().entity(propertiesAsJson).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("numberofactiveseller")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfSellersWithAnOnsaleProperties() {
		try {
			String numberOfActiveSellers = String.valueOf(statisticService.getNumberOfActiveSeller());
			return Response.ok().entity(JsonFormatter.fieldToJSON("numberOfSellerWithAProperty", numberOfActiveSellers))
					.build();
		} catch (Exception e) {
			return Response.serverError().build();
		}

	}
}
