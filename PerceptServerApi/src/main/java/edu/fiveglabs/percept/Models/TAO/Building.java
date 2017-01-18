package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.BuildingBase;

public class Building extends BuildingBase implements Serializable{
	public static final String JSON_ID = "Id";
	public static final String JSON_NAME = "Name";
	public static final String JSON_LOCATION_STRING = "LocationString";
	public static final String JSON_FLOORS = "Floors";
	private JSONObject json;
	
	public Building(){
		json = new JSONObject();
	}
	
	public Building packageObject(long _id, String _name, String _locationString, int _floors){
		
		setId(_id);
		setName(_name);
		setLocationString(_locationString);
		setFloor(_floors);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(Building.JSON_ID, getId());
			this.json.put(Building.JSON_LOCATION_STRING, getLocationString());
			this.json.put(Building.JSON_NAME, getName());
			this.json.put(Building.JSON_FLOORS,getFloor());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of Building", e.toString());
			return null;
		}
	}
	
	public static List<Building> parseJSONArray(JSONArray jsonArray) {
		List<Building> buildingNodes = new ArrayList<Building>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				Building buildingNode = parseJSON(poiItem);
				if(buildingNode == null){
					Log.e("BuildingHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				buildingNodes.add(buildingNode);
			}catch (JSONException e){
				Log.e("BuildingHttpGetParseError", e.toString());
				return null;
			}
		}
		return buildingNodes;
	}
	
	public static Building parseJSON(JSONObject poiItem){
		try{
			Building buildingNode = new Building();
			buildingNode.setId(poiItem.getLong(Building.JSON_ID));
			buildingNode.setName(poiItem.getString(Building.JSON_NAME));
			buildingNode.setLocationString(poiItem.getString(Building.JSON_LOCATION_STRING));
			buildingNode.setFloor(poiItem.getInt(Building.JSON_FLOORS));

			return buildingNode;
		}catch (JSONException e){
			Log.e("BuildingHttpGetParseError", e.toString());
			return null;
		}
	}
	
	// /Create JSON Array Structure
	public static JSONArray packageLocalizationUpdateArray(List<Building> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (Building localizationUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(Building.JSON_ID, localizationUpdate.getId());
				jsonObj.put(Building.JSON_LOCATION_STRING, localizationUpdate.getLocationString());
				jsonObj.put(Building.JSON_NAME, localizationUpdate.getName());
				jsonObj.put(Building.JSON_FLOORS,localizationUpdate.getFloor());

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
