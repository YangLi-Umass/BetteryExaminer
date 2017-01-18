package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.SurveyBase;


public class Survey extends SurveyBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_START_TIME = "StartTime";
	public static final String JSON_BUILDING_ID = "BuildingId";

	private JSONObject json;

	public Survey(){
		json = new JSONObject();
	}
	
	public Survey packageObject(long _id, String _startTime, long _buildingId){
		
		setId(_id);
		setStartTime(_startTime);
		setBuildingId(_buildingId);
		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(Survey.JSON_ID, getId());
			this.json.put(Survey.JSON_START_TIME, getStartTime());
			this.json.put(Survey.JSON_BUILDING_ID, getBuildingId());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("JsonPackageOfSurvey", e.toString());
			return null;
		}
	}
	
	public static List<Survey> parseJSONArray(JSONArray jsonArray) {
		List<Survey> surveys = new ArrayList<Survey>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				Survey survey = parseJSON(poiItem);
				if(survey == null){
					Log.e("SurveyArrayGetParse", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				surveys.add(survey);
			}catch (JSONException e){
				Log.e("SurveyArrayGetParse", e.toString());
				return null;
			}
		}
		return surveys;
	}
	
	public static Survey parseJSON(JSONObject poiItem){
		try{
			Survey survey = new Survey();
			survey.setId(poiItem.getLong(Survey.JSON_ID));
			survey.setBuildingId(poiItem.getLong(Survey.JSON_BUILDING_ID));
			survey.setStartTime(poiItem.getString(Survey.JSON_START_TIME));
			
			return survey;
		}catch (JSONException e){
			Log.e("SurveyGetParse", e.toString());
			return null;
		}
	}

	// *******No use for Survey. Since we will never change survey result.
	// Create JSON Array Structure
	public static JSONArray packageSurveyUpdateArray(List<Survey> surveyList) {
		JSONArray jArray = new JSONArray();
		try {
			for (Survey survey : surveyList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(Survey.JSON_ID, survey.getId());
				jsonObj.put(Survey.JSON_BUILDING_ID, survey.getBuildingId());
				jsonObj.put(Survey.JSON_START_TIME, survey.getStartTime());
				
				jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("PackageSurveyUpdate", e.toString());
			return null;
		}
	}
}
