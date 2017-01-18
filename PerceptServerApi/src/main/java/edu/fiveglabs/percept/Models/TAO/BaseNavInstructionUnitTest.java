package edu.fiveglabs.percept.Models.TAO;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.fiveglabs.percept.BaseModels.BaseNavInstructionUnitTestBase;
import android.util.Log;


public class BaseNavInstructionUnitTest extends BaseNavInstructionUnitTestBase {
	public static final String JSON_KEY_ID = "Id";
	public static final String JSON_KEY_SOURCE_LANDMARK_ID = "SourceLandmarkId";
	public static final String JSON_KEY_DESTINATION_LANDMARK_ID = "DestinationLandmarkId";
	public static final String JSON_KEY_BASE_DIRECTIONS = "BaseDirections";
	public static final String JSON_KEY_BUILDING_ID = "BuildingId";
	public static final String JSON_DISTANCE = "Distance";
	public static final String JSON_INSTRUCTIONTYPE = "InstructionType";

	private JSONObject json;
	
	//Converts Java Representation to Server Representation 
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(BaseNavInstructionUnitTest.JSON_KEY_ID, getId());
			this.json.put(BaseNavInstructionUnitTest.JSON_KEY_BASE_DIRECTIONS, getBaseDirections());
			this.json.put(BaseNavInstructionUnitTest.JSON_KEY_BUILDING_ID, getBuildingId());
			this.json.put(BaseNavInstructionUnitTest.JSON_KEY_DESTINATION_LANDMARK_ID, getDestinationLandmarkId());
			this.json.put(BaseNavInstructionUnitTest.JSON_KEY_SOURCE_LANDMARK_ID, getSourceLandmarkId());
			this.json.put(BaseNavInstructionUnitTest.JSON_DISTANCE, getDistance());
			this.json.put(BaseNavInstructionUnitTest.JSON_INSTRUCTIONTYPE, getInstructionType());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of BaseNavInstructionUnitTest", e.toString());
			return null;
	
		}
	}
	
	//Converts Server Representation to Java Representation (This is the array)
	public static List<BaseNavInstructionUnitTest> parseJSONArray(JSONArray jsonArray) {
		List<BaseNavInstructionUnitTest> patientAssessments = new ArrayList<BaseNavInstructionUnitTest>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				BaseNavInstructionUnitTest model = parseJSON(poiItem);
				if(model == null){
					Log.e( "BaseNavInstructionUnitTestHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				patientAssessments.add(model);
			}catch (JSONException e){
				Log.e("BaseNavInstructionUnitTesttHttpGetParseError", e.toString());
				return null;
			}
		}
		return patientAssessments;
	}
	
	//Converts Server Representation to Java Representation (This is the is the model)
	public static BaseNavInstructionUnitTest parseJSON(JSONObject poiItem){
		try{
			BaseNavInstructionUnitTest pa = new BaseNavInstructionUnitTest();
			pa.setId(poiItem.getLong(BaseNavInstructionUnitTest.JSON_KEY_ID));
			pa.setBaseDirections(poiItem.getString(BaseNavInstructionUnitTest.JSON_KEY_BASE_DIRECTIONS));
			pa.setBuildingId(poiItem.getLong(BaseNavInstructionUnitTest.JSON_KEY_BUILDING_ID));
			pa.setDestinationLandmarkId(poiItem.getLong(BaseNavInstructionUnitTest.JSON_KEY_DESTINATION_LANDMARK_ID));
			pa.setSourceLandmarkId(poiItem.getLong(BaseNavInstructionUnitTest.JSON_KEY_SOURCE_LANDMARK_ID));
			pa.setDistance(poiItem.getLong(BaseNavInstructionUnitTest.JSON_DISTANCE));
			pa.setInstructionType(poiItem.getInt(BaseNavInstructionUnitTest.JSON_INSTRUCTIONTYPE));
			
			return pa;
		}catch (JSONException e){
			Log.e("BaseNavInstructionUnitTestHttpGetParseError", e.toString());
			return null;
		}
	}
	
}
