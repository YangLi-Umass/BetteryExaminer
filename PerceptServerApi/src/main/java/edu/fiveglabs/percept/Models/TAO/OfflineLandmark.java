package edu.fiveglabs.percept.Models.TAO;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.fiveglabs.percept.BaseModels.LandmarkNodeBase;
import edu.fiveglabs.percept.BaseModels.OfflineLandmarkBase;

import android.util.Log;

public class OfflineLandmark extends OfflineLandmarkBase {
	
	
	public static final String JSON_ID = "Id";
	public static final String JSON_RFID = "RFID";
	public static final String JSON_LANDMARK_NAME = "Name";
	public static final String JSON_DETAILED_NAME = "DetailedName";
	public static final String JSON_FLOOR = "Floor";
	public static final String JSON_BUILDING_ID = "BuildingId";
	public static final String JSON_LANDMARK_NUMBER = "LandmarkNumber";
	public static final String JSON_IS_DESTINATION = "IsDestination";
	public static final String JSON_MULTI_LANDMARK_TYPE = "MultiLandmarkType";
	public static final String JSON_TAG_LOCATION = "TagLocation";
	
	private JSONObject json;
	
	public OfflineLandmark(){
		json = new JSONObject();
	}
	
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(OfflineLandmark.JSON_ID, getId());
			this.json.put(OfflineLandmark.JSON_BUILDING_ID, getBuildingId());
			this.json.put(OfflineLandmark.JSON_LANDMARK_NAME, getName());
			this.json.put(OfflineLandmark.JSON_DETAILED_NAME, getDetailedName());
			this.json.put(OfflineLandmark.JSON_FLOOR, getFloor());
			this.json.put(OfflineLandmark.JSON_RFID, getRfid());
			this.json.put(OfflineLandmark.JSON_LANDMARK_NUMBER, getLandmarkId());
			this.json.put(OfflineLandmark.JSON_IS_DESTINATION, isDestination());
			this.json.put(OfflineLandmark.JSON_MULTI_LANDMARK_TYPE, getMultiLandmarkType());
			this.json.put(OfflineLandmark.JSON_TAG_LOCATION, getTagLocation());
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of OfflineLandmark", e.toString());
			return null;
		}
	}
	
	public static List<OfflineLandmark> parseJSONArray(JSONArray jsonArray) {
		List<OfflineLandmark> landmarkNodes = new ArrayList<OfflineLandmark>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				OfflineLandmark landmark = parseJSON(poiItem);
				if(landmark == null){
					Log.e("OfflineLandmarkHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				landmarkNodes.add(landmark);
			}catch (JSONException e){
				Log.e("OfflineLandmarkHttpGetParseError", e.toString());
				return null;
			}
		}
		return landmarkNodes;
	}
	
	public static OfflineLandmark parseJSON(JSONObject poiItem){
		try{
			OfflineLandmark buildingNode = new OfflineLandmark();
			buildingNode.setId(poiItem.getLong(OfflineLandmark.JSON_ID));
			buildingNode.setBuildingId(poiItem.getLong(OfflineLandmark.JSON_BUILDING_ID));
			buildingNode.setName(poiItem.getString(OfflineLandmark.JSON_LANDMARK_NAME));
			buildingNode.setFloor(poiItem.getInt(OfflineLandmark.JSON_FLOOR));
			buildingNode.setLandmarkId(poiItem.getLong(OfflineLandmark.JSON_LANDMARK_NUMBER));
			
			if(poiItem.isNull(OfflineLandmark.JSON_DETAILED_NAME)){
				buildingNode.setDetailedName("");
			}
			else{
				buildingNode.setDetailedName(poiItem.getString(OfflineLandmark.JSON_DETAILED_NAME));
			}
			
			if(poiItem.isNull(OfflineLandmark.JSON_MULTI_LANDMARK_TYPE)){
				buildingNode.setMultiLandmarkType(0);
			}
			else{
				buildingNode.setMultiLandmarkType(poiItem.getInt(JSON_MULTI_LANDMARK_TYPE));
			}
			
			if(poiItem.isNull(OfflineLandmark.JSON_TAG_LOCATION)){
				buildingNode.setTagLocation(0);
			}
			else{
				buildingNode.setTagLocation(poiItem.getInt(JSON_TAG_LOCATION));
			}
			
			buildingNode.setDestination(poiItem.getBoolean(JSON_IS_DESTINATION));
			buildingNode.setRfid(poiItem.getLong(OfflineLandmark.JSON_RFID));

			
			return buildingNode;
		}catch (JSONException e){
			Log.e("OfflineLandmarkHttpGetParseError", e.toString());
			return null;
		}
	}
}
