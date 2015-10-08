package org.RealEstateMM.services.dtos.property.house;

import java.util.ArrayList;
import java.util.List;


public class HouseDTO {
	
	private List<RoomDTO> roomList;
	private int FloorNumber;
	private List<String> equipmentList;
	private Double landArea;
	private String landType;
	
	public HouseDTO(){
		
	}
	
	public HouseDTO(ArrayList<RoomDTO> listRoom, int floorNumber, ArrayList<String> listEquipment, Double landArea, String landType){
		this.equipmentList = listEquipment;
		this.FloorNumber = floorNumber;
		this.roomList = listRoom;
		this.setLandArea(landArea);
		this.setLandType(landType);
	}
	
	public List<RoomDTO> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RoomDTO> roomList) {
		this.roomList = roomList;
	}
	public int getFloorNumber() {
		return FloorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		FloorNumber = floorNumber;
	}
	public List<String> getEquipmentList() {
		return equipmentList;
	}
	public void setEquipmentList(List<String> equipmentList) {
		this.equipmentList = equipmentList;
	}
	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	public Double getLandArea() {
		return landArea;
	}

	public void setLandArea(Double landArea) {
		this.landArea = landArea;
	}


}
