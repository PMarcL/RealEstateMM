package org.RealEstateMM.services.dto;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.house.Builder;
import org.RealEstateMM.domain.property.house.House;
import org.RealEstateMM.domain.property.house.Room;
import org.RealEstateMM.services.Director;

public class HouseAssembler {
	
	public HouseDTO toDTO(House house){
		HouseDTO houseDTO = new HouseDTO();
		ArrayList<RoomDTO> listRoomDTO = new ArrayList<RoomDTO>();
		for (Room room : house.getRoomList()) {
			listRoomDTO.add(new RoomDTO(room.getFloorNumber(),
					room.getRoomType(),
					room.getAreaSurface(),
					room.getFloorType()));
		}
		houseDTO.setRoomList(listRoomDTO);
		houseDTO.setFloorNumber(house.getFloorNumber());
		houseDTO.setEquipmentList(house.getEquipmentList());
		houseDTO.setLandArea(house.getLand().getLandArea());
		houseDTO.setLandType(house.getLand().getLandType());
		return houseDTO;
	}
	
	public House fromDTO(HouseDTO dto){
		Director director = new Director(new Builder());
		return director.ConstructHouse(dto);
		
	}
}
