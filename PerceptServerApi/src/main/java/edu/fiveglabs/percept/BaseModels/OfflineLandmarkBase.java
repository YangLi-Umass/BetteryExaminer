package edu.fiveglabs.percept.BaseModels;

public class OfflineLandmarkBase {

	public static final int TYPE_ELEVATOR = -1;
	public static final int TYPE_MENS_RESTROOM = -2;
	public static final int TYPE_WOMENS_RESTROOM = -3;
	public static final int TYPE_STAIRS = -4;
	public static final int TYPE_ENTRANCE = -5;
	public static final int TYPE_SUITE225 = -6;
	
	
	private long id;
	private long rfid;
	private String name;
	private long buildingId;
	private String detailedName;
	private long floor;
	private long landmarkId;
	private boolean isDestination;
	private int MultiLandmarkType;
	private int TagLocation;
	
	public OfflineLandmarkBase() {
		super();
	}

	
	public long getId() {
		return id;
	}


	public long getRfid() {
		return rfid;
	}


	public String getName() {
		return name;
	}


	public long getBuildingId() {
		return buildingId;
	}


	public String getDetailedName() {
		return detailedName;
	}


	public long getFloor() {
		return floor;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setRfid(long rfid) {
		this.rfid = rfid;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}


	public void setDetailedName(String detailedName) {
		this.detailedName = detailedName;
	}


	public void setFloor(long floor) {
		this.floor = floor;
	}


	public long getLandmarkId() {
		return landmarkId;
	}


	public void setLandmarkId(long landmarkId) {
		this.landmarkId = landmarkId;
	}


	public boolean isDestination() {
		return isDestination;
	}


	public void setDestination(boolean isDestination) {
		this.isDestination = isDestination;
	}


	public int getMultiLandmarkType() {
		return MultiLandmarkType;
	}


	public void setMultiLandmarkType(int multiLandmarkType) {
		MultiLandmarkType = multiLandmarkType;
	}


	public int getTagLocation() {
		return TagLocation;
	}


	public void setTagLocation(int tagLocation) {
		TagLocation = tagLocation;
	}
	
	public static String getMultiLandmarkName(int landmarkType){
		if(landmarkType == TYPE_ENTRANCE){
			return "Entrance";
		}
		else if(landmarkType == TYPE_WOMENS_RESTROOM){
			return "Womens Restroom";
		}
		else if(landmarkType == TYPE_STAIRS){
			return "Stairway";
		}
		else if(landmarkType == TYPE_MENS_RESTROOM){
			return "Mens Restroom";
		}
		else if(landmarkType == TYPE_ELEVATOR){
			return "Elevator";
		}
		else if(landmarkType == TYPE_SUITE225){
			return "Suite 225";
		}
		else{
			return "unspecified landmark";
		}
		
	}

}