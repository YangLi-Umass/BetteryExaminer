package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.LandmarkNodeBase;

public class LandmarkNode extends LandmarkNodeBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_RFID = "RFID";
	public static final String JSON_LANDMARK_NAME = "LandmarkName";
	public static final String JSON_LANDMARK_NUMBER = "LandmarkNumber";
	public static final String JSON_LOCATION_STRING = "LocationString";
	public static final String JSON_BUILDING_ID = "BuildingId";
	public static final String JSON_IS_DESTINATION = "IsDestination";
	public static final String JSON_MULTILANDMARK_TYPE = "MultiLandmarkType";
	public static final String JSON_BLE = "BLE";
	public static final String JSON_LOCATION = "Location";

	private JSONObject json;
	
	public LandmarkNode(){
		json = new JSONObject();
	}
	public LandmarkNode packageObject(long _id, long _rfid, String _landmarkName, int _landmarkNumber, 
			String _locationString, long _buildingId, boolean _isDestination, int _multiLandmarkType,
									  String _ble, String _location){
		
		setId(_id);
		setRfid(_rfid);
		setLandmarkName(_landmarkName);
		setLandmarkNumber(_landmarkNumber);
		setLocationString(_locationString);
		setBuildingId(_buildingId);
		setDestination(_isDestination);
		setMultiLandmarkType(_multiLandmarkType);
		setBle(_ble);
		setLocation(_location);
		
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(LandmarkNode.JSON_ID, getId());
			this.json.put(LandmarkNode.JSON_BUILDING_ID, getBuildingId());
			this.json.put(LandmarkNode.JSON_LANDMARK_NAME, getLandmarkName());
			this.json.put(LandmarkNode.JSON_LANDMARK_NUMBER, getLandmarkNumber());
			this.json.put(LandmarkNode.JSON_LOCATION_STRING, getLocationString());
			this.json.put(LandmarkNode.JSON_RFID, getRfid());
			this.json.put(LandmarkNode.JSON_IS_DESTINATION, isDestination());
			this.json.put(LandmarkNode.JSON_MULTILANDMARK_TYPE, getMultiLandmarkType());
			this.json.put(LandmarkNode.JSON_BLE, getBle());
			this.json.put(LandmarkNode.JSON_LOCATION, getLocation());
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of LandmarkNode", e.toString());
			return null;
		}
	}
	
	public static List<LandmarkNode> parseJSONArray(JSONArray jsonArray) {
		List<LandmarkNode> landmarkNodes = new ArrayList<LandmarkNode>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				LandmarkNode landmark = parseJSON(poiItem);
				if(landmark == null){
					Log.e("LandmarkNodeHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				landmarkNodes.add(landmark);
			}catch (JSONException e){
				Log.e("LandmarkNodeHttpGetParseError", e.toString());
				return null;
			}
		}
		return landmarkNodes;
	}
	
	public static LandmarkNode parseJSON(JSONObject poiItem){
		try{
			LandmarkNode buildingNode = new LandmarkNode();
			buildingNode.setId(poiItem.getLong(LandmarkNode.JSON_ID));
			buildingNode.setBuildingId(poiItem.getLong(LandmarkNode.JSON_BUILDING_ID));
			buildingNode.setLandmarkName(poiItem.getString(LandmarkNode.JSON_LANDMARK_NAME));
			buildingNode.setLandmarkNumber(poiItem.getInt(LandmarkNode.JSON_LANDMARK_NUMBER));
			buildingNode.setLocationString(poiItem.getString(LandmarkNode.JSON_LOCATION_STRING));
			buildingNode.setRfid(poiItem.getLong(LandmarkNode.JSON_RFID));
			buildingNode.setDestination(poiItem.getBoolean(LandmarkNode.JSON_IS_DESTINATION));
			buildingNode.setMultiLandmarkType(poiItem.getInt(LandmarkNode.JSON_MULTILANDMARK_TYPE));
			buildingNode.setBle(poiItem.getString(LandmarkNode.JSON_BLE));
			buildingNode.setLocation(poiItem.getString(LandmarkNode.JSON_LOCATION));
			return buildingNode;
		}catch (JSONException e){
			Log.e("LandmarkNodeHttpGetParseError", e.toString());
			return null;
		}
	}
	
	// /Create JSON Array Structure
	/*public static JSONArray packageLocalizationUpdateArray(List<LandmarkNode> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (LandmarkNode landmarkUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(LandmarkNode.JSON_ID, landmarkUpdate.getId());
				jsonObj.put(LandmarkNode.JSON_BUILDING_ID, landmarkUpdate.getBuildingId());
				jsonObj.put(LandmarkNode.JSON_LANDMARK_NAME, landmarkUpdate.getLandmarkName());
				jsonObj.put(LandmarkNode.JSON_LANDMARK_NUMBER, landmarkUpdate.getLandmarkNumber());
				jsonObj.put(LandmarkNode.JSON_LOCATION_STRING, landmarkUpdate.getLocationString());
				jsonObj.put(LandmarkNode.JSON_RFID, landmarkUpdate.getRfid());
				
				jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			// Log.e("Error in HTTP Er Array Update Post", e.toString());
			return null;
		}
	}*/
}
