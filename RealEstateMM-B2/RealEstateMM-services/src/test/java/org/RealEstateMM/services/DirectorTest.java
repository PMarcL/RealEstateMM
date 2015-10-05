package org.RealEstateMM.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.house.House;
import org.RealEstateMM.domain.property.house.HouseBuilder;
import org.RealEstateMM.services.dto.HouseDTO;
import org.RealEstateMM.services.dto.RoomDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DirectorTest {
	@Mock
	private HouseBuilder builder;

	@Mock
	private HouseDTO houseDTO;

	@Mock
	private House house;

	private Director director;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		director = new Director(builder);
		when(builder.getHouse()).thenReturn(house);
	}

	@Test
	public void GivenAClientCreateDirectorToBuildAHouseFromIsSpecification() {
		director.ConstructHouse(houseDTO);
		verify(builder).getHouse();
	}

	@Test
	public void GivenAClientCreateDirectorToBuildAHouseFromIsSpecificationWithEquipment() {
		ArrayList<String> equipment = new ArrayList<String>();
		equipment.add("pool");
		when(houseDTO.getEquipmentList()).thenReturn(equipment);
		director.ConstructHouse(houseDTO);
		verify(builder).addEquipment("pool");
		verify(builder).getHouse();
	}

	@Test
	public void GivenAClientCreateDirectorToBuildAHouseFromIsSpecificationWithRoom() {
		ArrayList<RoomDTO> roomDTO = new ArrayList<RoomDTO>();
		roomDTO.add(new RoomDTO(1, "bathRoom", 12.3, "ceramic"));
		when(houseDTO.getRoomList()).thenReturn(roomDTO);
		director.ConstructHouse(houseDTO);
		verify(builder).buildRoom(1, "bathRoom", 12.3, "ceramic");
		verify(builder).getHouse();
	}
}
