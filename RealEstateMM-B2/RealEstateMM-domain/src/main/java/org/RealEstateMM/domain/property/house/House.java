package org.RealEstateMM.domain.property.house;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class House {
	private List<Room> roomList;
	private int FloorNumber;
	private List<String> equipmentList;
	private Land land;
	
	public House(){
		this.roomList = new ArrayList<Room>();
		this.FloorNumber = 1;
		this.equipmentList = new ArrayList<String>();
		this.land = new Land();
	}
	
	public void addRoom(int floor, String roomType, Double surfaceArea, String floorType){
		roomList.add(new Room(floor, roomType, surfaceArea, floorType));
	}
	
	public void addFloor(int floorNumber){
		FloorNumber = floorNumber;
	}
	
	public void addEquipment(String equipmentName){
		equipmentList.add(equipmentName);
	}
	
	public void addLand(String type, Double area){
		land.setLandArea(area);
		land.setLandType(type);
	}
	
	public boolean isEquals(House house){
		boolean equality = true;
		for (Room room : roomList) {
			
			if(house.containingRoom(room) == false){
				equality = false;
			}
		}
		return equality;
		
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	public int getFloorNumber() {
		return FloorNumber;
	}

	public List<String> getEquipmentList() {
		return equipmentList;
	}

	public Land getLand() {
		return land;
	}
	
	private boolean containingRoom(Room room){
		boolean containing = false;
		for (Room roomIterator : roomList) {
			if(roomIterator.equals(room)){
				containing = true;
			}
		}
		return containing;
	}
	
	

}
