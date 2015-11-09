package org.RealEstateMM.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserFilter {

	private UserRepository userRepository;

	public UserFilter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ArrayList<User> getActiveBuyer() {
		ArrayList<User> users = userRepository.getAll();
		Stream<User> buyerStream = users.stream().filter(u -> u.getUserTypeDescription() == UserType.BUYER);
		List<User> activeBuyers = buyerStream.filter(b -> b.isActive()).collect(Collectors.toList());
		return new ArrayList<>(activeBuyers);
	}

}
