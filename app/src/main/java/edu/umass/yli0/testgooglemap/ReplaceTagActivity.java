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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.fiveglabs.percept.HTTP.RestClient;
import edu.fiveglabs.percept.Models.TAO.LandmarkNode;

public class ReplaceTagActivity extends AppCompatActivity {

    private int replaceFlag;                  //When users click "Replace" Button

    private LandmarkNodeListAdapter adapter;
    private long buildingId;
    private TextView textViewLandmarkNodeInfo;
    private TextView textViewButtonInfo;
    private Button buttonReplace;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_tag);

        textViewLandmarkNodeInfo = (TextView)findViewById(R.id.landmark_node_info);
        textViewButtonInfo = (TextView)findViewById(R.id.button_info);

        buttonReplace = (Button)findViewById(R.id.button_replace);

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
        list.setOnItemClickListener(createOnItemClickListener());

        getVariableFromMapsActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_replace_tag, menu);
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

        if(replaceFlag == 1) {
            replaceFlag = 0;
            Runnable serverComm = new Runnable(){
                @Override
                public void run() {

                    RestClient rc = new RestClient();
                    rc.deleteLandmarkNode(currentLandmarkNode);
                    currentLandmarkNode.setRfid(rfidDec);
                    rc.sendLandmarkNode(currentLandmarkNode);
                    final List<LandmarkNode> landmarkNodesAfterRemove = rc.getLandmarkNodeforRFID(rfidDec + "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.replaceWith(landmarkNodesAfterRemove);
                            list.setAdapter(adapter);
                            textViewLandmarkNodeInfo.setText("Replace successfully.");
                            textViewButtonInfo.setText("");
                        }
                    });
                }
            };
            Thread t = new Thread(serverComm);
            t.start();

        }else {

            List<LandmarkNode> landmarkNodes = getLandmarkNode(rfidDec /*1289711604214144l*/);
            int nowLandmarkNodeSize;
            nowLandmarkNodeSize = landmarkNodes.size();
            if(nowLandmarkNodeSize == 0){
                textViewLandmarkNodeInfo.setText("There is no record about this tag in DataBase.");
                list.setAdapter(null);

            }else{
                adapter.replaceWith(landmarkNodes);
                list.setAdapter(adapter);
                textViewLandmarkNodeInfo.setText("Scanned tags information in Database.");

            }
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
     * View Adapter On Item Click Listener
     * */
    LandmarkNode currentLandmarkNode;
    private AdapterView.OnItemClickListener createOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentLandmarkNode = adapter.getItem(position);
                buttonReplace.setClickable(true);
                buttonReplace.setTextColor(Color.parseColor("#2196F3"));
                textViewButtonInfo.setText("Please click buttons to replace this LandmarkNode.");
            }
        };
    }

    /**
     * Get Variable From MapsActivity
     * */
    void getVariableFromMapsActivity(){
        Bundle myBundle = getIntent().getExtras();
        buildingId = myBundle.getLong(WelcomeActivity.BUILDING_ID);
    }

    /**
     * Button OnClick
     * */
    void initializeButtonOnClick(){

        buttonReplace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                textViewButtonInfo.setText("Please scan the new NFC tag.");
                replaceFlag = 1;
                buttonReplace.setTextColor(Color.parseColor("#000000"));
                buttonReplace.setClickable(false);
            }
        });

        buttonReplace.setClickable(false);
    }

}
