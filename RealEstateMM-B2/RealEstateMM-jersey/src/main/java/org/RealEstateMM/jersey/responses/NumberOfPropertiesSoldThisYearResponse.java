package org.RealEstateMM.jersey.responses;

public class NumberOfPropertiesSoldThisYearResponse {
	private int numberOfPropertiesSoldThisYearResponse;

	public NumberOfPropertiesSoldThisYearResponse(int numberOfPropertiesSoldThisYearResponse) {
		this.numberOfPropertiesSoldThisYearResponse = numberOfPropertiesSoldThisYearResponse;
	}

	public int getNumberOfPropertiesSoldThisYearResponse() {
		return numberOfPropertiesSoldThisYearResponse;
	}

	public void setNumberOfPropertiesSoldThisYearResponse(int numberOfPropertiesSoldThisYearResponse) {
		this.numberOfPropertiesSoldThisYearResponse = numberOfPropertiesSoldThisYearResponse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfPropertiesSoldThisYearResponse;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumberOfPropertiesSoldThisYearResponse other = (NumberOfPropertiesSoldThisYearResponse) obj;
		if (numberOfPropertiesSoldThisYearResponse != other.numberOfPropertiesSoldThisYearResponse)
			return false;
		return true;
	}

}
