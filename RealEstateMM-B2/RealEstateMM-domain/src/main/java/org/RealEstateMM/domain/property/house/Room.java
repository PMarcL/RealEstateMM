package org.RealEstateMM.domain.property.house;

public class Room {
	private String RoomType;
	private Double AreaSurface;
	private String FloorType;
	private int FloorNumber;

	public Room(int floorNumber, String typeOfRoom, Double areaSurface, String floorType) {
		this.FloorNumber = floorNumber;
		this.RoomType = typeOfRoom;
		this.AreaSurface = areaSurface;
		this.FloorType = floorType;
	}

	public Double getAreaSurface() {
		return AreaSurface;
	}

	public boolean isEquals(Room room) {
		boolean equality = false;

		if ((this.FloorNumber == room.FloorNumber) && (FloorType.equals(room.FloorType))
				&& (AreaSurface.equals(room.AreaSurface)) && (RoomType.equals(room.RoomType))) {
			equality = true;
		}

		return equality;
	}

	public String getRoomType() {
		return RoomType;
	}

	public String getFloorType() {
		return FloorType;
	}

	public int getFloorNumber() {
		return FloorNumber;
	}

}
