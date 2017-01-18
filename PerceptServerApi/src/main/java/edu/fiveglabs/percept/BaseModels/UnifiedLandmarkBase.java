package edu.fiveglabs.percept.BaseModels;

/**
 * Created by li on 6/22/2015.
 */
public class UnifiedLandmarkBase {

    private long id;
    private String name;
    private double count;
    private double vertical;
    private double layer;
    private double opening;
    private double closeIndicate;
    private double fireDoorIndicate;
    private boolean hallwayIndicate;
    private long buildingId;
    private int floor;
    private String detailedName;
    private long shortwall;
    private double altitude;
    private String texture;
    private String environment;
    private int clockDirection;
    private int travelByIndicate;
    private long rfid;
    private String landmarkName;
    private long landmarkNumber;
    private String locationString;
    private String location;
    private boolean isDestination;
    private String multiLandmarkType;
    private String tagLocation;
    private long transferId;
    private String landmarkType;
    private boolean isVirtualTag;

    public String getLandmarkType() {
        return landmarkType;
    }

    public void setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
    }

    public boolean isVirtualTag() {
        return isVirtualTag;
    }

    public void setIsVirtualTag(boolean isVirtualTag) {
        this.isVirtualTag = isVirtualTag;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
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
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getVertical() {
        return vertical;
    }

    public void setVertical(double vertical) {
        this.vertical = vertical;
    }

    public double getLayer() {
        return layer;
    }

    public void setLayer(double layer) {
        this.layer = layer;
    }

    public double getOpening() {
        return opening;
    }

    public void setOpening(double opening) {
        this.opening = opening;
    }

    public double getCloseIndicate() {
        return closeIndicate;
    }

    public void setCloseIndicate(double closeIndicate) {
        this.closeIndicate = closeIndicate;
    }

    public double getFireDoorIndicate() {
        return fireDoorIndicate;
    }

    public void setFireDoorIndicate(double fireDoorIndicate) {
        this.fireDoorIndicate = fireDoorIndicate;
    }

    public boolean isHallwayIndicate() {
        return hallwayIndicate;
    }

    public void setHallwayIndicate(boolean hallwayIndicate) {
        this.hallwayIndicate = hallwayIndicate;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDetailedName() {
        return detailedName;
    }

    public void setDetailedName(String detailedName) {
        this.detailedName = detailedName;
    }

    public long getShortwall() {
        return shortwall;
    }

    public void setShortwall(long shortwall) {
        this.shortwall = shortwall;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getClockDirection() {
        return clockDirection;
    }

    public void setClockDirection(int clockDirection) {
        this.clockDirection = clockDirection;
    }

    public int getTravelByIndicate() {
        return travelByIndicate;
    }

    public void setTravelByIndicate(int travelByIndicate) {
        this.travelByIndicate = travelByIndicate;
    }

    public long getRfid() {
        return rfid;
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

    public long getLandmarkNumber() {
        return landmarkNumber;
    }

    public void setLandmarkNumber(long landmarkNumber) {
        this.landmarkNumber = landmarkNumber;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDestination() {
        return isDestination;
    }

    public void setIsDestination(boolean isDestination) {
        this.isDestination = isDestination;
    }

    public String getMultiLandmarkType() {
        return multiLandmarkType;
    }

    public void setMultiLandmarkType(String multiLandmarkType) {
        this.multiLandmarkType = multiLandmarkType;
    }

    public String getTagLocation() {
        return tagLocation;
    }

    public void setTagLocation(String tagLocation) {
        this.tagLocation = tagLocation;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }


}
