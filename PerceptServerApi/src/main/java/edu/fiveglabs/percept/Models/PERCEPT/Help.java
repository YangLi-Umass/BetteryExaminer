package edu.fiveglabs.percept.Models.PERCEPT;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Help {
	public static final String JSON_ID = "Id";
	public static final String JSON_BUILDING_NODE_ID = "BuildingNodeId";
	public static final String JSON_VISUALLY_IMPAIRED_ID = "VisuallyImpairedUserId";
	public static final String JSON_GROUP_ID = "GroupId";
	public static final String JSON_RESPONSE = "Response";
	public static final String JSON_COMPLETED = "Completed";
	public static final String JSON_SESSION_ID = "Session";
	public static final String JSON_DESTINATION_NODE_ID ="DestinationNodeId";
	
	private long id;
	private long buildingNodeId;
	private long visuallyImpairedUserId;
	private long groupId;
	private String response;
	private boolean completed;
	private long session;
	private long destinationNodeId;
	
	private JSONObject json;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBuildingNodeId() {
		return buildingNodeId;
	}
	public void setBuildingNodeId(long HelpId) {
		this.buildingNodeId = HelpId;
	}
	public long getVisuallyImpairedUserId() {
		return visuallyImpairedUserId;
	}
	public void setVisuallyImpairedUserId(long visuallyImpairedUserId) {
		this.visuallyImpairedUserId = visuallyImpairedUserId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public long getSession() {
		return session;
	}
	public void setSession(long session) {
		this.session = session;
	}
	public long getDestinationNodeId() {
		return destinationNodeId;
	}
	public void setDestinationNodeId(long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}
	public Help packageObject(long _id, long _buildingNodeId, long _visuallyImpairedUserId, long _groupId, String _response, boolean _completed, long _session, long _destinationNodeId){
		
		setId(_id);
		setBuildingNodeId(_buildingNodeId);
		setVisuallyImpairedUserId(_visuallyImpairedUserId);
		setGroupId(_groupId);
		setResponse(_response);
		setCompleted(_completed);
		setSession(_session);
		setDestinationNodeId(_destinationNodeId);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(Help.JSON_ID, getId());
			this.json.put(Help.JSON_BUILDING_NODE_ID, getBuildingNodeId());
			this.json.put(Help.JSON_COMPLETED, isCompleted());
			this.json.put(Help.JSON_GROUP_ID, getGroupId());
			this.json.put(Help.JSON_RESPONSE, getResponse());
			this.json.put(Help.JSON_VISUALLY_IMPAIRED_ID, getVisuallyImpairedUserId());
			this.json.put(Help.JSON_SESSION_ID, getSession());
			this.json.put(Help.JSON_DESTINATION_NODE_ID, getDestinationNodeId());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of Helps", e.toString());
			return null;
		}
	}
	
	public static List<Help> parseJSONArray(JSONArray jsonArray) {
		List<Help> helps = new ArrayList<Help>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				Help help = parseJSON(poiItem);
				if(help == null){
					Log.e("HelpHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				helps.add(help);
			}catch (JSONException e){
				Log.e("HelpHttpGetParseError", e.toString());
				return null;
			}
		}
		return helps;
	}
	
	public static Help parseJSON(JSONObject poiItem){
		try{
			Help help = new Help();
			help.setId(poiItem.getLong(Help.JSON_ID));
			help.setBuildingNodeId(poiItem.getLong(Help.JSON_BUILDING_NODE_ID));
			help.setCompleted(poiItem.getBoolean(Help.JSON_COMPLETED));
			help.setGroupId(poiItem.getLong(Help.JSON_GROUP_ID));
			help.setResponse(poiItem.getString(Help.JSON_RESPONSE));
			help.setVisuallyImpairedUserId(poiItem.getLong(Help.JSON_VISUALLY_IMPAIRED_ID));
			help.setSession(poiItem.getLong(Help.JSON_SESSION_ID));
			help.setDestinationNodeId(poiItem.getLong(Help.JSON_DESTINATION_NODE_ID));
			return help;
		}catch (JSONException e){
			Log.e("HelpHttpGetParseError", e.toString());
			return null;
		}
	}
	
	// /Create JSON Array Structure
	public static JSONArray packageLocalizationUpdateArray(List<Help> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (Help helpUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(Help.JSON_ID, helpUpdate.getId());
				jsonObj.put(Help.JSON_BUILDING_NODE_ID, helpUpdate.getBuildingNodeId());
				jsonObj.put(Help.JSON_COMPLETED, helpUpdate.isCompleted());
				jsonObj.put(Help.JSON_GROUP_ID, helpUpdate.getGroupId());
				jsonObj.put(Help.JSON_RESPONSE, helpUpdate.getResponse());
				jsonObj.put(Help.JSON_VISUALLY_IMPAIRED_ID, helpUpdate.getVisuallyImpairedUserId());
				jsonObj.put(Help.JSON_SESSION_ID, helpUpdate.getSession());
				jsonObj.put(Help.JSON_DESTINATION_NODE_ID, helpUpdate.getDestinationNodeId());
				
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
