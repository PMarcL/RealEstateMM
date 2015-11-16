package org.RealEstateMM.jersey.responses;

public class JsonFormatter {

	
	public static String fieldToJSON(String title, String field){
		return "{\"" + title + "\":\""+ field + "\"}";
		
	}
}
