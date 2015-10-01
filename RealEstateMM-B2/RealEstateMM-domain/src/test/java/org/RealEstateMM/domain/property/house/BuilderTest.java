package org.RealEstateMM.domain.property.house;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class BuilderTest {

	@Mock
	private House house;
	@Mock
	private Land land;
	@InjectMocks
	private Builder builder;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void GivenABuilderWhenCreationOfAHouseTheHouseProperlyReturnedNotEmpty() {

		House returnedHouse = builder.getHouse();
		assertTrue(returnedHouse != null);
	}

	@Test
	public void GivenABuilderWhenCreationOfRoomInTheHouseThenTheHouseHadOneRoom() {
		HouseBuilder builder = new Builder();
		builder.buildRoom(0, "bathroom", 12.0, "wood");
		House returnedHouse = builder.getHouse();
		assertTrue(returnedHouse.getRoomList().size() == 1);
	}

	@Test
	public void GivenABuilderWhenCreationOfEquipmentInTheHouseThenTheHouseHaveOneEquipment() {
		HouseBuilder builder = new Builder();
		builder.addEquipment("pool");
		House returnedHouse = builder.getHouse();
		assertTrue(returnedHouse.getEquipmentList().size() == 1);
	}

	@Test
	public void GivenABuilderWhenAddingAFloorThenTheHouseHaveFloor() {
		HouseBuilder builder = new Builder();
		builder.addFloor(2);
		House returnedHouse = builder.getHouse();
		assertTrue(returnedHouse.getFloorNumber() == 2);
	}

	@Test
	public void GivenABuilderWhenAddindALandThenTheHouseHaveALand() {
		HouseBuilder builder = new Builder();
		builder.addLand("residential", 200.3);
		when(house.getLand()).thenReturn(land);
		when(land.getLandArea()).thenReturn(200.3);
		House returnedHouse = builder.getHouse();
		assertTrue(returnedHouse.getLand().getLandArea() == 200.3);
	}
}
