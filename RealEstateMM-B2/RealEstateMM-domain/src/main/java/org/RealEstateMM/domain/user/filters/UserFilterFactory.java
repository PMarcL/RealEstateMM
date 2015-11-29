package org.RealEstateMM.domain.user.filters;

public class UserFilterFactory {

	public UserTypeFilter createUserTypeFilter() {
		return new UserTypeFilter();
	}

	public UserLoggedInTheLastSixMonthsFilter createLoggedInTheLastSixMonthsFilter() {
		return new UserLoggedInTheLastSixMonthsFilter();
	}

}
