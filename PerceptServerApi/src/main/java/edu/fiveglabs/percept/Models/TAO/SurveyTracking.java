package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.SurveyTrackingBase;

public class SurveyTracking extends SurveyTrackingBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_LOCATION = "Location";
	public static final String JSON_USER_ID = "UserId";
	public static final String JSON_SURVEY_ID = "SurveyId";
	public static final String JSON_TIMESTAMP = "Timestamp";

	private JSONObject json;

	public SurveyTracking(){
		json = new JSONObject();
	}
	
	public SurveyTracking packageObject(long _id, String _location, long _userId, long _surveyId, String _timestamp){
		
		setId(_id);
		setLocation(_location);
		setUserId(_userId);
		setSurveyId(_surveyId);
		setTimeStamp(_timestamp);

		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(SurveyTracking.JSON_ID, getId());
			this.json.put(SurveyTracking.JSON_LOCATION, getLocation());
			this.json.put(SurveyTracking.JSON_USER_ID, getUserId());
			this.json.put(SurveyTracking.JSON_SURVEY_ID, getSurveyId());
			this.json.put(SurveyTracking.JSON_TIMESTAMP, getTimeStamp());

			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("JsonPackageOfSurveyT", e.toString());
			return null;
		}
	}
	
	public static List<SurveyTracking> parseJSONArray(JSONArray jsonArray) {
		List<SurveyTracking> surveyTrackings = new ArrayList<SurveyTracking>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				SurveyTracking surveyTracking = parseJSON(poiItem);
				if(surveyTracking == null){
					Log.e("SurveyTArrayGetParse", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				surveyTrackings.add(surveyTracking);
			}catch (JSONException e){
				Log.e("SurveyTArrayGetParse", e.toString());
				return null;
			}
		}
		return surveyTrackings;
	}
	
	public static SurveyTracking parseJSON(JSONObject poiItem){
		try{
			SurveyTracking user = new SurveyTracking();
			user.setId(poiItem.getLong(SurveyTracking.JSON_ID));
			user.setLocation(poiItem.getString(SurveyTracking.JSON_LOCATION));
			user.setUserId(poiItem.getLong(SurveyTracking.JSON_USER_ID));
			user.setSurveyId(poiItem.getLong(SurveyTracking.JSON_SURVEY_ID));
			user.setTimeStamp(poiItem.getString(SurveyTracking.JSON_TIMESTAMP));
			return user;
		}catch (JSONException e){
			Log.e("SurveyTGetParse", e.toString());
			return null;
		}
	}

    // For updating surveyTracking information in the future
	// Create JSON Array Structure
	public static JSONArray packageSurveyTrackingUpdateArray(List<SurveyTracking> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (SurveyTracking surveyTracking : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(SurveyTracking.JSON_ID, surveyTracking.getId());
				jsonObj.put(SurveyTracking.JSON_LOCATION, surveyTracking.getLocation());
                jsonObj.put(SurveyTracking.JSON_USER_ID, surveyTracking.getUserId());
                jsonObj.put(SurveyTracking.JSON_SURVEY_ID, surveyTracking.getSurveyId());
                jsonObj.put(SurveyTracking.JSON_TIMESTAMP, surveyTracking.getTimeStamp());


                jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("PackageSurveyTUpdate", e.toString());
			return null;
		}
	}
}
