package org.RealEstateMM.domain.property.house;

import static org.junit.Assert.*;
import org.junit.Test;

public class HouseTest {

	@Test
	public void GivenAHouseIsCreatedThenItIsNotNull() {
		House house = new House();
		assertTrue(house != null);
	}

	@Test
	public void GivenAHouseIsCreatedWithFloorNumberThenItTheHouseHaveAFloorValue() {
		House house = new House();
		house.addFloor(2);
		assertTrue(house.getFloorNumber() == 2);
	}

	@Test
	public void GivenTwoSameHouseWithSameRoomListThenTheyAreEquals() {
		House house = new House();
		house.addRoom(2, "bathRoom", 232.0, "ceramic");
		house.addRoom(3, "bathRoom", 10.0, "ceramic");
		assertTrue(house.isEquals(house));
	}

}
