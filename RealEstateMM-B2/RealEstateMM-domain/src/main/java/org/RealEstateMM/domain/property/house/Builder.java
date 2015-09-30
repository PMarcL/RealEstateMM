package org.RealEstateMM.domain.property.house;

public class Builder implements HouseBuilder{
	
	private House house;
	
	public Builder(){
		this.house = new House();
	}
	
	public void buildRoom(int floor, String roomType, Double surfaceArea, String floorType){
		this.house.addRoom(floor, roomType, surfaceArea, floorType);
	}
	
	public void addFloor(int floorNumber){
		this.house.addFloor(floorNumber);
	}
	
	public void addLand(String type, Double area){
		this.house.addLand(type, area);
	}
	
	public void addEquipment(String equipmentName){
		this.house.addEquipment(equipmentName);
	}
	
	
	@Override
	public House getHouse(){
		return this.house;
		
	}

}
