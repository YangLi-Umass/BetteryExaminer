package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.UserBase;

public class User extends UserBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_NAME = "Name";
	public static final String JSON_USERNAME = "Username";
	public static final String JSON_USER_TYPE = "UserType";

	private JSONObject json;

	public User(){
		json = new JSONObject();
	}
	
	public User packageObject(long _id, String _name, String _username, int _userType){
		
		setId(_id);
		setName(_name);
		setUsername(_username);
		setUserType(_userType);

		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(User.JSON_ID, getId());
			this.json.put(User.JSON_NAME, getName());
			this.json.put(User.JSON_USERNAME, getUsername());
			this.json.put(User.JSON_USER_TYPE, getUserType());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("JsonPackageOfUser", e.toString());
			return null;
		}
	}
	
	public static List<User> parseJSONArray(JSONArray jsonArray) {
		List<User> buildingNodes = new ArrayList<User>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				User buildingNode = parseJSON(poiItem);
				if(buildingNode == null){
					Log.e("UserArrayGetParse", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				buildingNodes.add(buildingNode);
			}catch (JSONException e){
				Log.e("UserArrayGetParse", e.toString());
				return null;
			}
		}
		return buildingNodes;
	}
	
	public static User parseJSON(JSONObject poiItem){
		try{
			User user = new User();
			user.setId(poiItem.getLong(User.JSON_ID));
			user.setName(poiItem.getString(User.JSON_NAME));
			user.setUsername(poiItem.getString(User.JSON_USERNAME));
			user.setUserType(poiItem.getInt(User.JSON_USER_TYPE));

			return user;
		}catch (JSONException e){
			Log.e("UserGetParse", e.toString());
			return null;
		}
	}

    // For updating user information in the future
	// Create JSON Array Structure
	public static JSONArray packageUserUpdateArray(List<User> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (User userUpdate : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(User.JSON_ID, userUpdate.getId());
				jsonObj.put(User.JSON_NAME, userUpdate.getName());
                jsonObj.put(User.JSON_USERNAME, userUpdate.getUsername());
                jsonObj.put(User.JSON_USER_TYPE, userUpdate.getUserType());
				
				jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("PackageUserUpdate", e.toString());
			return null;
		}
	}
}
