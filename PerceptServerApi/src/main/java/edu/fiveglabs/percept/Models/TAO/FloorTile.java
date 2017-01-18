package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.FloorTileBase;

public class FloorTile extends FloorTileBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_BUILDING_ID = "BuildingId";
	public static final String JSON_FLOOR = "Floor";
	public static final String JSON_TILE_URL = "TileUrl";
	public static final String JSON_DISPLAY_NAME = "DisplayName";
	private JSONObject json;

	public FloorTile(){
		json = new JSONObject();
	}
	
	public FloorTile packageObject(long _id, long _buildingId, int _floor, String _tileUrl, String _displayName){
		
		setId(_id);
		setBuildingId(_buildingId);
		setFloor(_floor);
		setTileUrl(_tileUrl);
		setDisplayName(_displayName);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(FloorTile.JSON_ID, getId());
			this.json.put(FloorTile.JSON_BUILDING_ID,getBuildingId());
			this.json.put(FloorTile.JSON_FLOOR,getFloor());
			this.json.put(FloorTile.JSON_TILE_URL,getTileUrl());
			this.json.put(FloorTile.JSON_DISPLAY_NAME,getDisplayName());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of FloorTile", e.toString());
			return null;
		}
	}
	
	public static List<FloorTile> parseJSONArray(JSONArray jsonArray) {
		List<FloorTile> buildingNodes = new ArrayList<FloorTile>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				FloorTile buildingNode = parseJSON(poiItem);
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
	
	public static FloorTile parseJSON(JSONObject poiItem){
		try{
			FloorTile buildingNode = new FloorTile();
			buildingNode.setId(poiItem.getLong(FloorTile.JSON_ID));
			buildingNode.setBuildingId(poiItem.getLong(FloorTile.JSON_BUILDING_ID));
			buildingNode.setFloor(poiItem.getInt(FloorTile.JSON_FLOOR));
			buildingNode.setTileUrl(poiItem.getString(FloorTile.JSON_TILE_URL));
			buildingNode.setDisplayName(poiItem.getString(FloorTile.JSON_DISPLAY_NAME));

			return buildingNode;
		}catch (JSONException e){
			Log.e("FloorTileHttpGetParseError", e.toString());
			return null;
		}
	}
	
	/*// /Create JSON Array Structure
	public static JSONArray packageLocalizationUpdateArray(List<FloorTile> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (FloorTile localizationUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(FloorTile.JSON_ID, localizationUpdate.getId());
				jsonObj.put(FloorTile.JSON_LOCATION_STRING, localizationUpdate.getLocationString());
				jsonObj.put(FloorTile.JSON_NAME, localizationUpdate.getName());
				jsonObj.put(FloorTile.JSON_FLOOR,localizationUpdate.getFloor());

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
