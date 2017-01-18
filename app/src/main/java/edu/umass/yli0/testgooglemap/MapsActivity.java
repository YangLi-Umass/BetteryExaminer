package edu.umass.yli0.testgooglemap;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.FloorTileBase;
import edu.fiveglabs.percept.BaseModels.UnifiedLandmarkBase;
import edu.fiveglabs.percept.HTTP.RestClient;
import edu.fiveglabs.percept.Models.TAO.Survey;
import edu.fiveglabs.percept.Models.TAO.SurveyData;
import edu.fiveglabs.percept.Models.TAO.SurveyTracking;
import edu.umass.yli0.testgooglemap.util.SystemUiHider;

public class MapsActivity extends AppCompatActivity {

    //Configurable Variable
    //AUTO_HIDE         AUTO_HIDE_DELAY_MILLIS          TOGGLE_ON_CLICK

    //Variables need to be configured begins*****************************
    long nowSurveyId = 0;
    long nowUserId = 3;
    //Variables need to be configured ends*******************************

    //NFC part starts   NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****Yang
        private static PendingIntent mPendingIntent;        //Yang
        private static IntentFilter[] mFilters;             //Yang
        private static NfcAdapter mAdapter;
        private static String[][] mTechLists;
        public static String rfid= null;
        public static long rfidDec = 0;

        private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1',
                (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
                (byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
                (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };

        final String REMAINING_NFC_TAGS_NUM ="Remaining NFC tags number: ";
    private TextView remainingNfcTagsNumTextView;

    //NFC part ends     NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****Yang

    double buildingPointLat = 42.393228;                //Initial Camera Position
    double buildingPointLng = -72.529057;
    int initialZoom = 25;

    //RedMarkerToDisplayUserPosition
    double initialMarkerUserLat = 42.393393;
    double initialMarkerUserLng = -72.529197;
    Marker markerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        //0st. Get variable from welcome activity
               //Modify actionbar name according to building name
        //1st. Download NFC Data according to building ID
        //2nd. Download tile
               //Create button according to the number of tiles
        //3rd. Get tile according to building ID
               //4st. GoogleMap initialize; Move camera according to building location
               //     Display the tile of first floor on the map
        //6st. Beacon region initialize
        //setUpMapIfNeeded();
        perceptMaintenanceDataSource = new PerceptMaintenanceDataSource(this);
        perceptMaintenanceDataSource.open();

        getVariableFromWelcomeActivity();
        /*if(isNetworkConnected()){
            Toast.makeText(MapsActivity.this, "Start download ...",Toast.LENGTH_SHORT).show();
            getUnifiedLandmarkFromDatabase();       //join
            getFloorTileFromDatabase();                    //join
            perceptMaintenanceDataSource.close();
        }else{
            Toast.makeText(MapsActivity.this, "Please connect to Internet."
                    , Toast.LENGTH_SHORT).show();
        }*/

        getUnifiedLandmarkFromDatabase();       //join
        getFloorTileFromDatabase();                    //join
        perceptMaintenanceDataSource.close();
        createSideButtons();
        setUpMapIfNeeded();
        divideTagsIntoGroups();

        beaconMinorFilter = new ArrayList<>();

        initializeHide();

        /*
        findViewById(R.id.getSurveyId).setOnClickListener(getSurveyIdButtonListener());
        */

        /*InitializeNfcMarker();

        //bugroup should be initialized here. In the future,
        it will be initialized via Internet
        totalNfcTagsNum = nfcs.length;
        //related variables initialize
        remainingTagsNum = totalNfcTagsNum;

        remainingNfcTagsNumTextView.setBackgroundColor(0xffff0000);
        remainingNfcTagsNumTextView.setText(REMAINING_NFC_TAGS_NUM + totalNfcTagsNum);*/

        initializeBeaconWithLatLng();                       // Load beacons information and
        // initialize the beaconMinorFilter
        try{
            initNFC();
        }catch ( NullPointerException e ) {
            System.out.println("null");
            e.printStackTrace();
        }catch ( StringIndexOutOfBoundsException e ) {
            System.out.println("String index error!");
            e.printStackTrace();
        }catch ( RuntimeException e ) {
            System.out.println("runtime exception!");
            e.printStackTrace();
        }

        initializeBeaconRegion();
        customToast();
        try {
            beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
            Toast.makeText(MapsActivity.this, "Start Ranging.", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            Log.e(BEACON_LOG, "Cannot start ranging", e);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("Beacon Range.", "Cannot start ranging", e);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Should be invoked in #onStop.
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e("Beacon Range.", "Cannot stop but it does not matter now", e);
        }
    }

    private Menu menu;
    final static String ITEM_GREEN_TITLE = "Scanned Tags Number: ";
    final static String ITEM_YELLOW_TITLE = "Remaining Tags Number: ";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        this.menu = menu;
        menu.findItem(R.id.mapLegendGreen).setEnabled(false);
        menu.findItem(R.id.mapLegendRed).setEnabled(false);
        menu.findItem(R.id.mapLegendYellow).setEnabled(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_upload:
                try {
                    beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("Beacon Range.", "Cannot stop but it does not matter now", e);
                }

                if(isNetworkConnected()){
                    menu.findItem(R.id.action_upload).setIcon(getResources().getDrawable(R.drawable.ic_file_upload_red_24dp));
                    uploadSurveyDataSurveyTracking();
                }else{
                    displayMyToast("Please check your Internet connection.","#F44336");
                }
                return true;
            case R.id.action_add:
                startAddTagActivity(BUILDING_ID, floorFilePath, buildingPointLat, buildingPointLng);
                return true;
            case R.id.action_remove:
                startRemoveTagActivity(BUILDING_ID);
                return true;
            case R.id.action_replace:
                startReplaceTagActivity(BUILDING_ID);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }

    /***********************************************************************************************
     Toast*****Toast*****Toast*****Toast*****Toast*****Toast*****Toast*****Toast*****Toast*****Toast
     ***********************************************************************************************
     */
    Toast myToast;
    TextView toastText;
    LinearLayout toastLayout;
    void customToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        toastText = (TextView) layout.findViewById(R.id.text);
        toastLayout = (LinearLayout)layout.findViewById(R.id.toast_layout_root);
        myToast = new Toast(getApplicationContext());
        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        myToast.setDuration(Toast.LENGTH_LONG);
        myToast.setView(layout);
    }

    void displayMyToast(String _textString, String _colorString){
        toastText.setText(_textString);
        toastLayout.setBackgroundColor(Color.parseColor(_colorString));
        myToast.show();
    }

    /***********************************************************************************************
     GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap**
     ***********************************************************************************************
     */
    private GoogleMap mMap;                          // Might be null if Google Play services APK is not available.

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * Add user marker and move camera
     */
    private void setUpMap() {
        markerUser = mMap.addMarker(new MarkerOptions().position(new LatLng(initialMarkerUserLat, initialMarkerUserLng))
                .title("User").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(buildingPointLat, buildingPointLng), initialZoom));
    }

    /***********************************************************************************************
     NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC****
     ***********************************************************************************************
     */

    public class NFC{
        long rfid;
        double lat;
        double lng;
        String name;
        Marker marker;
        int scannedFlag = 4;            //unscanned tags = 4; NFC tags = 0;
                                        //NfcShouldBeRemoved = 2;
        SurveyTracking surveyTracking = new SurveyTracking();
        SurveyData surveyData = new SurveyData();

        NFC(long _rfid, double _lat, double _lng, String _name, long _landmarkNumber){
            this.rfid = _rfid;
            this.lat = _lat;
            this.lng = _lng;
            this.name = _name;
            landmarkNumber = _landmarkNumber;
            surveyData.setTagId(_rfid);
            surveyData.setTagType(4);
        }

        //Landmark attribute
        long landmarkNumber;

    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        resolveIntent(intent);

    }

    private void initNFC(){
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        // Create a generic PendingIntent that will be deliver to this activity.
        // The NFC stack
        // will fill in the intent with the details of the discovered tag before
        // delivering to
        // this activity.
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // Setup an intent filter for all MIME based dispatches
        IntentFilter nfc = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

        try {
            nfc.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mFilters = new IntentFilter[] { nfc };

        // Setup a tech list for all NfcF tags
        //	mTechLists = new String[][] { new String[] { MifareClassic.class.getName()} };

        mTechLists = new String[][] {
                new String[] { MifareUltralight.class.getName()},
                new String[] { MifareClassic.class.getName()}};

        Intent intent = getIntent();
        resolveIntent(intent);
    }

    public void resolveIntent(Intent intent) {
        // Parse the intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            readCard(tagFromIntent);
        }
    }

    public void readCard(Tag tagFromIntent){

        if(nowIndex != -20){
            byte[] tagID = tagFromIntent.getId();
            rfid = getHexString(tagID, tagID.length);
            rfidDec = Long.parseLong(rfid, 16);

            for(int i = 0; i < nfcGroupByFloor[nowIndex].length; i++){
                if(rfidDec == nfcGroupByFloor[nowIndex][i].rfid){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(nfcGroupByFloor[nowIndex][i].lat, nfcGroupByFloor[nowIndex][i].lng), initialZoom));
                    if(nfcGroupByFloor[nowIndex][i].scannedFlag != 0){      //if this NFC tag is unscanned
                        nfcGroupByFloor[nowIndex][i].scannedFlag = 0;
                        //nfcGroupByFloor[nowIndex][i].surveyTracking.setLocation(nowPosition.latitude + ", " + nowPosition.longitude);
                        nfcGroupByFloor[nowIndex][i].surveyTracking.setId(0);
                        nfcGroupByFloor[nowIndex][i].surveyTracking.setTimeStamp(RestClient.getTimeStamp());
                        nfcGroupByFloor[nowIndex][i].surveyData.setId(0);
                        nfcGroupByFloor[nowIndex][i].surveyData.setTagId(rfidDec);
                        nfcGroupByFloor[nowIndex][i].surveyData.setTagType(0);
                        nfcGroupByFloor[nowIndex][i].surveyData.setTimeStamp(RestClient.getTimeStamp());
                        remainingTagsNum[nowIndex]--;
                        menu.findItem(R.id.mapLegendGreen).setTitle(ITEM_GREEN_TITLE + (nfcNumberOfEachGroup[nowIndex] - remainingTagsNum[nowIndex]));
                        menu.findItem(R.id.mapLegendYellow).setTitle(ITEM_YELLOW_TITLE + remainingTagsNum[nowIndex]);
                        /*setTitle(displayTitle1 + remainingTagsNum[nowIndex] +
                                displayTitle2 + (nfcNumberOfEachGroup[nowIndex] - remainingTagsNum[nowIndex]));*/

                        displayMyToast("Scan RFID tag Successfully!\n" + rfidDec, "#4CAF50");
                        nfcGroupByFloor[nowIndex][i].marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                        //After scanned successfully, find whether there is tags on the same landmark
                        for(int j = 0; j < nfcGroupByFloor[nowIndex].length; j++){
                            if(nfcGroupByFloor[nowIndex][j].landmarkNumber == nfcGroupByFloor[nowIndex][i].landmarkNumber){
                                if(nfcGroupByFloor[nowIndex][j].scannedFlag == 4){
                                    nfcGroupByFloor[nowIndex][j].scannedFlag = 2;
                                    nfcGroupByFloor[nowIndex][j].surveyData.setTagType(2);
                                }

                                /*Toast.makeText(MapsActivity.this, "Same landmark." + nfcGroupByFloor[nowIndex][j].landmarkNumber + "\n"
                                        + "Rfid " + nfcGroupByFloor[nowIndex][j].rfid + "\n"
                                        + "ScannedFlag " + nfcGroupByFloor[nowIndex][j].scannedFlag, Toast.LENGTH_SHORT).show();*/
                            }
                        }
                    }else{
                        displayMyToast("This tag has been scanned.\n" + rfidDec , "#2196F3");
                    }
                    break;
                }
                if(i == nfcGroupByFloor[nowIndex].length-1){
                    displayMyToast("This tag does not belong to this system.\n" + rfidDec, "#F44336");
                }
            }
        }else{
            displayMyToast("Please choose floor number first.", "#F44336");
        }
    }

    public static String getHexString(byte[] raw, int len) {
        byte[] hex = new byte[2 * len];
        int index = 0;
        int pos = 0;

        for (byte b : raw) {
            if (pos >= len)
                break;

            pos++;
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }

        return new String(hex);
    }

    /**
     * getUnifiedLandmarkFromDatabase
     *
     */

    private PerceptMaintenanceDataSource perceptMaintenanceDataSource;
    List<UnifiedLandmarkBase> unifiedLandmarkBases = new ArrayList<>();
    void getUnifiedLandmarkFromDatabase(){

        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                unifiedLandmarkBases = perceptMaintenanceDataSource.getUnifiedLandmarkBasesByBuildingId(BUILDING_ID);
            }
        };

        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*List<UnifiedLandmark> unifiedLandmarks;
    void getUnifiedLandmarkFromDatabase(){
        Runnable getUnifiedLandmarkFromDatabase = new Runnable(){
            @Override
            public void run() {
                RestClient rc = new RestClient();
                unifiedLandmarks = rc.getUnifiedLandmarks(BUILDING_ID);
            }
        };

        Thread t = new Thread(getUnifiedLandmarkFromDatabase);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Divide unifiedLandmarks into some group according to the number of floor tiles
     *
     */
    NFC[][] nfcGroupByFloor;                         //to store all the nfc information
    int[] remainingTagsNum;
    int[] nfcNumberOfEachGroup ;
    void divideTagsIntoGroups(){
        int groupNumber = floorTileBases.size();
        nfcNumberOfEachGroup = new int[groupNumber];

        for(UnifiedLandmarkBase unifiedLandmarkBase : unifiedLandmarkBases){

            for(int i = 0; i < groupNumber; i++){
                if(unifiedLandmarkBase.getFloor() == floorTileBases.get(i).getFloor() && !unifiedLandmarkBase.getLocationString().equals(null)){
                    nfcNumberOfEachGroup[i]++;
                }
            }
        }
        nfcGroupByFloor = new NFC[groupNumber][];
        remainingTagsNum = new int [groupNumber];
        for(int i = 0; i < groupNumber; i++){
            nfcGroupByFloor[i] = new NFC[nfcNumberOfEachGroup[i]];
        }

        int[] temp = new int[groupNumber];
        for(UnifiedLandmarkBase unifiedLandmarkBase : unifiedLandmarkBases){
            for(int i = 0; i < groupNumber; i++){
                if(unifiedLandmarkBase.getFloor() == floorTileBases.get(i).getFloor() && !unifiedLandmarkBase.getLocationString().equals(null)){
                    String[] latLng = unifiedLandmarkBase.getLocationString().split(",");
                    nfcGroupByFloor[i][temp[i]] = new NFC(unifiedLandmarkBase.getRfid(),Double.parseDouble(latLng[0]),
                            Double.parseDouble(latLng[1]),unifiedLandmarkBase.getLandmarkName(),
                            unifiedLandmarkBase.getLandmarkNumber());

                    temp[i]++;
                }
            }
        }
    }


    void initializeBeaconWithLatLng(){

        String testBeacons = "28473,42.393197,-72.529155"

                + "@50192,42.393203,-72.529056"

                + "@28657,42.393254,-72.528973"

                + "@22928,42.393263,-72.528874"

                + "@56011,42.393316,-72.528787"

                + "@43610,42.393322,-72.528692"

                ;

        String[] testBeaconsArray = testBeacons.split("@");
        beaconWithLatLngs = new BeaconWithLatLng[testBeaconsArray.length];

        for(int i = 0; i< testBeaconsArray.length; i++){

            int beaconMinorIndex = 0;
            int latitudeIndex = 1;
            int longitudeIndex = 2;

            String[] beaconComponents = testBeaconsArray[i].split(",");
            Integer beaconMinor = Integer.parseInt(beaconComponents[beaconMinorIndex]);
            double beaconLat = Double.parseDouble(beaconComponents[latitudeIndex]);
            double beaconLng = Double.parseDouble(beaconComponents[longitudeIndex]);

            beaconWithLatLngs[i] = new BeaconWithLatLng(beaconMinor,beaconLat,beaconLng);
            beaconMinorFilter.add(beaconMinor);
        }
    }

    void uploadSurveyDataSurveyTracking(){

        Runnable sendSurveyDataNfcAndTracking = new Runnable(){
            @Override
            public void run() {

                RestClient rc = new RestClient();

                Survey survey = new Survey();
                survey.setBuildingId(BUILDING_ID);
                survey.setStartTime(RestClient.getTimeStamp());
                survey.setId(0);

                nowSurveyId = rc.sendSurvey(survey);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        displayMyToast("Now survey ID: " + nowSurveyId, "#4CAF50");
                    }
                });

                for(NFC[] nfcGroup : nfcGroupByFloor){
                    for(NFC nfc : nfcGroup){
                        if(nfc.scannedFlag == 0){
                            nfc.surveyTracking.setUserId(nowUserId);
                            nfc.surveyTracking.setSurveyId(nowSurveyId);
                            rc.sendSurveyTracking(nfc.surveyTracking);

                        }
                        nfc.surveyData.setUserId(nowUserId);
                        nfc.surveyData.setSurveyId(nowSurveyId);
                        rc.sendSurveyData(nfc.surveyData);
                    }
                }

                for(BeaconWithLatLng beaconWithLatLng : beaconWithLatLngs){
                    if(beaconWithLatLng.scannedFlag == 1){
                        beaconWithLatLng.surveyData.setUserId(nowUserId);
                        beaconWithLatLng.surveyData.setSurveyId(nowSurveyId);
                        rc.sendSurveyData(beaconWithLatLng.surveyData);
                    }
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        menu.findItem(R.id.action_upload).setIcon(getResources().getDrawable(R.drawable.ic_file_upload_white_24dp));
                        //displayMyToast("Finish upload.", "#4CAF50");

                    }
                });
            }
        };

        Thread t = new Thread(sendSurveyDataNfcAndTracking);
        t.start();

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
     HideUi*****HideUi*****HideUi*****HideUi*****HideUi*****HideUi*****HideUi*****HideUi*****HideUi*
     ***********************************************************************************************
     */

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    void initializeHide (){
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.map);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;


                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getWidth();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationX(visible ? 0 : -mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Log.d("Map", "Map clicked");
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    /***********************************************************************************************
     Beacon*****Beacon*****Beacon*****Beacon*****Beacon*****Beacon*****Beacon*****Beacon*****Beacon*
     ***********************************************************************************************
     */

    /**
     * Region Configuration
     */
    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId",
            ESTIMOTE_PROXIMITY_UUID, null, null);

    /**
     * SCAN_PERIOD_MILLIS - How long to perform Bluetooth Low Energy scanning?
     * WAIT_TIME_MILLIS - How long to wait until performing next scanning?
     */
    private static final int SCAN_PERIOD_MILLIS = 1000;
    private static final int WAIT_TIME_MILLIS = 1000;

    /**
     * CACHE_TIMES - How many times to cache RSSI for per beacon before calculate the average RSSI?
     */
    private final static int CACHE_TIMES = 2;


    private BeaconManager beaconManager = new BeaconManager(this);
    public static final String BEACON_LOG = "BEACON_LOG";
    Collection<Integer> beaconMinorFilter;      //beacon minor filter
    BeaconWithLatLng[] beaconWithLatLngs;
    int bufferPointer = 0;                      //to change the index where new RSSI should be stored
    LatLng nowPosition;                         //to update the user position

    public class BeaconWithLatLng{
        Integer beaconMinor;
        Double lat;
        Double lng;
        Integer[] rssi = {-100,-100,-100};      //[2] store the average of RSSI
        int scannedFlag = 0;
        SurveyData surveyData = new SurveyData();

        BeaconWithLatLng(Integer _beaconMinor, Double _lat, Double _lng){
            this.beaconMinor = _beaconMinor;
            this.lat = _lat;
            this.lng = _lng;
        }
    }

    void initializeBeaconRegion(){

        beaconManager.setForegroundScanPeriod(SCAN_PERIOD_MILLIS, WAIT_TIME_MILLIS);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {

                for (Iterator<Beacon> bIterator = beacons.iterator(); bIterator.hasNext(); ) {
                    final Beacon beacon = bIterator.next();
                    if (beaconMinorFilter.contains(beacon.getMinor())) {

                        //after filtering by beacon's minor
                        for (int i = 0; i < beaconWithLatLngs.length; i++) {
                            if (beacon.getMinor() == beaconWithLatLngs[i].beaconMinor) {
                                beaconWithLatLngs[i].rssi[bufferPointer] = beacon.getRssi();
                                if (beaconWithLatLngs[i].scannedFlag == 0) {
                                    beaconWithLatLngs[i].scannedFlag = 1;
                                    beaconWithLatLngs[i].surveyData.setId(0);
                                    beaconWithLatLngs[i].surveyData.setTagId(beacon.getMinor());
                                    beaconWithLatLngs[i].surveyData.setTagType(1);
                                    beaconWithLatLngs[i].surveyData.setTimeStamp(RestClient.getTimeStamp());
                                }
                            }
                        }
                    }
                }

                bufferPointer++;

                if (bufferPointer >= CACHE_TIMES) {
                    bufferPointer = 0;
                    //compute the average of RSSI
                    for (int i = 0; i < beaconWithLatLngs.length; i++) {
                        beaconWithLatLngs[i].rssi[CACHE_TIMES] = 0;
                        for (int j = 0; j < CACHE_TIMES; j++) {
                            beaconWithLatLngs[i].rssi[CACHE_TIMES] = beaconWithLatLngs[i].rssi[CACHE_TIMES] + beaconWithLatLngs[i].rssi[j];
                        }
                        beaconWithLatLngs[i].rssi[CACHE_TIMES] = beaconWithLatLngs[i].rssi[CACHE_TIMES] / CACHE_TIMES;
                    }
                    //sort by average of RSSI
                    BeaconWithLatLng[] beaconWithLatLngSorts = beaconWithLatLngs;
                    for (int i = 0; i < 2; i++) {
                        for (int j = i + 1; j < beaconWithLatLngSorts.length; j++) {
                            if (beaconWithLatLngSorts[i].rssi[CACHE_TIMES] < beaconWithLatLngSorts[j].rssi[CACHE_TIMES]) {
                                BeaconWithLatLng beaconWithLatLngTemp;
                                beaconWithLatLngTemp = beaconWithLatLngSorts[i];
                                beaconWithLatLngSorts[i] = beaconWithLatLngSorts[j];
                                beaconWithLatLngSorts[j] = beaconWithLatLngTemp;
                            }
                        }
                    }
                    nowPosition = new LatLng(beaconWithLatLngSorts[0].lat, beaconWithLatLngSorts[0].lng);
                    markerUser.setPosition(nowPosition);
                }

            }
        });
    }

    /***********************************************************************************************
     From welcome activity*****From welcome activity*****From welcome activity**********************
     ***********************************************************************************************
     */

    /**
     * From welcome activity
     */
    int BUILDING_FLOOR;
    long BUILDING_ID;
    String BUILDING_LOCATION;
    String BUILDING_NAME;

    void getVariableFromWelcomeActivity(){
        Bundle myBundle = getIntent().getExtras();
        BUILDING_FLOOR = myBundle.getInt(WelcomeActivity.BUILDING_FLOOR);
        BUILDING_ID = myBundle.getLong(WelcomeActivity.BUILDING_ID);
        BUILDING_LOCATION = myBundle.getString(WelcomeActivity.BUILDING_LOCATION);
        BUILDING_NAME = myBundle.getString(WelcomeActivity.BUILDING_NAME);
        setTitle(BUILDING_NAME);
        String [] temp = BUILDING_LOCATION.split(",");
        buildingPointLat = Double.parseDouble(temp[0]);
        buildingPointLng = Double.parseDouble(temp[1]);
    }


    void startReplaceTagActivity(long _buildingId){

        Intent intent = new Intent(getApplicationContext(),ReplaceTagActivity.class);

        Bundle myBundle = new Bundle();
        myBundle.putLong(WelcomeActivity.BUILDING_ID, _buildingId);

        intent.putExtras(myBundle);

        startActivity(intent);
    }

    void startRemoveTagActivity(long _buildingId){

        Intent intent = new Intent(getApplicationContext(),RemoveTagActivity.class);

        Bundle myBundle = new Bundle();
        myBundle.putLong(WelcomeActivity.BUILDING_ID, _buildingId);

        intent.putExtras(myBundle);

        startActivity(intent);
    }

    /**
     * Variable to pass.
     * */
    public static final String BUILDING_FLOOR_TILE_PATH = "floorTilePath";
    public static final String BUILDING_CENTRAL_LAT = "buildingCentralLat";
    public static final String BUILDING_CENTRAL_LNG = "buildingCentralLng";

    void startAddTagActivity(long _buildingId, String _floorTilePath,
                             Double _buildingCentralLat, Double _buildingCentralLng){

        Intent intent = new Intent(getApplicationContext(),AddTagActivity.class);

        Bundle myBundle = new Bundle();
        myBundle.putLong(WelcomeActivity.BUILDING_ID, _buildingId);
        myBundle.putString(MapsActivity.BUILDING_FLOOR_TILE_PATH, _floorTilePath);
        myBundle.putDouble(MapsActivity.BUILDING_CENTRAL_LAT, _buildingCentralLat);
        myBundle.putDouble(MapsActivity.BUILDING_CENTRAL_LNG, _buildingCentralLng);

        intent.putExtras(myBundle);

        startActivity(intent);
    }


    /***********************************************************************************************
     Download tile and Create button according to the number of tiles*******************************
     ***********************************************************************************************
     */

    /**
     * Download tile
     */
    List<FloorTileBase> floorTileBases;
    void getFloorTileFromDatabase(){

        Runnable downloadFloorTile = new Runnable(){
            @Override
            public void run() {
                floorTileBases = perceptMaintenanceDataSource.getAllFloorTileBasesByBuildingId(BUILDING_ID);
            }
        };

        /*Runnable getFloorTileFromDatabase = new Runnable(){
            @Override
            public void run() {
                RestClient rc = new RestClient();
                floorTiles = rc.getFloorTiles(BUILDING_ID);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MapsActivity.this, "Finish download.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };*/

        Thread t = new Thread(downloadFloorTile);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create buttons according to the number of floor tiles
     */
    Button sideButton[];
    void createSideButtons(){
        LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.fullscreen_content_controls);
        sideButton = new Button[floorTileBases.size()];
        for (int i = 0; i < sideButton.length; i++){
            sideButton[i] = new Button(this);
            sideButton[i].setId(i + 100);               //Side button id offset 100
            sideButton[i].setText(floorTileBases.get(i).getDisplayName());
            buttonLayout.addView(sideButton[i]);
            LinearLayout.LayoutParams temp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            sideButton[i].setLayoutParams(temp);
            sideButton[i].setOnClickListener(updateUiAccordingToClick(sideButton[i]));

        }

    }

    /***********************************************************************************************
     SideButtonClick*****SideButtonClick*****SideButtonClick*****SideButtonClick*****SideButtonClick
     ***********************************************************************************************
     */
    NFC [] nowNfc;
    TileOverlay nowTileOverlay;
    int nowIndex = -20;

    /*public class MyUrlTileProvider extends UrlTileProvider {

        private String baseUrl;

        public MyUrlTileProvider(int width, int height, String url) {
            super(width, height);
            this.baseUrl = url;
        }

        @Override
        public URL getTileUrl(int x, int y, int zoom) {
            try {
                return new URL(baseUrl.replace("{z}", ""+zoom).replace("{x}",""+x).
                        replace("{y}", "" + y));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/

    String floorFilePath;    //When click addTag button, this String will be passed to AddTagActivity
    private View.OnClickListener updateUiAccordingToClick(final Button button) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(nowTileOverlay != null){
                    nowTileOverlay.remove();
                }

                /*MyUrlTileProvider myUrlTileProvider = new MyUrlTileProvider(256, 256,
                        floorTileBases.get(button.getId()-100).getTileUrl()+"{z}/{x}/{y}.png");
                nowTileOverlay = mMap.addTileOverlay(new
                        TileOverlayOptions().tileProvider(myUrlTileProvider).zIndex(0));*/
                floorFilePath = floorTileBases.get(button.getId()-100).getTileUrl();
                nowTileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new CustomMapTileProvider(getResources().getAssets(),
                        floorFilePath)));


                nowIndex = button.getId()-100;
                if(nowNfc != null){
                    for(int i = 0; i < nowNfc.length; i++){
                        nowNfc[i].marker.remove();
                    }
                }
                nowNfc = nfcGroupByFloor[button.getId()-100];
                remainingTagsNum[button.getId()-100] = nowNfc.length;
                for(int i = 0; i < nowNfc.length; i++){
                    LatLng position = new LatLng(nowNfc[i].lat,nowNfc[i].lng);
                    nowNfc[i].marker = mMap.addMarker(new MarkerOptions().
                            position(position).title(nowNfc[i].name + "; " + nowNfc[i].rfid)
                            .draggable(false).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    if(nowNfc[i].scannedFlag == 0){
                        nowNfc[i].marker.setIcon(BitmapDescriptorFactory.
                                defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        remainingTagsNum[button.getId()-100]--;
                    }else{
                        nowNfc[i].marker.setIcon(BitmapDescriptorFactory.
                                defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    }
                }
                /*displayTitle1 = "Floor number: " + floorTiles.get(button.getId()-100).getFloor() + "    Remaining Tags number: ";
                displayTitle2 = "    Scanned Tags number: ";
                setTitle(displayTitle1 + remainingTagsNum[button.getId()-100] +
                displayTitle2 + (nfcNumberOfEachGroup[button.getId()-100] - remainingTagsNum[button.getId()-100]));*/

                menu.findItem(R.id.mapLegendYellow).setTitle(ITEM_YELLOW_TITLE + remainingTagsNum[button.getId()-100]);
                menu.findItem(R.id.mapLegendGreen).setTitle(ITEM_GREEN_TITLE + (nfcNumberOfEachGroup[button.getId() - 100] - remainingTagsNum[button.getId() - 100]));
                setTitle("Floor number: " + floorTileBases.get(button.getId() - 100).getFloor());

                Toast.makeText(MapsActivity.this, "Update UI successfully." +
                        nfcNumberOfEachGroup[button.getId()-100], Toast.LENGTH_SHORT).show();
            }
        };
    }

}

