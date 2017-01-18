package edu.fiveglabs.percept.BaseModels;

public class BuildingBase {

	private long id;
	private String name;
	private String locationString;
	private int floor;

	public BuildingBase() {
		super();
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

	public String getLocationString() {
		return locationString;
	}

	public void setLocationString(String location) {
		this.locationString = location;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

}