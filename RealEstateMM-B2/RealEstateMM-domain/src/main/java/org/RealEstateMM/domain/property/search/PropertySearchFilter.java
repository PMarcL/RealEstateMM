package org.RealEstateMM.domain.property.search;

public class PropertySearchFilter {

	private static final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private static final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";

	private String filter;

	public PropertySearchFilter(String toParse) {
		this.filter = toParse;
	}

	public PropertySearchParameters getParsedSearchParameter() {
		PropertySearchParameters param;

		if (filter == null) {
			throw new InvalidFilterException();
		} else if (filter.equals(RECENTLY_UPLOADED_FIRST)) {
			param = PropertySearchParameters.RECENTLY_UPLOADED_FIRST;
		} else if (filter.equals(RECENTLY_UPLOADED_LAST)) {
			param = PropertySearchParameters.RECENTLY_UPLOADED_LAST;
		} else {
			throw new InvalidFilterException();
		}

		return param;
	}

}
