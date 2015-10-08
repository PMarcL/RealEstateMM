package org.RealEstateMM.services.dtos.property.house;

public class RoomDTO {
	
	private String RoomType;
	private Double AreaSurface;
	private String FloorType;
	private int FloorNumber;
	
	public RoomDTO(){
		
	}
	
	public RoomDTO(int floorNumber, String typeOfRoom, Double areaSurface, String floorType){
		this.FloorNumber = floorNumber;
		this.setRoomType(typeOfRoom);
		this.AreaSurface = areaSurface;
		this.FloorType = floorType;
	}
	
	public Double getAreaSurface() {
		return AreaSurface;
	}

	public void setAreaSurface(Double areaSurface) {
		AreaSurface = areaSurface;
	}

	public String getFloorType() {
		return FloorType;
	}

	public void setFloorType(String floorType) {
		FloorType = floorType;
	}

	public int getFloorNumber() {
		return FloorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		FloorNumber = floorNumber;
	}


	public String getRoomType() {
		return RoomType;
	}

	public void setRoomType(String roomType) {
		RoomType = roomType;
	}

}
