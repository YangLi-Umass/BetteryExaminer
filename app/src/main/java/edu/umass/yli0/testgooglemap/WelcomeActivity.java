package edu.umass.yli0.testgooglemap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.BuildingBase;
import edu.fiveglabs.percept.HTTP.RestClient;
import edu.fiveglabs.percept.Models.TAO.Building;
import edu.fiveglabs.percept.Models.TAO.FloorTile;
import edu.fiveglabs.percept.Models.TAO.UnifiedLandmark;


public class WelcomeActivity extends ActionBarActivity {

    private PerceptMaintenanceDataSource perceptMaintenanceDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //copyDatabaseFromAssetIfNeed();

        perceptMaintenanceDataSource = new PerceptMaintenanceDataSource(this);
        perceptMaintenanceDataSource.open();

        if(isNetworkConnected()){
            perceptMaintenanceDataSource.deleteAllBuildingBases();
            perceptMaintenanceDataSource.deleteAllUnifiedLandmarkBases();
            perceptMaintenanceDataSource.deleteAllFloorTileBase();

            downloadBuildingData();             //thread has joined.
            createButtons(true);
            updateBuildingDatabase();           //thread has not joined.
            downloadFloorTileData();            //At the same time, update database. thread has not joined.

            for (int i = 0; i < buttonSet.length; i++){
                downloadUnifiedLandmarkData(Integer.parseInt(buildingBases.get(i).getId() + ""), i);
            }

        }else{
            buildingBases = perceptMaintenanceDataSource.getAllBuildingBases();
            createButtons(false);
        }

    }

/*    @Override
    protected void onStart() {
        super.onStart();
        Runnable serverComm = new Runnable(){
            @Override
            public void run() {
                RestClient rc = new RestClient();

                LandmarkNode landmarkNode = new LandmarkNode();
                landmarkNode.setId(0);
                landmarkNode.setBle("0-0");
                landmarkNode.setRfid(1190278011103616l);
                landmarkNode.setLandmarkName("Li Test");
                landmarkNode.setLandmarkNumber(406);
                landmarkNode.setLocationString(99 + "," + -99);
                landmarkNode.setBuildingId(103);
                landmarkNode.setDestination(true);
                landmarkNode.setMultiLandmarkType(0);
                landmarkNode.setLocation(null);


                rc.sendLandmarkNode(landmarkNode);

                List<LandmarkNode> _landmarkNodes = rc.getLandmarkNodeforRFID("1190278011103616");
                int templ;

            }
        };
        Thread t = new Thread(serverComm);
        t.start();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     **********************************************************************************************
     *Get building information from server.
     **/
    List<Building> buildings;
    List<BuildingBase> buildingBases = new ArrayList<>();

    void downloadBuildingData(){
        Runnable serverComm = new Runnable(){
            @Override
            public void run() {
                RestClient rc = new RestClient();
                buildings = rc.getBuildings();
                for(Building building : buildings){
                    buildingBases.add(building);
                }
            }
        };

        Thread t = new Thread(serverComm);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     **********************************************************************************************
     *Get unifiedLandmark information from server.
     **/

    void downloadUnifiedLandmarkData(int _buildingId, int _buttonId){

        final int buildingId = _buildingId;
        final int buttonId = _buttonId;
        Runnable downloadNfc = new Runnable(){
            @Override
            public void run() {
                List<UnifiedLandmark> unifiedLandmarks;

                RestClient rc = new RestClient();
                unifiedLandmarks = rc.getUnifiedLandmarks(buildingId);
                for(UnifiedLandmark unifiedLandmark : unifiedLandmarks){
                    if(!unifiedLandmark.getLocationString().equals(null) && !unifiedLandmark.isVirtualTag()){
                        perceptMaintenanceDataSource.createUnifiedLandmarkBase(unifiedLandmark);
                    }
                }
                Log.d("UnifiedLandmark.", buildingId + " finished.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonSet[buttonId].setClickable(true);
                        buttonSet[buttonId].setTextColor(Color.parseColor("#000000"));
                    }
                });

            }
        };

        Thread t = new Thread(downloadNfc);
        t.start();

    }

    /**
     **********************************************************************************************
     *Get unifiedLandmark information from server.
     **/

    void downloadFloorTileData(){

        Runnable downloadFloorTile = new Runnable(){
            @Override
            public void run() {

                List<FloorTile> floorTiles;
                RestClient rc = new RestClient();
                floorTiles = rc.getFloorTiles();
                for(FloorTile floorTile : floorTiles){
                    perceptMaintenanceDataSource.createFloorTileBase(floorTile);

                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(WelcomeActivity.this, "FloorTile download finish.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread t = new Thread(downloadFloorTile);
        t.start();

    }

    /**
     *Create buttons according to the number of buildings.
     **********************************************************************************************
     **/
    Button buttonSet[];
    void createButtons(boolean _updateDatabase){
        LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.buttonSet);
        buttonSet = new Button[buildingBases.size()];
        for (int i = 0; i < buttonSet.length; i++){
            buttonSet[i] = new Button(this);
            buttonSet[i].setId(i);
            buttonSet[i].setText(buildingBases.get(i).getName());
            buttonLayout.addView(buttonSet[i]);
            LinearLayout.LayoutParams temp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonSet[i].setLayoutParams(temp);
            buttonSet[i].setOnClickListener(welcomeButtonOnClickListener(buttonSet[i]));
            if(_updateDatabase){
                buttonSet[i].setClickable(false);
                buttonSet[i].setTextColor(Color.parseColor("#F44336"));
            }

        }
    }

    /**
     * ButtonOnclickListener.
     * *********************************************************************************************
     * */

    View.OnClickListener welcomeButtonOnClickListener(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                perceptMaintenanceDataSource.close();
                int temp = button.getId();
                startMapsActivity(buildingBases.get(temp).getFloor(), buildingBases.get(temp).getId(),
                        buildingBases.get(temp).getLocationString(), buildingBases.get(temp).getName());
            }
        };
    }

    /**
     * Variable to pass.
     * */
    public static final String BUILDING_FLOOR = "buildingFloor";
    public static final String BUILDING_ID = "buildingId";
    public static final String BUILDING_LOCATION = "buildingLocation";
    public static final String BUILDING_NAME = "buildingName";

    void startMapsActivity(int _buildingFloor, long _buildingId,
                           String _buildingLocation, String _buildingName){

        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

        Bundle myBundle = new Bundle();
        myBundle.putInt(BUILDING_FLOOR, _buildingFloor);
        myBundle.putLong(BUILDING_ID, _buildingId);
        myBundle.putString(BUILDING_LOCATION, _buildingLocation);
        myBundle.putString(BUILDING_NAME, _buildingName);

        intent.putExtras(myBundle);

        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

    }

    /***********************************************************************************************
     * Building Database  **************************************************************************
     * */

    void updateBuildingDatabase(){
        Runnable updateBuildingDb = new Runnable(){
            @Override
            public void run() {

                for(BuildingBase buildingBase : buildingBases){
                    perceptMaintenanceDataSource.createBuildingBase(buildingBase);
                }
            }
        };
        Thread t = new Thread(updateBuildingDb);
        t.start();
    }

    /***********************************************************************************************
     ***********************************************************************************************
     * ship an Android application with a database
     * */
    void copyDatabaseFromAssetIfNeed(){
        PerceptDbHelper perceptDbHelper = new PerceptDbHelper(this);

        try {
            perceptDbHelper.createDataBase();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
