package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.property.search.PropertyOrderingParameters;

public class PropertySearchParametersParser {

	private static final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private static final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";

	private static final String HIGHEST_PRICE_FIRST = "highest_price_first";
	private static final String HIGHEST_PRICE_LAST = "highest_price_last";

	public PropertyOrderingParameters getParsedSearchParameter(String toParse) throws InvalidSearchParameterException {
		PropertyOrderingParameters param;

		if (toParse == null) {
			throw new InvalidSearchParameterException();
		} else if (toParse.equals(RECENTLY_UPLOADED_FIRST)) {
			param = PropertyOrderingParameters.RECENTLY_UPLOADED_FIRST;
		} else if (toParse.equals(RECENTLY_UPLOADED_LAST)) {
			param = PropertyOrderingParameters.RECENTLY_UPLOADED_LAST;
		} else if (toParse.equals(HIGHEST_PRICE_FIRST)) {
			param = PropertyOrderingParameters.HIGHEST_PRICE_FIRST;
		} else if (toParse.equals(HIGHEST_PRICE_LAST)) {
			param = PropertyOrderingParameters.HIGHEST_PRICE_LAST;
		} else {
			throw new InvalidSearchParameterException();
		}
		return param;
	}

}
