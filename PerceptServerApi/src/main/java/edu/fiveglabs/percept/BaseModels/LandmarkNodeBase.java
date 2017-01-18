package edu.fiveglabs.percept.BaseModels;

public class LandmarkNodeBase {

	private long id;
	private long rfid;
	private String landmarkName;
	private int landmarkNumber;
	private String locationString;
	private long buildingId;
	private boolean isDestination;
	private int multiLandmarkType;
	private String ble;
	private String location;

	public String getBle() {
		return ble;
	}

	public void setBle(String ble) {
		this.ble = ble;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LandmarkNodeBase() {
		super();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRfid() {
		return rfid;
	}
	
	public int getMultiLandmarkType() {
		return multiLandmarkType;
	}
	
	public void setMultiLandmarkType(int multiLandmarkType) {
		this.multiLandmarkType = multiLandmarkType;
	}

	public void setRfid(long rfid) {
		this.rfid = rfid;
	}

	public String getLandmarkName() {
		return landmarkName;
	}

	public void setLandmarkName(String landmarkName) {
		this.landmarkName = landmarkName;
	}

	public int getLandmarkNumber() {
		return landmarkNumber;
	}

	public void setLandmarkNumber(int landmarkNumber) {
		this.landmarkNumber = landmarkNumber;
	}

	public String getLocationString() {
		return locationString;
	}

	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}


	public boolean isDestination() {
		return isDestination;
	}


	public void setDestination(boolean isDestination) {
		this.isDestination = isDestination;
	}

	

}