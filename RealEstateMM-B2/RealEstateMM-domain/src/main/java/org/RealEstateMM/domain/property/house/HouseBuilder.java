package org.RealEstateMM.domain.property.house;

public interface HouseBuilder {
	
	public void buildRoom(int floor, String roomType, Double surfaceArea, String floorType);
	
	public House getHouse();
	
	public void addFloor(int floorNumber);
	
	public void addEquipment(String equipmentName);
	
	public void addLand(String type, Double area);
}
