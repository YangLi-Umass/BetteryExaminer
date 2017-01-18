package edu.fiveglabs.percept.Models.PERCEPT;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BuildingNode {
	public static final String JSON_ID = "Id";
	public static final String JSON_NAME = "Name";
	public static final String JSON_LOCATION_STRING = "LocationString";
	
	private long id;
	private String name;
	private String locationString;
	
	private JSONObject json;
	
	public BuildingNode(){
		json = new JSONObject();
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
	
	public BuildingNode packageObject(long _id, String _name, String _locationString){
		
		setId(_id);
		setName(_name);
		setLocationString(_locationString);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(BuildingNode.JSON_ID, getId());
			this.json.put(BuildingNode.JSON_LOCATION_STRING, getLocationString());
			this.json.put(BuildingNode.JSON_NAME, getName());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of BuildingNodes", e.toString());
			return null;
		}
	}
	
	public static List<BuildingNode> parseJSONArray(JSONArray jsonArray) {
		List<BuildingNode> buildingNodes = new ArrayList<BuildingNode>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				BuildingNode buildingNode = parseJSON(poiItem);
				if(buildingNode == null){
					Log.e("BuildingNodeHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				buildingNodes.add(buildingNode);
			}catch (JSONException e){
				Log.e("BuildingNodeHttpGetParseError", e.toString());
				return null;
			}
		}
		return buildingNodes;
	}
	
	public static BuildingNode parseJSON(JSONObject poiItem){
		try{
			BuildingNode buildingNode = new BuildingNode();
			buildingNode.setId(poiItem.getLong(BuildingNode.JSON_ID));
			buildingNode.setName(poiItem.getString(BuildingNode.JSON_NAME));
			buildingNode.setLocationString(poiItem.getString(BuildingNode.JSON_LOCATION_STRING));
			
			return buildingNode;
		}catch (JSONException e){
			Log.e("BuildingNodeHttpGetParseError", e.toString());
			return null;
		}
	}
	
	// /Create JSON Array Structure
	public static JSONArray packageLocalizationUpdateArray(List<BuildingNode> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (BuildingNode localizationUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(BuildingNode.JSON_ID, localizationUpdate.getId());
				jsonObj.put(BuildingNode.JSON_LOCATION_STRING, localizationUpdate.getLocationString());
				jsonObj.put(BuildingNode.JSON_NAME, localizationUpdate.getName());
				
				jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			// Log.e("Error in HTTP Er Array Update Post", e.toString());
			return null;
		}
	}
}
