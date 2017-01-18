package edu.umass.yli0.testgooglemap;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.util.List;

import edu.fiveglabs.percept.HTTP.RestClient;
import edu.fiveglabs.percept.Models.TAO.LandmarkNode;

public class AddTagActivity extends AppCompatActivity implements GoogleMap.OnMarkerDragListener {

    private LandmarkNodeListAdapter adapter;
    private long buildingId;
    private TextView textViewLandmarkNodeInfo;
    private TextView textViewButtonInfo;
    private Button buttonAdd;
    private ListView list;

    private ScrollView scrollView;

    private EditText addBleMajor;
    private EditText addBleMinor;
    private EditText addBuildingId;
    private EditText addIsDestination;
    private EditText addLandmarkName;
    private EditText addLandmarkNumber;
    private EditText addLocationLat;
    private EditText addLocationLng;
    private EditText addMultiLandmarkType;
    private EditText addRfid;

    private View fragmentGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        fragmentGoogleMap = findViewById(R.id.mapIndicateLocation);

        scrollView = (ScrollView)findViewById(R.id.landmark_attribute);

        addBleMajor = (EditText)findViewById(R.id.add_button_ble_major);
        addBleMinor = (EditText)findViewById(R.id.add_button_ble_minor);
        addBuildingId = (EditText)findViewById(R.id.add_button_building_id);
        addIsDestination = (EditText)findViewById(R.id.add_button_is_destination);
        addLandmarkName = (EditText)findViewById(R.id.add_button_landmark_name);
        addLandmarkNumber = (EditText)findViewById(R.id.add_button_landmark_number);
        addLocationLat = (EditText)findViewById(R.id.add_button_location_lat);
        addLocationLng = (EditText)findViewById(R.id.add_button_location_lng);
        addMultiLandmarkType = (EditText)findViewById(R.id.add_button_multi_landmark_type);
        addRfid = (EditText)findViewById(R.id.add_button_rfid);

        textViewLandmarkNodeInfo = (TextView)findViewById(R.id.landmark_node_info);
        textViewButtonInfo = (TextView)findViewById(R.id.button_info);

        buttonAdd = (Button)findViewById(R.id.button_add);

        initializeButtonOnClick();

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

        // Configure device list.
        adapter = new LandmarkNodeListAdapter(this);
        list = (ListView)findViewById(R.id.landmark_node_list);
        list.setAdapter(adapter);

        getVariableFromMapsActivity();
        setUpMapIfNeeded();
        fragmentGoogleMap.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tag, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }

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

    //NFC part ends     NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****Yang

    /***********************************************************************************************
     NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC*****NFC****
     ***********************************************************************************************
     */

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

        byte[] tagID = tagFromIntent.getId();
        rfid = getHexString(tagID, tagID.length);
        rfidDec = Long.parseLong(rfid, 16);

        List<LandmarkNode> landmarkNodes = getLandmarkNode(rfidDec /*1289711604214144l*/);
        int nowLandmarkNodeSize;
        nowLandmarkNodeSize = landmarkNodes.size();
        if(nowLandmarkNodeSize == 0){
            textViewLandmarkNodeInfo.setText("There is no record about this tag in DataBase.");
            buttonAdd.setClickable(true);
            buttonAdd.setTextColor(Color.parseColor("#2196F3"));
            scrollView.setVisibility(View.VISIBLE);
            list.setAdapter(null);
            addRfid.setText(rfidDec + "");
            addBuildingId.setText(buildingId + "");
            list.setVisibility(View.GONE);
            fragmentGoogleMap.setVisibility(View.VISIBLE);
        }else{
            adapter.replaceWith(landmarkNodes);
            list.setAdapter(adapter);
            textViewLandmarkNodeInfo.setText("Scanned tags information in Database.");

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
     * Query LandmarkNode Object according to Rfid
     * **/
    List<LandmarkNode> _landmarkNodes;
    List<LandmarkNode> getLandmarkNode(final long _rfid){

        Runnable serverComm = new Runnable(){
            @Override
            public void run() {
                RestClient rc = new RestClient();
                _landmarkNodes = rc.getLandmarkNodeforRFID(_rfid+"");
            }
        };
        Thread t = new Thread(serverComm);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return _landmarkNodes;
    }


    /**
     * Get Variable From MapsActivity
     * */

    String buildingFloorTilePath;
    Double buildingCentralLat;
    Double buildingCentralLng;

    void getVariableFromMapsActivity(){
        Bundle myBundle = getIntent().getExtras();
        buildingId = myBundle.getLong(WelcomeActivity.BUILDING_ID);
        buildingFloorTilePath = myBundle.getString(MapsActivity.BUILDING_FLOOR_TILE_PATH);
        buildingCentralLat = myBundle.getDouble(MapsActivity.BUILDING_CENTRAL_LAT);
        buildingCentralLng = myBundle.getDouble(MapsActivity.BUILDING_CENTRAL_LNG);
    }

    /**
     * Button OnClick
     * */
    void initializeButtonOnClick(){

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (addBleMajor.getText().toString().matches("") ||
                        addBleMinor.getText().toString().matches("") ||
                        addBuildingId.getText().toString().matches("") ||
                        addIsDestination.getText().toString().matches("") ||
                        addLandmarkName.getText().toString().matches("") ||
                        addLandmarkNumber.getText().toString().matches("") ||
                        addLocationLat.getText().toString().matches("") ||
                        addLocationLng.getText().toString().matches("") ||
                        addMultiLandmarkType.getText().toString().matches("") ||
                        addRfid.getText().toString().matches("")) {
                    textViewButtonInfo.setText("Please make sure there is no blank field.");
                } else {
                    textViewButtonInfo.setText("");
                    Runnable serverComm = new Runnable() {
                        @Override
                        public void run() {
                            RestClient rc = new RestClient();

                            LandmarkNode landmarkNode = new LandmarkNode();
                            landmarkNode.setBle(addBleMajor.getText().toString() + "-" + addBleMinor.getText().toString());
                            landmarkNode.setId(0);
                            landmarkNode.setRfid(Long.parseLong(addRfid.getText().toString()));
                            landmarkNode.setLandmarkName(addLandmarkName.getText().toString());
                            landmarkNode.setLandmarkNumber(Integer.parseInt(addLandmarkNumber.getText().toString()));
                            landmarkNode.setLocationString(addLocationLat.getText().toString() + "," +
                                    addLocationLng.getText().toString());
                            landmarkNode.setBuildingId(Long.parseLong(addBuildingId.getText().toString()));
                            landmarkNode.setDestination(Boolean.parseBoolean(addIsDestination.getText().toString()));
                            landmarkNode.setMultiLandmarkType(Integer.parseInt(addMultiLandmarkType.getText().toString()));
                            rc.sendLandmarkNode(landmarkNode);

                            final List<LandmarkNode> landmarkNodes = rc.getLandmarkNodeforRFID(addRfid.getText().toString());
                            if (landmarkNodes.size() != 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.replaceWith(landmarkNodes);
                                        list.setAdapter(adapter);
                                        //Toast add physical landmark node successfully
                                        textViewLandmarkNodeInfo.setText("Add new landmark node successfully.");
                                        buttonAdd.setTextColor(Color.parseColor("#000000"));
                                        buttonAdd.setClickable(false);
                                        scrollView.setVisibility(View.INVISIBLE);
                                        addBleMajor.setText("");
                                        addBleMinor.setText("");
                                        addIsDestination.setText("");
                                        addLandmarkName.setText("");
                                        addLandmarkNumber.setText("");
                                        addLocationLat.setText("");
                                        addLocationLng.setText("");
                                    }
                                });

                            }
                        }
                    };
                    Thread t = new Thread(serverComm);
                    t.start();

                }
            }
        });

        buttonAdd.setClickable(false);
    }

    /***********************************************************************************************
     GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap*****GoogleMap**
     ***********************************************************************************************
     */
    private GoogleMap mMap;                             // Might be null if Google Play services APK is not available.
    private Marker markerToIndicateLocation;

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapIndicateLocation))
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

    int initialZoom = 25;

    //RedMarkerToDisplayUserPosition
    double nowMarkerLat;
    double nowMarkerLng;

    private void setUpMap() {
        nowMarkerLat = buildingCentralLat;
        nowMarkerLng = buildingCentralLng;
        markerToIndicateLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(buildingCentralLat, buildingCentralLng))
                .title("User").draggable(true));
        markerToIndicateLocation.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(buildingCentralLat, buildingCentralLng), initialZoom));
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new CustomMapTileProvider(getResources().getAssets(),
                buildingFloorTilePath)));
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        addLocationLat.setText("" + marker.getPosition().latitude);
        addLocationLng.setText("" + marker.getPosition().longitude);
    }
}
