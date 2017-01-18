package edu.fiveglabs.percept.Models.TAO;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NodeLink {
	public static final String JSON_ID = "Id";
	public static final String JSON_WEIGHT = "Weight";
	public static final String JSON_START_POINT = "StartPoint";
	public static final String JSON_END_POINT = "EndPoint";
	public static final String JSON_BUILDING_ID = "BuildingId";
	public static final String JSON_CLOCKDIRECTION = "ClockDirection";
	
	private long id;
	private int weight;
	private String startPoint;
	private String endPoint;
	private long buildingId;
	private int clockdirection;
	
	private JSONObject json;
	
	public NodeLink(){
		json = new JSONObject();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}
	
	public int getClockDirection() {
		return clockdirection;
	}
	public void setClockDirection(int clockdirection) {
		this.clockdirection = clockdirection;
	}
	public NodeLink packageObject(long _id, int _weight, String _startPoint, String _endPoint, 
			long _buildingId, int _clockDirection){
		
		setId(_id);
		setWeight(_weight);
		setStartPoint(_startPoint);
		setEndPoint(_endPoint);
		setBuildingId(_buildingId);
		setClockDirection(_clockDirection);
		
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(NodeLink.JSON_ID, getId());
			this.json.put(NodeLink.JSON_BUILDING_ID, getBuildingId());
			this.json.put(NodeLink.JSON_END_POINT, getEndPoint());
			this.json.put(NodeLink.JSON_START_POINT, getStartPoint());
			this.json.put(NodeLink.JSON_WEIGHT, getWeight());
			this.json.put(NodeLink.JSON_CLOCKDIRECTION, getClockDirection());
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of NodeLink", e.toString());
			return null;
		}
	}
	
	public static List<NodeLink> parseJSONArray(JSONArray jsonArray) {
		List<NodeLink> nodeLinks = new ArrayList<NodeLink>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				NodeLink landmark = parseJSON(poiItem);
				if(landmark == null){
					Log.e("NodeLinkHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				nodeLinks.add(landmark);
			}catch (JSONException e){
				Log.e("NodeLinkHttpGetParseError", e.toString());
				return null;
			}
		}
		return nodeLinks;
	}
	
	public static NodeLink parseJSON(JSONObject poiItem){
		try{
			NodeLink buildingNode = new NodeLink();
			buildingNode.setId(poiItem.getLong(NodeLink.JSON_ID));
			buildingNode.setBuildingId(poiItem.getLong(NodeLink.JSON_BUILDING_ID));
			buildingNode.setEndPoint(poiItem.getString(NodeLink.JSON_END_POINT));
			buildingNode.setStartPoint(poiItem.getString(NodeLink.JSON_START_POINT));
			buildingNode.setWeight(poiItem.getInt(NodeLink.JSON_WEIGHT));
			buildingNode.setClockDirection(poiItem.getInt(NodeLink.JSON_CLOCKDIRECTION));
			
			return buildingNode;
		}catch (JSONException e){
			Log.e("NodeLinkHttpGetParseError", e.toString());
			return null;
		}
	}
	
	// /Create JSON Array Structure
	public static JSONArray packageLocalizationUpdateArray(List<NodeLink> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (NodeLink landmarkUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(NodeLink.JSON_ID, landmarkUpdate.getId());
				jsonObj.put(NodeLink.JSON_BUILDING_ID, landmarkUpdate.getBuildingId());
				jsonObj.put(NodeLink.JSON_END_POINT, landmarkUpdate.getEndPoint());
				jsonObj.put(NodeLink.JSON_START_POINT, landmarkUpdate.getStartPoint());
				jsonObj.put(NodeLink.JSON_WEIGHT, landmarkUpdate.getWeight());
				jsonObj.put(NodeLink.JSON_CLOCKDIRECTION, landmarkUpdate.getClockDirection());
				
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
