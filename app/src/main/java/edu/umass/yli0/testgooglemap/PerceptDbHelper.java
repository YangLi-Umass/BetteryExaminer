package edu.umass.yli0.testgooglemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by li on 7/17/2015.
 */
public class PerceptDbHelper extends SQLiteOpenHelper {

    public static final class BuildingContract implements BaseColumns{
        public static final String TABLE_NAME = "BUILDING";
        public static final String UID = "_id";
        
        public static final String COLUMN_NAME_BUILDING_ID = "Id";
        public static final String COLUMN_NAME_BUILDING_NAME = "Name";
        public static final String COLUMN_NAME_BUILDING_LOCATION = "Location";
        public static final String COLUMN_NAME_BUILDING_FLOOR = "Floor";

    }
    
    public static final class UnifiedLandmarkContract implements BaseColumns{
        public static final String TABLE_NAME = "UNIFIED_LANDMARK";
        public static final String UID = "_id";
        
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_ID = "Id";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_NAME = "Name";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_COUNT = "Count";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_VERTICAL = "Vertical";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_LAYER = "Layer";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_OPENING = "Opening";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_CLOSE_INDICATE = "CloseIndicate";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_FIRE_DOOR_INDICATE = "FireDoorIndicate";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_HALLWAY_INDICATE = "HallwayIndicate";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_BUILDING_ID = "BuildingId";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_FLOOR = "Floor";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_DETAILED_NAME = "DetailedName";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_SHORTWALL = "Shortwall";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_ALTITUDE = "Altitude";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_TEXTURE = "Texture";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_ENVIRONMENT = "Environment";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_CLOCK_DIRECTION = "ClockDirection";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_TRAVEL_BY_INDICATE = "TravelByIndicate";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_RFID = "RFID";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NAME = "LandmarkName";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NUMBER = "LandmarkNumber";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_LOCATION_STRING = "LocationString";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_LOCATION = "Location";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_IS_DESTINATION = "IsDestination";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_MULTI_LANDMARK_TYPE = "MultiLandmarkType";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_TAG_LOCATION = "TagLocation";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_TRANSFER_ID = "TransferId";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_TYPE = "LandmarkType";
        public static final String COLUMN_NAME_UNIFIED_LANDMARK_IS_VIRTUAL_TAG = "IsVirtualTag";
    }

    public static final class FloorTileContract implements BaseColumns{
        public static final String TABLE_NAME = "FLOOR_TILE";
        public static final String UID = "_id";

        public static final String COLUMN_NAME_FLOOR_TILE_ID = "Id";
        public static final String COLUMN_NAME_FLOOR_TILE_BUILDING_ID = "BuildingId";
        public static final String COLUMN_NAME_FLOOR_TILE_FLOOR = "Floor";
        public static final String COLUMN_NAME_FLOOR_TILE_TILE_URL = "TileUrl";
        public static final String COLUMN_NAME_FLOOR_TILE_DISPLAY_NAME = "DisplayName";

    }

    public static final String DATABASE_NAME = "PERCEPT_Maintenance.db";
    public static final int DATABASE_VERSION = 1;

    //The Android's default system path of your application database.
    private static String DATABASE_PATH = "/data/data/edu.umass.yli0.testgooglemap/databases/";

    private static final String TEXT_TYPE = " TEXT";            //similar to String in Java
    private static final String INTEGER_TYPE = " INTEGER";      //similar to long in Java
    private static final String COMMA_SEP = ",";

    private static final String BUILDING_CREATE_ENTRIES =
            "CREATE TABLE " + BuildingContract.TABLE_NAME + " (" +
                    BuildingContract.UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BuildingContract.COLUMN_NAME_BUILDING_ID + INTEGER_TYPE + COMMA_SEP +
                    BuildingContract.COLUMN_NAME_BUILDING_NAME + TEXT_TYPE + COMMA_SEP +
                    BuildingContract.COLUMN_NAME_BUILDING_LOCATION + TEXT_TYPE + COMMA_SEP +
                    BuildingContract.COLUMN_NAME_BUILDING_FLOOR + INTEGER_TYPE +
                    " )";


    private static final String BUILDING_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BuildingContract.TABLE_NAME;
    
    private static final String UNIFIED_LANDMARK_CREATE_ENTRIES =
            "CREATE TABLE " + UnifiedLandmarkContract.TABLE_NAME + " (" +
                    UnifiedLandmarkContract.UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ID + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_NAME + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_COUNT + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_VERTICAL + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LAYER + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_OPENING + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOSE_INDICATE + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FIRE_DOOR_INDICATE + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_HALLWAY_INDICATE + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_BUILDING_ID + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FLOOR + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_DETAILED_NAME + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_SHORTWALL + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ALTITUDE + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TEXTURE + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ENVIRONMENT + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOCK_DIRECTION + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRAVEL_BY_INDICATE + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_RFID + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NAME + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION_STRING + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_DESTINATION + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_MULTI_LANDMARK_TYPE + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TAG_LOCATION + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRANSFER_ID + INTEGER_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_TYPE + TEXT_TYPE + COMMA_SEP +
                    UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_VIRTUAL_TAG + INTEGER_TYPE +
                    " )";

    private static final String UNIFIED_LANDMARK_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UnifiedLandmarkContract.TABLE_NAME;

    private static final String FLOOR_TILE_CREATE_ENTRIES =
            "CREATE TABLE " + FloorTileContract.TABLE_NAME + " (" +
                    FloorTileContract.UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FloorTileContract.COLUMN_NAME_FLOOR_TILE_ID + INTEGER_TYPE + COMMA_SEP +
                    FloorTileContract.COLUMN_NAME_FLOOR_TILE_BUILDING_ID + INTEGER_TYPE + COMMA_SEP +
                    FloorTileContract.COLUMN_NAME_FLOOR_TILE_FLOOR + INTEGER_TYPE + COMMA_SEP +
                    FloorTileContract.COLUMN_NAME_FLOOR_TILE_TILE_URL + TEXT_TYPE + COMMA_SEP +
                    FloorTileContract.COLUMN_NAME_FLOOR_TILE_DISPLAY_NAME + TEXT_TYPE +
                    " )";


    private static final String FLOOR_TILE_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FloorTileContract.TABLE_NAME;

    public PerceptDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
        Log.d("Database", "Constructor called.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUILDING_CREATE_ENTRIES);
        db.execSQL(UNIFIED_LANDMARK_CREATE_ENTRIES);
        db.execSQL(FLOOR_TILE_CREATE_ENTRIES);
        Log.d("Database", "OnCreate called.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.d("Database", "OnUpgrade called.");
        db.execSQL(BUILDING_DELETE_ENTRIES);
        db.execSQL(UNIFIED_LANDMARK_DELETE_ENTRIES);
        db.execSQL(FLOOR_TILE_DELETE_ENTRIES);
        onCreate(db);
    }

    /***********************************************************************************************
     ***********************************************************************************************
     * ship an Android application with a database
     * */

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

}
