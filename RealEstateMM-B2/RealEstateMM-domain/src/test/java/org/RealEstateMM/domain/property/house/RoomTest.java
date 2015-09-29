package org.RealEstateMM.domain.property.house;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.AssertionFailedError;

public class RoomTest {

	@Test
	public void GivenARoomCreatedThenIsNotNull(){
		Room room = new Room(2, "bathRoom", 23.0, "wood");
		assertTrue(room != null);
	}
	
	@Test
	public void GivenARoomIsCreateThenItHaveTheGoodArgument(){
		Room room = new Room(2, "bathRoom", 23.0, "wood");
		assertTrue(room.getAreaSurface() == 23);
		assertTrue(room.getFloorType().equals("wood"));
		assertTrue(room.getFloorNumber() == 2);
		assertTrue(room.getRoomType().equals("bathRoom"));
		
	}
	
	@Test
	public void GivenTwoSameRoomThenTheyAreEquals(){
		Room room = new Room(2, "bathRoom", 23.0, "wood");
		Room room2 = new Room(2, "bathRoom", 23.0, "wood");
		assertTrue(room.equals(room2));
			
	}
	@Test
	public void GivenTwoDifferentsRoomThenTheyAreNotEquals(){
		Room room = new Room(2, "bathRoom", 23.0, "wood");
		Room room2 = new Room(1, "livingRoom", 23.0, "wood");
		assertFalse(room.equals(room2));
		
	}
}
