package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.UnifiedLandmarkBase;

public class UnifiedLandmark extends UnifiedLandmarkBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_NAME = "Name";
	public static final String JSON_COUNT = "Count";
	public static final String JSON_VERTICAL = "Vertical";
	public static final String JSON_LAYER = "Layer";
	public static final String JSON_OPENING = "Opening";
	public static final String JSON_CLOSE_INDICATE = "CloseIndicate";
	public static final String JSON_FIRE_DOOR_INDICATE = "FireDoorIndicate";
	public static final String JSON_HALLWAY_INDICATE = "HallwayIndicate";
	public static final String JSON_BUILDING_ID = "BuildingId";
	public static final String JSON_FLOOR = "Floor";
	public static final String JSON_DETAILED_NAME = "DetailedName";
	public static final String JSON_SHORTWALL = "Shortwall";
	public static final String JSON_ALTITUDE = "Altitude";
	public static final String JSON_TEXTURE = "Texture";
	public static final String JSON_ENVIRONMENT = "Environment";
	public static final String JSON_CLOCK_DIRECTION = "ClockDirection";
	public static final String JSON_TRAVEL_BY_INDICATE = "TravelByIndicate";
	public static final String JSON_RFID = "RFID";
	public static final String JSON_LANDMARK_NAME = "LandmarkName";
	public static final String JSON_LANDMARK_NUMBER = "LandmarkNumber";
	public static final String JSON_LOCATION_STRING = "LocationString";
	public static final String JSON_LOCATION = "Location";
	public static final String JSON_IS_DESTINATION = "IsDestination";
	public static final String JSON_MULTI_LANDMARK_TYPE = "MultiLandmarkType";
	public static final String JSON_TAG_LOCATION = "TagLocation";
	public static final String JSON_TRANSFER_ID = "TransferId";
	public static final String JSON_LANDMARK_TYPE = "LandmarkType";
	public static final String JSON_IS_VIRTUAL_TAG = "IsVirtualTag";

	private JSONObject json;

	public UnifiedLandmark(){
		json = new JSONObject();
	}
	
	/*public UnifiedLandmark packageObject(long _id, String _name, String _username, int _userType){
		
		setId(_id);
		setName(_name);
		setUsername(_username);
		setUserType(_userType);

		return this;
	}*/
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(UnifiedLandmark.JSON_ID, getId());
			this.json.put(UnifiedLandmark.JSON_NAME, getName());
			this.json.put(UnifiedLandmark.JSON_COUNT, getCount());
			this.json.put(UnifiedLandmark.JSON_VERTICAL, getVertical());
			this.json.put(UnifiedLandmark.JSON_LAYER, getLayer());
			this.json.put(UnifiedLandmark.JSON_OPENING, getOpening());
			this.json.put(UnifiedLandmark.JSON_CLOSE_INDICATE, getCloseIndicate());
			this.json.put(UnifiedLandmark.JSON_FIRE_DOOR_INDICATE, getFireDoorIndicate());
			this.json.put(UnifiedLandmark.JSON_HALLWAY_INDICATE, isHallwayIndicate());
			this.json.put(UnifiedLandmark.JSON_BUILDING_ID, getBuildingId());
			this.json.put(UnifiedLandmark.JSON_FLOOR, getFloor());
			this.json.put(UnifiedLandmark.JSON_DETAILED_NAME, getDetailedName());
			this.json.put(UnifiedLandmark.JSON_SHORTWALL, getShortwall());
			this.json.put(UnifiedLandmark.JSON_ALTITUDE, getAltitude());
			this.json.put(UnifiedLandmark.JSON_TEXTURE, getTexture());
			this.json.put(UnifiedLandmark.JSON_ENVIRONMENT, getEnvironment());
			this.json.put(UnifiedLandmark.JSON_CLOCK_DIRECTION, getClockDirection());
			this.json.put(UnifiedLandmark.JSON_TRAVEL_BY_INDICATE, getTravelByIndicate());
			this.json.put(UnifiedLandmark.JSON_RFID, getRfid());
			this.json.put(UnifiedLandmark.JSON_LANDMARK_NAME, getLandmarkName());
			this.json.put(UnifiedLandmark.JSON_LANDMARK_NUMBER, getLandmarkNumber());
			this.json.put(UnifiedLandmark.JSON_LOCATION_STRING, getLocationString());
			this.json.put(UnifiedLandmark.JSON_LOCATION, getLocation());
			this.json.put(UnifiedLandmark.JSON_IS_DESTINATION, isDestination());
			this.json.put(UnifiedLandmark.JSON_MULTI_LANDMARK_TYPE, getMultiLandmarkType());
			this.json.put(UnifiedLandmark.JSON_TAG_LOCATION, getTagLocation());
			this.json.put(UnifiedLandmark.JSON_TRANSFER_ID, getTransferId());
			this.json.put(UnifiedLandmark.JSON_LANDMARK_TYPE, getLandmarkType());
			this.json.put(UnifiedLandmark.JSON_IS_VIRTUAL_TAG, isVirtualTag());

			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("JsonPackageOfUnifiedLM", e.toString());
			return null;
		}
	}
	
	public static List<UnifiedLandmark> parseJSONArray(JSONArray jsonArray) {
		List<UnifiedLandmark> unifiedLandmarks = new ArrayList<UnifiedLandmark>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				UnifiedLandmark unifiedLandmark = parseJSON(poiItem);
				if(unifiedLandmark == null){
					Log.e("UnifiedLMArrayGetParse", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				unifiedLandmarks.add(unifiedLandmark);
			}catch (JSONException e){
				Log.e("UnifiedLMArrayGetParse", e.toString());
				return null;
			}
		}
		return unifiedLandmarks;
	}
	
	public static UnifiedLandmark parseJSON(JSONObject poiItem){
		try{
			UnifiedLandmark unifiedLandmark = new UnifiedLandmark();

			unifiedLandmark.setId(poiItem.getLong(UnifiedLandmark.JSON_ID));
			unifiedLandmark.setName(poiItem.getString(UnifiedLandmark.JSON_NAME));
			unifiedLandmark.setCount(poiItem.getDouble(UnifiedLandmark.JSON_COUNT));
			unifiedLandmark.setVertical(poiItem.getDouble(UnifiedLandmark.JSON_VERTICAL));
			unifiedLandmark.setLayer(poiItem.getDouble(UnifiedLandmark.JSON_LAYER));
			unifiedLandmark.setOpening(poiItem.getDouble(UnifiedLandmark.JSON_OPENING));
			unifiedLandmark.setCloseIndicate(poiItem.getDouble(UnifiedLandmark.JSON_CLOSE_INDICATE));
			unifiedLandmark.setFireDoorIndicate(poiItem.getDouble(UnifiedLandmark.JSON_FIRE_DOOR_INDICATE));
			unifiedLandmark.setHallwayIndicate(poiItem.getBoolean(UnifiedLandmark.JSON_HALLWAY_INDICATE));
			unifiedLandmark.setBuildingId(poiItem.getLong(UnifiedLandmark.JSON_BUILDING_ID));
			unifiedLandmark.setFloor(poiItem.getInt(UnifiedLandmark.JSON_FLOOR));
			unifiedLandmark.setDetailedName(poiItem.getString(UnifiedLandmark.JSON_DETAILED_NAME));
			unifiedLandmark.setShortwall(poiItem.getLong(UnifiedLandmark.JSON_SHORTWALL));
			unifiedLandmark.setAltitude(poiItem.getLong(UnifiedLandmark.JSON_ALTITUDE));
			unifiedLandmark.setTexture(poiItem.getString(UnifiedLandmark.JSON_TEXTURE));
			unifiedLandmark.setEnvironment(poiItem.getString(UnifiedLandmark.JSON_ENVIRONMENT));
			unifiedLandmark.setClockDirection(poiItem.getInt(UnifiedLandmark.JSON_CLOCK_DIRECTION));
			unifiedLandmark.setTravelByIndicate(poiItem.getInt(UnifiedLandmark.JSON_TRAVEL_BY_INDICATE));
			unifiedLandmark.setRfid(poiItem.getLong(UnifiedLandmark.JSON_RFID));
			unifiedLandmark.setLandmarkName(poiItem.getString(UnifiedLandmark.JSON_LANDMARK_NAME));
			unifiedLandmark.setLandmarkNumber(poiItem.getInt(UnifiedLandmark.JSON_LANDMARK_NUMBER));
			unifiedLandmark.setLocationString(poiItem.getString(UnifiedLandmark.JSON_LOCATION_STRING));
			unifiedLandmark.setLocation(poiItem.getString(UnifiedLandmark.JSON_LOCATION));
			unifiedLandmark.setIsDestination(poiItem.getBoolean(UnifiedLandmark.JSON_IS_DESTINATION));
			unifiedLandmark.setMultiLandmarkType(poiItem.getString(UnifiedLandmark.JSON_MULTI_LANDMARK_TYPE));
			unifiedLandmark.setTagLocation(poiItem.getString(UnifiedLandmark.JSON_TAG_LOCATION));
			unifiedLandmark.setTransferId(poiItem.getLong(UnifiedLandmark.JSON_TRANSFER_ID));
			unifiedLandmark.setLandmarkType(poiItem.getString(UnifiedLandmark.JSON_LANDMARK_TYPE));
			unifiedLandmark.setIsVirtualTag(poiItem.getBoolean(UnifiedLandmark.JSON_IS_VIRTUAL_TAG));

			return unifiedLandmark;
		}catch (JSONException e){
			Log.e("UnifiedLMGetParse", e.toString());
			return null;
		}
	}

    /*// For updating user information in the future
	// Create JSON Array Structure
	public static JSONArray packageUserUpdateArray(List<UnifiedLandmark> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (UnifiedLandmark userUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(UnifiedLandmark.JSON_ID, userUpdate.getId());
				jsonObj.put(UnifiedLandmark.JSON_NAME, userUpdate.getName());
                jsonObj.put(UnifiedLandmark.JSON_USERNAME, userUpdate.getUsername());
                jsonObj.put(UnifiedLandmark.JSON_USER_TYPE, userUpdate.getUserType());
				
				jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("PackageUserUpdate", e.toString());
			return null;
		}
	}*/
}
