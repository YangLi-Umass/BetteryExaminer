package edu.fiveglabs.percept.Models.TAO;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.fiveglabs.percept.BaseModels.LandmarkBase;

import android.util.Log;

public class Landmark extends LandmarkBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_NAME = "Name";
	public static final String JSON_COUNT = "Count";
	public static final String JSON_VERTICAL = "Vertical";
	public static final String JSON_LAYER = "Layer";
	public static final String JSON_OPENING = "Opening";
	public static final String JSON_CLOSE_INDICATE = "CloseIndicate";
	public static final String JSON_FIREDOOR_INDICATE = "FireDoorIndicate";
	public static final String JSON_HALLWAY_INDICATE = "HallwayIndicate";
	public static final String JSON_BUILDING_ID = "BuildingId";
	public static final String JSON_FLOOR = "Floor";
	public static final String JSON_DETAILED_NAME = "DetailedName";
	public static final String JSON_SHORTWALL = "Shortwall";
	public static final String JSON_ALTITUDE = "Altitude";
	public static final String JSON_TEXTURE = "Texture";
	public static final String JSON_ENVIRONMENT = "Environment";
	public static final String JSON_CLOCKDIRECTION = "ClockDirection";
	public static final String JSON_TRAVELBYINDICATE = "TravelByIndicate";
	
	private JSONObject json;
	
	
	public Landmark(){
		json = new JSONObject();
	}
	public Landmark packageObject(long _id, String _name, double _count, double _vertical, double _layer, double _opening
			, double _closeIndicate, double _firedoorIndicate, boolean _hallwayIndicate, long _buildingId, int _floor, 
			String _detailedname, long _shortwall, long _altitude, String _texture, String _environment, int _clockdirection,
			int _travelbyindicate){
		
		setId(_id);
		setName(_name);
		setCount(_count);
		setVertical(_vertical);
		setLayer(_layer);
		setOpening(_opening);
		setCloseIndicate(_closeIndicate);
		setFireDoorIndicate(_firedoorIndicate);
		setHallwayIndicate(_hallwayIndicate);
		setBuildingId(_buildingId);
		setFloor(_floor);
		setDetailedName(_detailedname);
		setShortwall(_shortwall);
		setAltitude(_altitude);
		setTexture(_texture);
		setEnvironment(_environment);
		setClockDirection(_clockdirection);
		setTravelByIndicate(_travelbyindicate);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(Landmark.JSON_ID, getId());
			this.json.put(Landmark.JSON_NAME, getName());
			this.json.put(Landmark.JSON_COUNT, getCount());
			this.json.put(Landmark.JSON_BUILDING_ID, getBuildingId());
			this.json.put(Landmark.JSON_CLOSE_INDICATE, getCloseIndicate());
			this.json.put(Landmark.JSON_FIREDOOR_INDICATE, getFireDoorIndicate());
			this.json.put(Landmark.JSON_HALLWAY_INDICATE, isHallwayIndicate());
			this.json.put(Landmark.JSON_LAYER, getLayer());
			this.json.put(Landmark.JSON_OPENING, getOpening());
			this.json.put(Landmark.JSON_VERTICAL, getVertical());
			this.json.put(Landmark.JSON_FLOOR, getFloor());
			this.json.put(Landmark.JSON_DETAILED_NAME, getDetailedName());
			this.json.put(Landmark.JSON_SHORTWALL, getShortwall());
			this.json.put(Landmark.JSON_ALTITUDE, getAltitude());
			this.json.put(Landmark.JSON_TEXTURE, getTexture());
			this.json.put(Landmark.JSON_ENVIRONMENT, getEnvironment());
			this.json.put(Landmark.JSON_CLOCKDIRECTION, getClockDirection());
			this.json.put(Landmark.JSON_TRAVELBYINDICATE, getTravelByIndicate());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of Landmark", e.toString());
			return null;
		}
	}
	
	public static List<Landmark> parseJSONArray(JSONArray jsonArray) {
		List<Landmark> buildingNodes = new ArrayList<Landmark>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				Landmark landmark = parseJSON(poiItem);
				if(landmark == null){
					Log.e("LandmarkHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				buildingNodes.add(landmark);
			}catch (JSONException e){
				Log.e("LandmarkHttpGetParseError", e.toString());
				return null;
			}
		}
		return buildingNodes;
	}
	
	public static Landmark parseJSON(JSONObject poiItem){
		try{
			Landmark buildingNode = new Landmark();
			buildingNode.setId(poiItem.getLong(Landmark.JSON_ID));
			buildingNode.setName(poiItem.getString(Landmark.JSON_NAME));
			buildingNode.setBuildingId(poiItem.getLong(Landmark.JSON_BUILDING_ID));
			buildingNode.setCloseIndicate(poiItem.getDouble(Landmark.JSON_CLOSE_INDICATE));
			buildingNode.setCount(poiItem.getDouble(Landmark.JSON_COUNT));
			buildingNode.setFireDoorIndicate(poiItem.getDouble(Landmark.JSON_FIREDOOR_INDICATE));
			buildingNode.setHallwayIndicate(poiItem.getBoolean(Landmark.JSON_HALLWAY_INDICATE));
			buildingNode.setLayer(poiItem.getDouble(Landmark.JSON_LAYER));
			buildingNode.setOpening(poiItem.getDouble(Landmark.JSON_OPENING));
			buildingNode.setVertical(poiItem.getDouble(Landmark.JSON_VERTICAL));
			buildingNode.setFloor(poiItem.getInt(Landmark.JSON_FLOOR));
			buildingNode.setDetailedName(poiItem.getString(Landmark.JSON_DETAILED_NAME));
			buildingNode.setShortwall(poiItem.getLong(JSON_SHORTWALL));
			buildingNode.setAltitude(poiItem.getLong(JSON_ALTITUDE));
			buildingNode.setTexture(poiItem.getString(JSON_TEXTURE));
			buildingNode.setEnvironment(poiItem.getString(JSON_ENVIRONMENT));
			buildingNode.setClockDirection(poiItem.getInt(JSON_CLOCKDIRECTION));
			buildingNode.setTravelByIndicate(poiItem.getInt(JSON_TRAVELBYINDICATE));
			return buildingNode;
		}catch (JSONException e){
			Log.e("LandmarkHttpGetParseError", e.toString());
			return null;
		}
	}
	
	
}
