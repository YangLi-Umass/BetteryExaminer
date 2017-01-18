package edu.fiveglabs.percept.Models.TAO;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class UnitTest {
	public static final String JSON_KEY_ID = "Id";
	public static final String JSON_KEY_DESCRIPTION = "Description";
	public static final String JSON_KEY_CREATED_TIMESTAMP = "Created";
	


	private long id;
	private String description = "";
	private String createdDate = "";


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	private JSONObject json;
	
	//Converts Java Representation to Server Representation 
	public JSONObject packageJSON(){
		try{
			if(json == null){
				json = new JSONObject();
			}
			this.json.put(UnitTest.JSON_KEY_ID, getId());
			this.json.put(UnitTest.JSON_KEY_DESCRIPTION, getDescription());
			this.json.put(UnitTest.JSON_KEY_CREATED_TIMESTAMP, getCreatedDate());
			
			return this.json;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Error in JSON Package of UnitTest", e.toString());
			return null;
	
		}
	}
	
	//Converts Server Representation to Java Representation (This is the array)
	public static List<UnitTest> parseJSONArray(JSONArray jsonArray) {
		List<UnitTest> patientAssessments = new ArrayList<UnitTest>();
		for(int i=0; i < jsonArray.length(); i++){
			try{
				JSONObject poiItem;
				poiItem = jsonArray.getJSONObject(i);
				UnitTest model = parseJSON(poiItem);
				if(model == null){
					Log.e( "UnitTestHttpGetParseError", "error parsing a jsonObject: " + poiItem.toString());
					continue;
				}
				patientAssessments.add(model);
			}catch (JSONException e){
				Log.e("UnitTestHttpGetParseError", e.toString());
				return null;
			}
		}
		return patientAssessments;
	}
	
	//Converts Server Representation to Java Representation (This is the is the model)
	public static UnitTest parseJSON(JSONObject poiItem){
		try{
			UnitTest pa = new UnitTest();
			pa.setId(poiItem.getLong(UnitTest.JSON_KEY_ID));
			pa.setDescription(poiItem.getString(UnitTest.JSON_KEY_DESCRIPTION));
			pa.setCreatedDate(poiItem.getString(UnitTest.JSON_KEY_CREATED_TIMESTAMP));
			
	
			return pa;
		}catch (JSONException e){
			Log.e("UnitTestHttpGetParseError", e.toString());
			return null;
		}
	}
	
}
