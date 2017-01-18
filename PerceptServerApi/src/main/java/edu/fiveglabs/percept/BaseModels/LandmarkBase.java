package edu.fiveglabs.percept.BaseModels;

public class LandmarkBase {

	private long id;
	private String name;
	private double Count;
	private double Vertical;
	private double Layer;
	private double Opening;
	private double CloseIndicate;
	private double FireDoorIndicate;
	private boolean HallwayIndicate;
	private long BuildingId;
	private int Floor;
	private String detailedName;
	private long Shortwall;
	private long Altitude;
	private String Texture;
	private String Environment;
	private int ClockDirection;
	private int TravelByIndicate;

	public LandmarkBase() {
		super();
	}
	
	public int getClockDirection() {
		return ClockDirection;
	}
	
	public int getTravelByIndicate() {
		return TravelByIndicate;
	}

	public void setClockDirection(int clockdirection) {
		this.ClockDirection = clockdirection;
	}
	
	public void setTravelByIndicate(int travelbyindicate) {
		this.TravelByIndicate = travelbyindicate;
	}

	public int getFloor() {
		return Floor;
	}

	public void setFloor(int floor) {
		this.Floor = floor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCount() {
		return Count;
	}

	public void setCount(double count) {
		Count = count;
	}

	public double getVertical() {
		return Vertical;
	}

	public void setVertical(double vertical) {
		Vertical = vertical;
	}

	public double getLayer() {
		return Layer;
	}

	public void setLayer(double layer) {
		Layer = layer;
	}

	public double getOpening() {
		return Opening;
	}

	public void setOpening(double opening) {
		Opening = opening;
	}

	public double getCloseIndicate() {
		return CloseIndicate;
	}

	public void setCloseIndicate(double closeIndicate) {
		CloseIndicate = closeIndicate;
	}

	public double getFireDoorIndicate() {
		return FireDoorIndicate;
	}

	public void setFireDoorIndicate(double fireDoorIndicate) {
		FireDoorIndicate = fireDoorIndicate;
	}

	public boolean isHallwayIndicate() {
		return HallwayIndicate;
	}

	public void setHallwayIndicate(boolean hallwayIndicate) {
		HallwayIndicate = hallwayIndicate;
	}

	public long getBuildingId() {
		return BuildingId;
	}

	public void setBuildingId(long buildingId) {
		BuildingId = buildingId;
	}

	public String getDetailedName() {
		return detailedName;
	}

	public void setDetailedName(String detailedName) {
		this.detailedName = detailedName;
	}

	public long getShortwall() {
		return Shortwall;
	}

	public void setShortwall(long shortwall) {
		Shortwall = shortwall;
	}
	
	public void setAltitude(long altitude) {
		Altitude = altitude;
	}
	
	public long getAltitude() {
		return Altitude;
	}
	
	public void setTexture(String texture) {
		Texture = texture;
	}
	
	public String getTexture() {
		return Texture;
	}
	
	public void setEnvironment(String environment) {
		Environment = environment;
	}
	
	public String getEnvironment() {
		return Environment;
	}

}