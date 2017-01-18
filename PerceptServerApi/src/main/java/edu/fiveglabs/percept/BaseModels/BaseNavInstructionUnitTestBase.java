package edu.fiveglabs.percept.BaseModels;

public class BaseNavInstructionUnitTestBase {

	private long id;
	private long sourceLandmarkId;
	private long destinationLandmarkId;
	private String baseDirections;
	private long buildingId;
	private long distance;
	private int instructiontype;

	public BaseNavInstructionUnitTestBase() {
		super();
	}

	public long getId() {
		return id;
	}
	
	public int getInstructionType() {
		return instructiontype;
	}
	
	public void setInstructionType(int instructiontype) {
		this.instructiontype = instructiontype;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceLandmarkId() {
		return sourceLandmarkId;
	}

	public void setSourceLandmarkId(long sourceLandmarkId) {
		this.sourceLandmarkId = sourceLandmarkId;
	}

	public long getDestinationLandmarkId() {
		return destinationLandmarkId;
	}

	public void setDestinationLandmarkId(long destinationLandmarkId) {
		this.destinationLandmarkId = destinationLandmarkId;
	}

	public String getBaseDirections() {
		return baseDirections;
	}

	public void setBaseDirections(String baseDirections) {
		this.baseDirections = baseDirections;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

}