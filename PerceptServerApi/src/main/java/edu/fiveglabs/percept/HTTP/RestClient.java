package edu.fiveglabs.percept.HTTP;

import android.text.format.Time;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import edu.fiveglabs.percept.Models.PERCEPT.BuildingNode;
import edu.fiveglabs.percept.Models.PERCEPT.Help;
import edu.fiveglabs.percept.Models.PERCEPT.TraversedNode;
import edu.fiveglabs.percept.Models.TAO.BaseNavInstructionUnitTest;
import edu.fiveglabs.percept.Models.TAO.Building;
import edu.fiveglabs.percept.Models.TAO.FloorTile;
import edu.fiveglabs.percept.Models.TAO.Landmark;
import edu.fiveglabs.percept.Models.TAO.LandmarkNode;
import edu.fiveglabs.percept.Models.TAO.NodeLink;
import edu.fiveglabs.percept.Models.TAO.OfflineLandmark;
import edu.fiveglabs.percept.Models.TAO.Survey;
import edu.fiveglabs.percept.Models.TAO.SurveyData;
import edu.fiveglabs.percept.Models.TAO.SurveyTracking;
import edu.fiveglabs.percept.Models.TAO.UnifiedLandmark;
import edu.fiveglabs.percept.Models.TAO.UnitTest;
import edu.fiveglabs.percept.Models.TAO.UnitTestReport;
import edu.fiveglabs.percept.Models.TAO.User;


public class RestClient {

	private static String URL_PRE = "http://perceptdata.azurewebsites.net/api/";
	private final static int FIRST_VALUE = 0;
	
	private final String URL_HELP = "Help/";
	private final String URL_TRAVERSED_NODE ="TraversedNode/";
	private final String URL_BUILDING_NODE = "BuildingNode/";
	private final static String URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST = "BaseNavInstructionUnitTest/";
	private final static  String URL_UNIT_TEST_REPORT = "UnitTestReport/";
	private final static String URL_UNIT_TEST = "UnitTest/";
	
	private final String URL_BUILDING = "Building/";
	private final static String URL_LANDMARK = "Landmark/";
	private final static String URL_LANDMARK_NODE = "LandmarkNode/";
	private final static String URL_NODE_LINK = "NodeLink/";

	private final static String URL_SURVEY = "Survey/";
	private final static String URL_USER = "User/";
	private final static String URL_SURVEY_TRACKING = "SurveyTracking/";
	private final static String URL_SURVEY_DATA = "SurveyData/";
	private final static String URL_UNIFIED_LANDMARK = "UnifiedLandmark/";
	private final static String URL_FLOOR_TILE = "FloorTile/";

	public final static int HTTP_RESPONSE_CODE_CREATED = 201;
	public final static int HTTP_RESPONSE_CODE_OK = 200;
	public final static int HTTP_RESPONSE_NO_CONTENT = 204;


	//Create a Timestamp for current HTTP Message
	//THIS IS STATIC BECAUSE IT CAN BE ACCESSED GLOBALLY WITHOUT CREATING AN INSTANCE OF IT
	//THIS IS BECAUSE IT IS AUTONOMOUS OF THE REST OF THE CLASS
	//Format should follow:  2011-11-24 02:15:13.473
	//
	public RestClient(String serverConnection){
		this.URL_PRE = serverConnection;
	}

	public RestClient(){

	}

	public static String getTimeStamp(){
		Time dtNow = new Time();
		dtNow.setToNow();
		String year = ((Integer)(dtNow.year)).toString();
		String month = dtNow.toString().substring(4, 6);
		String day = ((Integer)(dtNow.monthDay)).toString();
		String hour = ((Integer)(dtNow.hour)).toString();
		String minute = ((Integer)(dtNow.minute)).toString();
		String second = ((Integer)(dtNow.second)).toString();
		String milisecond = "000";

		if(month.length()<2){
			month = "0" + month;
		}
		if(day.length()<2){
			day = "0" + day;
		}
		if(hour.length()<2){
			hour = "0" + hour;
		}
		if(minute.length()<2){
			minute = "0" + minute;
		}
		if(second.length()<2){
			second = "0" + second;
		}

		String timeStamp = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + "." + milisecond;

		return timeStamp;
	}


	///POST
	//// POST POST POST
	///POST

	//Send FloorTile
	public void sendFloorTile(FloorTile floorTile){
		JSONObject jObj = floorTile.packageJSON();

		String url = URL_PRE + URL_FLOOR_TILE;
		submitHttpPost(url, jObj);
	}

	//Send SurveyData
	public void sendSurveyData(SurveyData surveyData){
		JSONObject jObj = surveyData.packageJSON();

		String url = URL_PRE + URL_SURVEY_DATA;
		submitHttpPost(url, jObj);
	}

	//Send SurveyTracking
	public void sendSurveyTracking(SurveyTracking surveyTracking){
		JSONObject jObj = surveyTracking.packageJSON();

		String url = URL_PRE + URL_SURVEY_TRACKING;
		submitHttpPost(url, jObj);
	}

	//Send User
	public void sendUser(User user){
		JSONObject jObj = user.packageJSON();

		String url = URL_PRE + URL_USER;
		submitHttpPost(url, jObj);
	}

	//Send Survey, return the id assigned by Server
	public long sendSurvey(Survey survey){
		JSONObject jObj = survey.packageJSON();

		String url = URL_PRE + URL_SURVEY;
		String result = submitHttpPost(url, jObj);
		String[] separated1 = result.split(",");
		String[] separated2 = separated1[0].split(":");
		if(separated2[1] != null){
			return Long.parseLong(separated2[1]);
		}
		return 0;

	}

	//Send Help
	public void sendHelp(Help help){
		JSONObject jObj = help.packageJSON();

		String url = URL_PRE + URL_HELP;
		submitHttpPost(url, jObj);
	}

	//Send TraversedNode
	public void sendTraversedNode(TraversedNode traversedNode){
		JSONObject jObj = traversedNode.packageJSON();

		String url = URL_PRE + URL_TRAVERSED_NODE;
		submitHttpPost(url, jObj);
	}
	
	//Send Building
	public void sendBuilding(Building building){
		JSONObject jObj = building.packageJSON();

		String url = URL_PRE + URL_BUILDING;
		submitHttpPost(url, jObj);
	}
	
	//Send Landmark
	public static void sendLandmark(Landmark landmark){
		JSONObject jObj = landmark.packageJSON();

		Landmark example = new Landmark();
		String url = URL_PRE + URL_LANDMARK;
		submitHttpPost(url, jObj);
	}
	
	//Send LandmarkNode
	public static void sendLandmarkNode(LandmarkNode landmarkNode){
		JSONObject jObj = landmarkNode.packageJSON();

		String url = URL_PRE + URL_LANDMARK_NODE;
		submitHttpPost(url, jObj);
	}
	
	//Send NodeLink
	public static void sendNodeLink(NodeLink nodeLink){
		JSONObject jObj = nodeLink.packageJSON();

		String url = URL_PRE + URL_NODE_LINK;
		submitHttpPost(url, jObj);
	}
	
	//Send BaseNavInstr
	public static void sendBaseNavInstructions(BaseNavInstructionUnitTest baseInstruction){
		JSONObject jObj = baseInstruction.packageJSON();

		String url = URL_PRE + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		submitHttpPost(url, jObj);
	}
	
	//Send UnitTestReport
	public static UnitTestReport sendUnitTestReport(UnitTestReport unitTestReport){
		JSONObject jObj = unitTestReport.packageJSON();

		String url = URL_PRE + URL_UNIT_TEST_REPORT;
		String httpResponse = submitHttpPost(url, jObj);
		
		JSONObject jsonObject = stringToJsonObject(httpResponse);
		if(jsonObject == null){
			return null;
		}
		UnitTestReport unitTest = UnitTestReport.parseJSON(jsonObject);
		return unitTest;
	}
	
	//Send UnitTest
	public static UnitTest sendUnitTest(UnitTest unitTest){
		JSONObject jObj = unitTest.packageJSON();

		String url = URL_PRE + URL_UNIT_TEST;
		String httpResponse = submitHttpPost(url, jObj);
		
		JSONObject jsonObject = stringToJsonObject(httpResponse);
		if(jsonObject == null){
			return null;
		}
		UnitTest unitTestObj = UnitTest.parseJSON(jsonObject);
		return unitTestObj;
	}


	private static String submitHttpPost(String url, JSONObject jObj){
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;

		JSONObject json;
		String result = "";
		try{
			json = jObj;

			HttpPost post = new HttpPost(url);

			StringEntity se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			response = client.execute(post);
			/*check response to see if success*/

			if(response!=null){
				if (response.getStatusLine().getStatusCode() != HTTP_RESPONSE_CODE_CREATED) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				InputStream in = response.getEntity().getContent();

				result = convertStreamToString(in);
				Log.i("PostResult",result);
				in.close();
			}

		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("httpPost", e.toString());
		}
		return result;
	}


	////////////////
	// PUT  PUT  PUT  PUT
	//   PUT  PUT   PUT
	/////////////
	//A put is an update method (updates existing row in database)

	//Update FloorTile
	public void updateFloorTile(FloorTile floorTile){
		JSONObject jObj = floorTile.packageJSON();

		String url = URL_PRE + URL_FLOOR_TILE;
		submitHttpPut(url, jObj);
	}

	//Update User
	public void updateUser(User user){
		JSONObject jObj = user.packageJSON();

		String url = URL_PRE + URL_USER;
		submitHttpPut(url, jObj);
	}
	
	//Update Help
	public void updateHelp(Help help){
		JSONObject jObj = help.packageJSON();

		String url = URL_PRE + URL_HELP;
		submitHttpPut(url, jObj);
	}

	//Update Building
	public void updateBuilding(Building building){
		JSONObject jObj = building.packageJSON();

		String url = URL_PRE + URL_BUILDING;
		submitHttpPut(url, jObj);
	}
	
	//Update Landmark
	public static void updateLandmark(Landmark landmark){
		JSONObject jObj = landmark.packageJSON();

		String url = URL_PRE + URL_LANDMARK;
		submitHttpPut(url, jObj);
	}
	
	
		
	//Update LandmarkNode
	public static void updateLandmarkNode(LandmarkNode landmarkNode){
		JSONObject jObj = landmarkNode.packageJSON();

		String url = URL_PRE + URL_LANDMARK_NODE;
		submitHttpPut(url, jObj);
	}
	
	//
	//Update Offine LandmarkNode
		public static void updateOfflineLandmarkNode(OfflineLandmark offlinelandmark){
			JSONObject jObj = offlinelandmark.packageJSON();

			String url = URL_PRE + URL_LANDMARK_NODE + "forOffline";
			submitHttpPut(url, jObj);
		}
	
	//Update NodeLink
	public static void updateNodeLink(NodeLink nodeLink){
		JSONObject jObj = nodeLink.packageJSON();

		String url = URL_PRE + URL_NODE_LINK;
		submitHttpPut(url, jObj);
	}
	
	//Update UnitTest
	public static void updateUnitTest(UnitTest unitTest){
		JSONObject jObj = unitTest.packageJSON();

		String url = URL_PRE + URL_UNIT_TEST;
		submitHttpPut(url, jObj);
	}
	
	//Update BaseNavigationInstructions
	public static void updateBaseNavigationInstructions(BaseNavInstructionUnitTest baseInstruction){
		JSONObject jObj = baseInstruction.packageJSON();

		String url = URL_PRE + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		submitHttpPut(url, jObj);
	}
	


	private static void submitHttpPut(String url, JSONObject jObj){
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;

		JSONObject json;
		try{
			json = jObj;

			HttpPut put = new HttpPut(url);

			StringEntity se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			put.setEntity(se);
			response = client.execute(put);
			/*check response to see if success*/

			if(response!=null){
				if (response.getStatusLine().getStatusCode() != HTTP_RESPONSE_CODE_OK) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
				InputStream in = response.getEntity().getContent();
				
				String result = convertStreamToString(in);
				Log.i("PUTresult",result);
				in.close();
			}

		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("httpPut", e.toString());
		}

	}



	////////////////
	// GET  GET  GET  GET
	//   GET  GET   GET
	/////////////

	//GET FloorTile According to building Id
	public List<FloorTile> getFloorTiles(long buildingId){
		String url = URL_PRE + URL_BUILDING + buildingId + "/" + URL_FLOOR_TILE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<FloorTile> floorTiles = FloorTile.parseJSONArray(jsonArray);
		return floorTiles;
	}

	//GET FloorTile

	public List<FloorTile> getFloorTiles(){
		String url = URL_PRE + URL_FLOOR_TILE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<FloorTile> floorTiles = FloorTile.parseJSONArray(jsonArray);
		return floorTiles;
	}

	//GET UnifiedLandmark According to Building Id
	public List<UnifiedLandmark> getUnifiedLandmarks(long buildingId){
		String url = URL_PRE + URL_BUILDING + buildingId + "/" + URL_UNIFIED_LANDMARK;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<UnifiedLandmark> unifiedLandmarks = UnifiedLandmark.parseJSONArray(jsonArray);
		return unifiedLandmarks;
	}

	//GET SurveyData According to Survey Id
	public List<SurveyData> getSurveyDatas(long surveyId){
		String url = URL_PRE + URL_SURVEY + surveyId + "/" + URL_SURVEY_DATA;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<SurveyData> surveyDatas = SurveyData.parseJSONArray(jsonArray);
		return surveyDatas;
	}

	//GET SurveyData
	public List<SurveyData> getSurveyDatas(){
		String url = URL_PRE + URL_SURVEY_DATA;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<SurveyData> surveyDatas = SurveyData.parseJSONArray(jsonArray);
		return surveyDatas;
	}

	//GET SurveyTracking According to Survey Id
	public List<SurveyTracking> getSurveyTrackings(long surveyId){
		String url = URL_PRE + URL_SURVEY + surveyId + "/" + URL_SURVEY_TRACKING;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<SurveyTracking> surveyTrackingList = SurveyTracking.parseJSONArray(jsonArray);
		return surveyTrackingList;
	}

	//GET SurveyTracking
	public List<SurveyTracking> getSurveyTrackings(){
		String url = URL_PRE + URL_SURVEY_TRACKING;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<SurveyTracking> surveyTrackingList = SurveyTracking.parseJSONArray(jsonArray);
		return surveyTrackingList;
	}

	//GET User
	public List<User> getUsers(){
		String url = URL_PRE + URL_USER;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<User> userList = User.parseJSONArray(jsonArray);
		return userList;
	}

	//Get Survey
	public List<Survey> getSurveys(){
		String url = URL_PRE + URL_SURVEY;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Survey> surveyList = Survey.parseJSONArray(jsonArray);
		return surveyList;
	}

	//Get Help
	public List<Help> getHelp(){
		String url = URL_PRE + "session/" + Global.SESSION_ID + "/groupId/" + Global.GROUP_ID + "/"  + URL_HELP;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Help> helps = Help.parseJSONArray(jsonArray);
		return helps;
	}

	//Get TraversedNodes
	public List<TraversedNode> getTraversedNodes(){
		String url = URL_PRE + "session/" + Global.SESSION_ID + "/groupId/" + Global.GROUP_ID + "/"  + URL_TRAVERSED_NODE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<TraversedNode> traversedNodes = TraversedNode.parseJSONArray(jsonArray);
		return traversedNodes;
	}

	//Get Building Nodes
	public List<BuildingNode> getBuildingNodes(){
		String url = URL_PRE + URL_BUILDING_NODE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<BuildingNode> buildingNodes = BuildingNode.parseJSONArray(jsonArray);
		return buildingNodes;
	}
	
	//Get Buildings
	public List<Building> getBuildings(){
		String url = URL_PRE + URL_BUILDING;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Building> building = Building.parseJSONArray(jsonArray);
		return building;
	}
	
	//Get Particular Building
	public Building getBuildingById(long buildingId){
		String url = URL_PRE + "buildingId/" + buildingId + "/" + URL_BUILDING;
		String httpResponse = submitHttpGet(url);
		JSONObject jsonObj = stringToJsonObject(httpResponse);
		if(jsonObj == null){
			return null;
		}
		Building building = Building.parseJSON(jsonObj);
		return building;
	}

	
	//Get Landmark Nodes
	public List<Landmark> getLandmark(){
		String url = URL_PRE + URL_LANDMARK;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Landmark> landmarks = Landmark.parseJSONArray(jsonArray);
		
		return landmarks;
		
	}
	
	//Get Landmarks for Particular Building
	public static List<Landmark> getLandmarkForBuilding(long buildingId){
		String url = URL_PRE + "buildingId/" + buildingId + "/"  + URL_LANDMARK;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Landmark> landmarks = Landmark.parseJSONArray(jsonArray);
		return landmarks;
	}
	
	//Get Landmarks for Particular Building
	public List<Landmark> getLandmarkForBuildingFilter(long buildingId){
		String url = URL_PRE + "buildingId/" + buildingId + "/"  + URL_LANDMARK + "/filter";
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Landmark> landmarks = Landmark.parseJSONArray(jsonArray);
		return landmarks;
	}
	
	//Get Landmark Nodes
	public List<LandmarkNode> getLandmarkNodes(){
		String url = URL_PRE + URL_LANDMARK_NODE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<LandmarkNode> landmarkNodes = LandmarkNode.parseJSONArray(jsonArray);
		return landmarkNodes;
	}
	
	//Get LandmarkNodes according to rfid
	public List<LandmarkNode> getLandmarkNodeforRFID(String rfid){

		String url = URL_PRE + "rfidlong/" + rfid + "/"  + URL_LANDMARK_NODE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<LandmarkNode> landmarkNodes = LandmarkNode.parseJSONArray(jsonArray);
		return landmarkNodes;
	}
	
	//Get LandmarkNodes for Particular Building
	public List<OfflineLandmark> getOfflineLandmark(){

		String url = URL_PRE + URL_LANDMARK_NODE + "forOffline";
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<OfflineLandmark> offlineLandmarks = OfflineLandmark.parseJSONArray(jsonArray);
		return offlineLandmarks;
	}
	
	//Get LandmarkNodes for Particular Building
	public static List<LandmarkNode> getLandmarkNodesforBuilding(long buildingId){
		String url = URL_PRE + "buildingId/" + buildingId + "/"  + URL_LANDMARK_NODE;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<LandmarkNode> landmarkNodes = LandmarkNode.parseJSONArray(jsonArray);
		return landmarkNodes;
	}
	
	
	
	//Get LandmarkNodes for Particular Building and floor
	//~/api/buildingId/{buildingId}/floor/{floorId}/Landmark
	public List<Landmark> getLandmarkforBuildingAndFloor(long buildingId, long floorId){
		String url = URL_PRE + "buildingId/" + buildingId + "/floor/" + floorId + "/"  + URL_LANDMARK;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Landmark> landmarkNodes = Landmark.parseJSONArray(jsonArray);
		return landmarkNodes;
	}
	
	
	//Get LandmarkNodes for Particular Building and floor
	//"~/api/buildingId/{buildingId}/Landmark/floorCount"
	public List<Landmark> getBuildingFloors(long buildingId){
		String url = URL_PRE + "buildingId/" + buildingId + "/"  + URL_LANDMARK + "floorCount";
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<Landmark> landmarkNodes = Landmark.parseJSONArray(jsonArray);
		return landmarkNodes;
	}
	
	
	//Get NodeLinks 
	public List<NodeLink> getNodeLinks(){
		String url = URL_PRE + URL_NODE_LINK;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<NodeLink> nodeLinks = NodeLink.parseJSONArray(jsonArray);
		return nodeLinks;
	}
	
	//Get NodeLinks for particular building
	public static List<NodeLink> getNodeLinksforBuilding(long buildingId){
		String url = URL_PRE + "buildingId/" + buildingId + "/" + URL_NODE_LINK;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<NodeLink> nodeLinks = NodeLink.parseJSONArray(jsonArray);
		return nodeLinks;
	}
	
	//Get NodeLinks for particular building
	public static List<UnitTest> getUnitTests(){
		String url = URL_PRE + URL_UNIT_TEST;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<UnitTest> unitTests = UnitTest.parseJSONArray(jsonArray);
		return unitTests;
	}
	
	
	//Get BaseNavigationInstructions for particular building
	public static List<BaseNavInstructionUnitTest> getBaseNavigationInstruction(long buildingId, long sourceId, long destinationId){
		String url = URL_PRE + "building/" + buildingId + "/source/" + sourceId + "/destination/" 
				+ destinationId +"/" + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<BaseNavInstructionUnitTest> baseInstructions = BaseNavInstructionUnitTest.parseJSONArray(jsonArray);
		return baseInstructions;
	}

	public static BaseNavInstructionUnitTest getNavigationInstruction(long sourceId, long destinationId){
		String url = URL_PRE + "/source/" + sourceId + "/destination/" + destinationId +"/" + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		String httpResponse = submitHttpGet(url);
		JSONObject jsonObj = stringToJsonObject(httpResponse);
		if(jsonObj == null){
			return null;
		}
		BaseNavInstructionUnitTest baseInstructions = BaseNavInstructionUnitTest.parseJSON(jsonObj);
		return baseInstructions;
	}
	
	public List<BaseNavInstructionUnitTest> getBaseNavForBuilding(long buildingId){
		String url = URL_PRE + "/building/" + buildingId + "/" + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<BaseNavInstructionUnitTest> baseInstructions = BaseNavInstructionUnitTest.parseJSONArray(jsonArray);
		return baseInstructions;
	}
	
	public List<BaseNavInstructionUnitTest> getBaseNavForSourceId(long sourceId){
		String url = URL_PRE + "/source/" + sourceId + "/" + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<BaseNavInstructionUnitTest> baseInstructions = BaseNavInstructionUnitTest.parseJSONArray(jsonArray);
		return baseInstructions;
	}
	
	//api/building/7/page/18/BaseNavInstructionUnitTest
	public List<BaseNavInstructionUnitTest> getBaseNavPagingForBuilding(long buildingId, int page){
		String url = URL_PRE + "/building/" + buildingId + "/page/" + page + "/" + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST;
		String httpResponse = submitHttpGet(url);
		JSONArray jsonArray = stringToJsonArray(httpResponse);
		if(jsonArray == null){
			return null;
		}
		List<BaseNavInstructionUnitTest> baseInstructions = BaseNavInstructionUnitTest.parseJSONArray(jsonArray);
		return baseInstructions;
	}
	
	
	public long getBaseNavPageCountForBuilding(long buildingId){
		String url = URL_PRE + "/building/" + buildingId + "/" + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST + "pageCount";
		String httpResponse = submitHttpGet(url);
		return (long)Double.parseDouble(httpResponse.trim());
	}
	
	//private final static String URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST = "BaseNavInstructionUnitTest/";
	//private final static  String URL_UNIT_TEST_REPORT = "UnitTestReport/";
	//private final static String URL_UNIT_TEST = "UnitTest/";
	

	private static String submitHttpGet(String url){
		HttpResponse response;
		//JSONArray jArray = null;
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url); 
		try {
			response = httpclient.execute(httpget);
			Log.i("GETcommand",response.getStatusLine().toString());

			if (response.getStatusLine().getStatusCode() != HTTP_RESPONSE_CODE_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				Log.i("GETresult",result);
				instream.close();
			}
		} 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("httpGet", e.toString());
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("httpGet", e.toString());
			e.printStackTrace();
			return null;
		} 
		return result;
	}

	private static JSONArray stringToJsonArray(String httpResponse){
		JSONArray jArray = null;
		try {
			if(httpResponse == null || httpResponse.equals("")){
				return null;
			}
			jArray = new JSONArray(httpResponse);
			return jArray;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSONarry", e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	private static JSONObject stringToJsonObject(String httpResponse){
		JSONObject jObj = null;
		try {
			if(httpResponse == null || httpResponse.equals("")){
				return null;
			}
			jObj = new JSONObject(httpResponse);
			return jObj;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSONobject", e.toString());
			e.printStackTrace();
			return null;
		}
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	////DELETE

	public static void deleteFloorTile(FloorTile floorTile){
		String url = URL_PRE + URL_FLOOR_TILE + floorTile.getId();
		submitHttpDelete(url);
	}

	public static void deleteSurveyData(SurveyData surveyData){
		String url = URL_PRE + URL_SURVEY_DATA + surveyData.getId();
		submitHttpDelete(url);
	}

	public static void deleteSurveyTracking(SurveyTracking surveyTracking){
		String url = URL_PRE + URL_SURVEY_TRACKING + surveyTracking.getId();
		submitHttpDelete(url);
	}

	public static void deleteUser(User user){
		String url = URL_PRE + URL_USER + user.getId();
		submitHttpDelete(url);
	}

	public static void deleteSurvey(Survey survey){
		String url = URL_PRE + URL_SURVEY + survey.getId();
		submitHttpDelete(url);
	}
	
	public static void deleteLandamrk(Landmark landmark){
		String url = URL_PRE + URL_LANDMARK+landmark.getId();
		submitHttpDelete(url);
	}
	
	public static void deleteNodeLinks(NodeLink nodelink){
		String url = URL_PRE + URL_NODE_LINK+nodelink.getId();
	//	String url = URL_PRE + URL_LANDMARK+landmark.getId();
		submitHttpDelete(url);
	}
	
	public static void deleteLandmarkNode(LandmarkNode landmarkNode){
		String url = URL_PRE + URL_LANDMARK_NODE + landmarkNode.getId();
		submitHttpDelete(url);
	}
	
	//Send BaseNavInstr
		public static void deleteBaseNavInstructions(BaseNavInstructionUnitTest baseInstruction){

			String url = URL_PRE + URL_BASE_NAV_INSTRUCTIONS_UNIT_TEST + baseInstruction.getId();
			submitHttpDelete(url);
		}

	
	private static void submitHttpDelete(String url){
		HttpResponse response;
		//JSONArray jArray = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		try {
			response = httpclient.execute(httpDelete);
			Log.i("DELETEcommand",response.getStatusLine().toString());
			
			if (response.getStatusLine().getStatusCode() != HTTP_RESPONSE_NO_CONTENT) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			
			
		} 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("httpDelete", e.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("httpDelete", e.toString());
			e.printStackTrace();
			
		} 
		
	}

}
