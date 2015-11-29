package org.RealEstateMM.persistence.xml.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-HH:mm:ss";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_NOW);

	synchronized public static String formatDate(Date date) {
		return (date == null ? null : dateFormatter.format(date));
	}

	synchronized public static Date parseDate(String date) throws ParseException {
		return (date == null ? null : dateFormatter.parse(date));
	}

}
