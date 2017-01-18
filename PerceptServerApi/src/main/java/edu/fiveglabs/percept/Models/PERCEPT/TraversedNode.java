package edu.fiveglabs.percept.Models.PERCEPT;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class TraversedNode {
	public static final String JSON_ID = "Id";
	public static final String JSON_BUILDING_NODE_ID = "BuildingNodeId";
	public static final String JSON_VISUALLY_IMPAIRED_ID = "VisuallyImpairedUserId";
	public static final String JSON_GROUP_ID = "GroupId";
	public static final String JSON_SESSION_ID = "Session";
	public static final String JSON_TIMESTAMP = "Timestamp";
	
	private long id;
	private long buildingNodeId;
	private long visuallyImpairedUserId;
	private long groupId;
	private long session;
	private String timestamp;
	
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
	public long getSession() {
		return session;
	}
	public void setSession(long session) {
		this.session = session;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public TraversedNode packageObject(long _id, long _buildingNodeId, long _visuallyImpairedUserId, long _groupId, long _session, String _timestamp){
		
		setId(_id);
		setBuildingNodeId(_buildingNodeId);
		setVisuallyImpairedUserId(_visuallyImpairedUserId);
		setGroupId(_groupId);
		setSession(_session);
		setTimestamp(_timestamp);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(TraversedNode.JSON_ID, getId());
			this.json.put(TraversedNode.JSON_BUILDING_NODE_ID, getBuildingNodeId());
			this.json.put(TraversedNode.JSON_GROUP_ID, getGroupId());
			this.json.put(TraversedNode.JSON_VISUALLY_IMPAIRED_ID, getVisuallyImpairedUserId());
			this.json.put(TraversedNode.JSON_SESSION_ID, getSession());
			this.json.put(TraversedNode.JSON_TIMESTAMP, getTimestamp());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of TraversedNodes", e.toString());
			return null;
		}
	}
	
	public static List<TraversedNode> parseJSONArray(JSONArray jsonArray) {
		List<TraversedNode> traversedNodes = new ArrayList<TraversedNode>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				TraversedNode traversedNode = parseJSON(poiItem);
				if(traversedNode == null){
					Log.e("TraversedNodeHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				traversedNodes.add(traversedNode);
			}catch (JSONException e){
				Log.e("TraversedNodeHttpGetParseError", e.toString());
				return null;
			}
		}
		return traversedNodes;
	}
	
	public static TraversedNode parseJSON(JSONObject poiItem){
		try{
			TraversedNode traversedNode = new TraversedNode();
			traversedNode.setId(poiItem.getLong(TraversedNode.JSON_ID));
			traversedNode.setBuildingNodeId(poiItem.getLong(TraversedNode.JSON_BUILDING_NODE_ID));
			traversedNode.setGroupId(poiItem.getLong(TraversedNode.JSON_GROUP_ID));
			traversedNode.setVisuallyImpairedUserId(poiItem.getLong(TraversedNode.JSON_VISUALLY_IMPAIRED_ID));
			traversedNode.setSession(poiItem.getLong(TraversedNode.JSON_SESSION_ID));
			traversedNode.setTimestamp(poiItem.getString(TraversedNode.JSON_TIMESTAMP));
			return traversedNode;
		}catch (JSONException e){
			Log.e("TraversedNodeHttpGetParseError", e.toString());
			return null;
		}
	}
	
	// /Create JSON Array Structure
	public static JSONArray packageLocalizationUpdateArray(List<TraversedNode> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (TraversedNode traversedNodeUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(TraversedNode.JSON_ID, traversedNodeUpdate.getId());
				jsonObj.put(TraversedNode.JSON_BUILDING_NODE_ID, traversedNodeUpdate.getBuildingNodeId());
				jsonObj.put(TraversedNode.JSON_GROUP_ID, traversedNodeUpdate.getGroupId());
				jsonObj.put(TraversedNode.JSON_VISUALLY_IMPAIRED_ID, traversedNodeUpdate.getVisuallyImpairedUserId());
				jsonObj.put(TraversedNode.JSON_SESSION_ID, traversedNodeUpdate.getSession());
				jsonObj.put(TraversedNode.JSON_TIMESTAMP, traversedNodeUpdate.getTimestamp());
				
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
