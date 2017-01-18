package edu.fiveglabs.percept.Models.TAO;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class UnitTestReport {
	public static final String JSON_KEY_ID = "Id";
	public static final String JSON_KEY_SOURCE_LANDMARK_ID = "SourceLandmarkId";
	public static final String JSON_KEY_DESTINATION_LANDMARK_ID = "DestinationLandmarkId";
	public static final String JSON_KEY_BASE_DIRECTIONS = "BaseDirections";
	public static final String JSON_KEY_COMPARE_DIRECTIONS = "CompareDirections";
	public static final String JSON_KEY_SUCCESS = "Success";
	public static final String JSON_KEY_BUILDING_ID = "BuildingId";
	public static final String JSON_KEY_UNIT_TEST_ID = "UnitTestId";

	private long id;
	private long sourceLandmarkId;
	private long destinationLandmarkId;
	private String baseDirections;
	private String compareDirections;
	private boolean success;
	private long buildingId;
	private long unitTestId;
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceLandmarkId() {
		return sourceLandmarkId;
	}

	public void setSourceLandmarkId(long sourceLandmarkId) {
		this.sourceLandmarkId = sourceLandmarkId;
	}

	public long getDestinationLandmarkId() {
		return destinationLandmarkId;
	}

	public void setDestinationLandmarkId(long destinationLandmarkId) {
		this.destinationLandmarkId = destinationLandmarkId;
	}

	public String getBaseDirections() {
		return baseDirections;
	}

	public void setBaseDirections(String baseDirections) {
		this.baseDirections = baseDirections;
	}

	public String getCompareDirections() {
		return compareDirections;
	}

	public void setCompareDirections(String compareDirections) {
		this.compareDirections = compareDirections;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}

	public long getUnitTestId() {
		return unitTestId;
	}

	public void setUnitTestId(long unitTestId) {
		this.unitTestId = unitTestId;
	}

	private JSONObject json;
	
	//Converts Java Representation to Server Representation 
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(UnitTestReport.JSON_KEY_ID, getId());
			this.json.put(UnitTestReport.JSON_KEY_BASE_DIRECTIONS, getBaseDirections());
			this.json.put(UnitTestReport.JSON_KEY_BUILDING_ID, getBuildingId());
			this.json.put(UnitTestReport.JSON_KEY_COMPARE_DIRECTIONS, getCompareDirections());
			this.json.put(UnitTestReport.JSON_KEY_DESTINATION_LANDMARK_ID, getDestinationLandmarkId());
			this.json.put(UnitTestReport.JSON_KEY_SOURCE_LANDMARK_ID, getSourceLandmarkId());
			this.json.put(UnitTestReport.JSON_KEY_SUCCESS, isSuccess());
			this.json.put(UnitTestReport.JSON_KEY_UNIT_TEST_ID, getUnitTestId());
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of UnitTestReport", e.toString());
			return null;
	
		}
	}
	
	//Converts Server Representation to Java Representation (This is the array)
	public static List<UnitTestReport> parseJSONArray(JSONArray jsonArray) {
		List<UnitTestReport> patientAssessments = new ArrayList<UnitTestReport>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				UnitTestReport model = parseJSON(poiItem);
				if(model == null){
					Log.e( "UnitTestReportHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				patientAssessments.add(model);
			}catch (JSONException e){
				Log.e("UnitTestReportHttpGetParseError", e.toString());
				return null;
			}
		}
		return patientAssessments;
	}
	
	//Converts Server Representation to Java Representation (This is the is the model)
	public static UnitTestReport parseJSON(JSONObject poiItem){
		try{
			UnitTestReport pa = new UnitTestReport();
			pa.setId(poiItem.getLong(UnitTestReport.JSON_KEY_ID));
			pa.setBaseDirections(poiItem.getString(UnitTestReport.JSON_KEY_BASE_DIRECTIONS));
			pa.setBuildingId(poiItem.getLong(UnitTestReport.JSON_KEY_BUILDING_ID));
			pa.setCompareDirections(poiItem.getString(UnitTestReport.JSON_KEY_COMPARE_DIRECTIONS));
			pa.setDestinationLandmarkId(poiItem.getLong(UnitTestReport.JSON_KEY_DESTINATION_LANDMARK_ID));
			pa.setSourceLandmarkId(poiItem.getLong(UnitTestReport.JSON_KEY_SOURCE_LANDMARK_ID));
			pa.setSuccess(poiItem.getBoolean(UnitTestReport.JSON_KEY_SUCCESS));
			pa.setUnitTestId(poiItem.getLong(UnitTestReport.JSON_KEY_UNIT_TEST_ID));
			return pa;
		}catch (JSONException e){
			Log.e("UnitTestReportHttpGetParseError", e.toString());
			return null;
		}
	}
	
}
