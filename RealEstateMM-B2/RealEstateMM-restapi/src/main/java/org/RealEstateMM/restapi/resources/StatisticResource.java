package org.RealEstateMM.restapi.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.statistics.StatisticService;

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
		int numberOfPropertiesSoldThisYear = statisticService.getNumberOfPropertiesSoldThisYear();

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("numberOfPropertiesSoldThisYear", "" + numberOfPropertiesSoldThisYear);

		return Response.ok().entity(map).build();
	}

	@GET
	@Path("numberofactiveuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfActiveUser() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("numberOfActiveSeller", "" + statisticService.getNumberOfActiveSeller());
		map.put("numberOfActiveBuyer", "" + statisticService.getNumberOfActiveBuyer());
		return Response.ok().entity(map).build();
	}

	@GET
	@Path("numberofonsaleproperties")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfOnSalePropertiesByCategory() {
		Map<String, String> resultsPerCategory = new HashMap<String, String>();
		for (PropertyType type : PropertyType.values()) {
			String numberOfPropertiesOfType = "" + statisticService.getNumberOfOnSalePropertiesPerType(type);
			resultsPerCategory.put(PropertyType.getStringFromType(type), numberOfPropertiesOfType);
		}
		return Response.ok().entity(resultsPerCategory).build();
	}

	@GET
	@Path("numberofactiveseller")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfSellersWithAnOnsaleProperties() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("numberOfSellerWithAProperty", "" + statisticService.getNumberOfActiveSeller());
		return Response.ok().entity(map).build();
	}
}
