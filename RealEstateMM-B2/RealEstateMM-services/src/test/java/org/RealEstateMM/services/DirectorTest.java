package org.RealEstateMM.services;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.house.House;
import org.RealEstateMM.domain.property.house.HouseBuilder;
import org.RealEstateMM.services.dto.HouseDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class DirectorTest {

	@Mock
	private HouseBuilder builder;
	
	@Mock
	private HouseDTO houseDTO;
	
	@Mock
	private House house;
	
	@InjectMocks
	private Director directorMocked;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		directorMocked = new Director(builder);
	}
	
	@Test
	public void GivenAClientCreateDirectorToBuildAHouseFromIsSpecification(){
		when(directorMocked.ConstructHouse(houseDTO)).thenReturn(house);
		when(house.getFloorNumber()).thenReturn(2);
		assertTrue(house.getFloorNumber() == 2);
	}
}
