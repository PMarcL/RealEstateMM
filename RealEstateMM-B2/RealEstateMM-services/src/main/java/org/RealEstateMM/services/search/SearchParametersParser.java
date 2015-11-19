package org.RealEstateMM.services.search;

import org.RealEstateMM.domain.search.PropertySearchParameters;

public class SearchParametersParser {

	private static final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private static final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";

	private static final String HIGHEST_PRICE_FIRST = "highest_price_first";
	private static final String HIGHEST_PRICE_LAST = "highest_price_last";

	public PropertySearchParameters getParsedSearchParameter(String toParse) throws InvalidSearchParameterException {
		PropertySearchParameters param;

		if (toParse == null) {
			throw new InvalidSearchParameterException();
		} else if (toParse.equals(RECENTLY_UPLOADED_FIRST)) {
			param = PropertySearchParameters.RECENTLY_UPLOADED_FIRST;
		} else if (toParse.equals(RECENTLY_UPLOADED_LAST)) {
			param = PropertySearchParameters.RECENTLY_UPLOADED_LAST;
		} else if (toParse.equals(HIGHEST_PRICE_FIRST)) {
			param = PropertySearchParameters.HIGHEST_PRICE_FIRST;
		} else if (toParse.equals(HIGHEST_PRICE_LAST)) {
			param = PropertySearchParameters.HIGHEST_PRICE_LAST;
		} else {
			throw new InvalidSearchParameterException();
		}
		return param;
	}

}
