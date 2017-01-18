package edu.fiveglabs.percept.Models.TAO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.SurveyDataBase;

public class SurveyData extends SurveyDataBase {
	public static final String JSON_ID = "Id";
	public static final String JSON_TAG_ID = "TagId";
	public static final String JSON_TAG_TYPE = "TagType";
	public static final String JSON_TIMESTAMP = "Timestamp";
	public static final String JSON_USER_ID = "UserId";
	public static final String JSON_SURVEY_ID = "SurveyId";


	private JSONObject json;

	public SurveyData(){
		json = new JSONObject();
	}
	
	public SurveyData packageObject(long _id, long _tagId, int _tagType, String _timestamp, long _userId, long _surveyId){
		
		setId(_id);
		setTagId(_tagId);
		setTagType(_tagType);
		setTimeStamp(_timestamp);
		setUserId(_userId);
		setSurveyId(_surveyId);

		return this;
	}
	
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(SurveyData.JSON_ID, getId());
			this.json.put(SurveyData.JSON_TAG_ID, getTagId());
			this.json.put(SurveyData.JSON_TAG_TYPE, getTagType());
			this.json.put(SurveyData.JSON_TIMESTAMP, getTimeStamp());
			this.json.put(SurveyData.JSON_USER_ID, getUserId());
			this.json.put(SurveyData.JSON_SURVEY_ID, getSurveyId());

			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("JsonPackOfSurveyData", e.toString());
			return null;
		}
	}
	
	public static List<SurveyData> parseJSONArray(JSONArray jsonArray) {
		List<SurveyData> surveyDatas = new ArrayList<SurveyData>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				SurveyData surveyData = parseJSON(poiItem);
				if(surveyData == null){
					Log.e("SurveyDataArrayGetParse", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				surveyDatas.add(surveyData);
			}catch (JSONException e){
				Log.e("SurveyDataArrayGetParse", e.toString());
				return null;
			}
		}
		return surveyDatas;
	}
	
	public static SurveyData parseJSON(JSONObject poiItem){
		try{
			SurveyData surveyData = new SurveyData();
			surveyData.setId(poiItem.getLong(SurveyData.JSON_ID));
			surveyData.setTagId(poiItem.getLong(SurveyData.JSON_TAG_ID));
			surveyData.setTagType(poiItem.getInt(SurveyData.JSON_TAG_TYPE));
			surveyData.setTimeStamp(poiItem.getString(SurveyData.JSON_TIMESTAMP));
			surveyData.setUserId(poiItem.getLong(SurveyData.JSON_USER_ID));
			surveyData.setSurveyId(poiItem.getLong(SurveyData.JSON_SURVEY_ID));

			return surveyData;
		}catch (JSONException e){
			Log.e("SurveyDataGetParse", e.toString());
			return null;
		}
	}

    // For updating surveyData information in the future
	// Create JSON Array Structure
	public static JSONArray packageSurveyDataUpdateArray(List<SurveyData> lUpdates) {
		JSONArray jArray = new JSONArray();
		try {
			for (SurveyData surveyData : lUpdates) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(SurveyData.JSON_ID, surveyData.getId());
				jsonObj.put(SurveyData.JSON_TAG_ID, surveyData.getTagId());
				jsonObj.put(SurveyData.JSON_TAG_TYPE, surveyData.getTagType());
				jsonObj.put(SurveyData.JSON_TIMESTAMP, surveyData.getTimeStamp());
				jsonObj.put(SurveyData.JSON_USER_ID, surveyData.getUserId());
				jsonObj.put(SurveyData.JSON_SURVEY_ID, surveyData.getSurveyId());

				jArray.put(jsonObj);
			}
			return jArray;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("PackageSurveyDataUpdate", e.toString());
			return null;
		}
	}
}
