package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.house.House;
import org.RealEstateMM.domain.property.house.HouseBuilder;
import org.RealEstateMM.services.dto.HouseDTO;
import org.RealEstateMM.services.dto.RoomDTO;

public class Director {
	
	private HouseBuilder builder;
	
	public Director(HouseBuilder builderHouse){
		builder = builderHouse;
	}
	
	public House ConstructHouse(HouseDTO houseDTO){
		for (RoomDTO room : houseDTO.getRoomList()) {
			builder.buildRoom(room.getFloorNumber(), room.getRoomType(), room.getAreaSurface(), room.getFloorType());
		}
		builder.addFloor(houseDTO.getFloorNumber());
		
		for (String equipment : houseDTO.getEquipmentList()) {
			builder.addEquipment(equipment);
		}
		builder.addLand(houseDTO.getLandType(),houseDTO.getLandArea());
		
		return builder.getHouse();
		
	}
}
