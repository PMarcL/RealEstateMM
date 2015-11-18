package org.RealEstateMM.jersey.responses.statistics;

public class NumberOfActiveUserResponse {

	private int numberOfActiveSeller;
	private int numberOfActiveBuyer;

	public NumberOfActiveUserResponse(int numberOfActiveSeller, int numberOfActiveBuyer) {
		super();
		this.numberOfActiveSeller = numberOfActiveSeller;
		this.numberOfActiveBuyer = numberOfActiveBuyer;
	}

	public int getNumberOfActiveSeller() {
		return numberOfActiveSeller;
	}

	public void setNumberOfActiveSeller(int numberOfActiveSeller) {
		this.numberOfActiveSeller = numberOfActiveSeller;
	}

	public int getNumberOfActiveBuyer() {
		return numberOfActiveBuyer;
	}

	public void setNumberOfActiveBuyer(int numberOfActiveBuyer) {
		this.numberOfActiveBuyer = numberOfActiveBuyer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfActiveBuyer;
		result = prime * result + numberOfActiveSeller;
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
		NumberOfActiveUserResponse other = (NumberOfActiveUserResponse) obj;
		if (numberOfActiveBuyer != other.numberOfActiveBuyer)
			return false;
		if (numberOfActiveSeller != other.numberOfActiveSeller)
			return false;
		return true;
	}

}
