package edu.umass.yli0.testgooglemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.fiveglabs.percept.BaseModels.BuildingBase;
import edu.fiveglabs.percept.BaseModels.FloorTileBase;
import edu.fiveglabs.percept.BaseModels.UnifiedLandmarkBase;

/**
 * Created by li on 7/20/2015.
 */
public class PerceptMaintenanceDataSource {

    //Database fields
    private SQLiteDatabase sqLiteDatabase;
    private PerceptDbHelper mySQLiteHelper;
    private String [] buildingAllColumns = {
            PerceptDbHelper.BuildingContract.UID,
            PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_ID,
            PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_NAME,
            PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_LOCATION,
            PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_FLOOR
            };
    private String [] unifiedLandmarkAllColumns = {
            PerceptDbHelper.UnifiedLandmarkContract.UID,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ID,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_NAME,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_COUNT,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_VERTICAL,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LAYER,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_OPENING,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOSE_INDICATE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FIRE_DOOR_INDICATE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_HALLWAY_INDICATE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_BUILDING_ID,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FLOOR,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_DETAILED_NAME,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_SHORTWALL,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ALTITUDE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TEXTURE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ENVIRONMENT,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOCK_DIRECTION,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRAVEL_BY_INDICATE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_RFID,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NAME,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NUMBER,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION_STRING,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_DESTINATION,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_MULTI_LANDMARK_TYPE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TAG_LOCATION,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRANSFER_ID,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_TYPE,
            PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_VIRTUAL_TAG,
    };

    private String [] floorTileAllColumns = {
            PerceptDbHelper.FloorTileContract.UID,
            PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_ID,
            PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_BUILDING_ID,
            PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_FLOOR,
            PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_TILE_URL,
            PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_DISPLAY_NAME
    };

    public PerceptMaintenanceDataSource(Context context){
        mySQLiteHelper = new PerceptDbHelper(context);
    }

    public void open() {
        try {
            sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        sqLiteDatabase.close();
    }

    /**
     * Building Table
     * */
    public BuildingBase createBuildingBase (BuildingBase _buildingBase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_ID, _buildingBase.getId());
        contentValues.put(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_NAME, _buildingBase.getName());
        contentValues.put(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_LOCATION, _buildingBase.getLocationString());
        contentValues.put(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_FLOOR, _buildingBase.getFloor());

        long insertId = sqLiteDatabase.insert(PerceptDbHelper.BuildingContract.TABLE_NAME,null,contentValues);
        Cursor cursor = sqLiteDatabase.query(PerceptDbHelper.BuildingContract.TABLE_NAME,
                buildingAllColumns, PerceptDbHelper.BuildingContract.UID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        BuildingBase newBuildingBase = cursorToBuildingBase(cursor);
        cursor.close();
        return  newBuildingBase;
    }

    public void deleteBuildingBase(BuildingBase _buildingBase){
        long id = _buildingBase.getId();
        sqLiteDatabase.delete(PerceptDbHelper.BuildingContract.TABLE_NAME,
                PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_ID + " = " + id, null);
    }

    public void deleteAllBuildingBases(){
        sqLiteDatabase.delete(PerceptDbHelper.BuildingContract.TABLE_NAME, null, null);
    }

    public List<BuildingBase> getAllBuildingBases(){
        List<BuildingBase> buildingBases = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(PerceptDbHelper.BuildingContract.TABLE_NAME,
                buildingAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BuildingBase buildingBase = cursorToBuildingBase(cursor);
            buildingBases.add(buildingBase);
            cursor.moveToNext();
        }

        //make sure to close the cursor
        cursor.close();
        return buildingBases;
    }

    private BuildingBase cursorToBuildingBase (Cursor _cursor){
        BuildingBase buildingBase = new BuildingBase();
        int buildingIdIndex = _cursor.getColumnIndex(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_ID);
        int buildingNameIndex = _cursor.getColumnIndex(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_NAME);
        int buildingLocationIndex = _cursor.getColumnIndex(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_LOCATION);
        int buildingFloorIndex = _cursor.getColumnIndex(PerceptDbHelper.BuildingContract.COLUMN_NAME_BUILDING_FLOOR);

        buildingBase.setId(_cursor.getLong(buildingIdIndex));
        buildingBase.setName(_cursor.getString(buildingNameIndex));
        buildingBase.setLocationString(_cursor.getString(buildingLocationIndex));
        buildingBase.setFloor(_cursor.getInt(buildingFloorIndex));
        return buildingBase;
    }

    /**
     * UnifiedLandmark Table
     * */

    public UnifiedLandmarkBase createUnifiedLandmarkBase (UnifiedLandmarkBase _unifiedLandmarkBase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ID, _unifiedLandmarkBase.getId());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_NAME, _unifiedLandmarkBase.getName());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_COUNT, _unifiedLandmarkBase.getCount());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_VERTICAL, _unifiedLandmarkBase.getVertical());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LAYER, _unifiedLandmarkBase.getLayer());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_OPENING, _unifiedLandmarkBase.getOpening());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOSE_INDICATE, _unifiedLandmarkBase.getCloseIndicate());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FIRE_DOOR_INDICATE, _unifiedLandmarkBase.getFireDoorIndicate());
        int hallwayIndicate = (_unifiedLandmarkBase.isHallwayIndicate()) ? 1 : 0;
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_HALLWAY_INDICATE, hallwayIndicate);
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_BUILDING_ID, _unifiedLandmarkBase.getBuildingId());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FLOOR, _unifiedLandmarkBase.getFloor());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_DETAILED_NAME, _unifiedLandmarkBase.getDetailedName());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_SHORTWALL, _unifiedLandmarkBase.getShortwall());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ALTITUDE, _unifiedLandmarkBase.getAltitude());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TEXTURE, _unifiedLandmarkBase.getTexture());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ENVIRONMENT, _unifiedLandmarkBase.getEnvironment());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOCK_DIRECTION, _unifiedLandmarkBase.getClockDirection());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRAVEL_BY_INDICATE, _unifiedLandmarkBase.getTravelByIndicate());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_RFID, _unifiedLandmarkBase.getRfid());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NAME, _unifiedLandmarkBase.getLandmarkName());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NUMBER, _unifiedLandmarkBase.getLandmarkNumber());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION_STRING, _unifiedLandmarkBase.getLocationString());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION, _unifiedLandmarkBase.getLocation());
        int destinationIndicate = (_unifiedLandmarkBase.isDestination()) ? 1 : 0;
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_DESTINATION, destinationIndicate);
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_MULTI_LANDMARK_TYPE, _unifiedLandmarkBase.getMultiLandmarkType());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TAG_LOCATION, _unifiedLandmarkBase.getTagLocation());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRANSFER_ID, _unifiedLandmarkBase.getTransferId());
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_TYPE, _unifiedLandmarkBase.getLandmarkType());
        int virtualTagIndicate = (_unifiedLandmarkBase.isVirtualTag()) ? 1 : 0;
        contentValues.put(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_VIRTUAL_TAG, virtualTagIndicate);

        long insertId = sqLiteDatabase.insert(PerceptDbHelper.UnifiedLandmarkContract.TABLE_NAME,null,contentValues);
        Cursor cursor = sqLiteDatabase.query(PerceptDbHelper.UnifiedLandmarkContract.TABLE_NAME,
                unifiedLandmarkAllColumns, PerceptDbHelper.UnifiedLandmarkContract.UID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        UnifiedLandmarkBase unifiedLandmarkBase = cursorToUnifiedLandmarkBase(cursor);
        cursor.close();
        return  unifiedLandmarkBase;
    }

    public void deleteUnifiedLandmarkBase(UnifiedLandmarkBase _unifiedLandmarkBase){
        long id = _unifiedLandmarkBase.getId();
        sqLiteDatabase.delete(PerceptDbHelper.UnifiedLandmarkContract.TABLE_NAME,
                PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ID + " = " + id, null);
    }

    public void deleteAllUnifiedLandmarkBases(){
        sqLiteDatabase.delete(PerceptDbHelper.UnifiedLandmarkContract.TABLE_NAME, null, null);
    }

    public List<UnifiedLandmarkBase> getUnifiedLandmarkBasesByBuildingId(long _buildingId){
        List<UnifiedLandmarkBase> unifiedLandmarkBases = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(PerceptDbHelper.UnifiedLandmarkContract.TABLE_NAME,
                unifiedLandmarkAllColumns, PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_BUILDING_ID + " = " + _buildingId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            UnifiedLandmarkBase unifiedLandmarkBase = cursorToUnifiedLandmarkBase(cursor);
            unifiedLandmarkBases.add(unifiedLandmarkBase);
            cursor.moveToNext();
        }

        //make sure to close the cursor
        cursor.close();
        return unifiedLandmarkBases;
    }

    private UnifiedLandmarkBase cursorToUnifiedLandmarkBase (Cursor _cursor){
        UnifiedLandmarkBase unifiedLandmarkBase = new UnifiedLandmarkBase();
        int unifiedLandmarkIdIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ID);
        int unifiedLandmarkNameIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_NAME);
        int unifiedLandmarkCountIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_COUNT);
        int unifiedLandmarkVerticalIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_VERTICAL);
        int unifiedLandmarkLayerIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LAYER);
        int unifiedLandmarkOpeningIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_OPENING);
        int unifiedLandmarkCloseIndicateIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOSE_INDICATE);
        int unifiedLandmarkFireDoorIndicateIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FIRE_DOOR_INDICATE);
        int unifiedLandmarkHallwayIndicateIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_HALLWAY_INDICATE);
        int unifiedLandmarkBuildingIdIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_BUILDING_ID);
        int unifiedLandmarkFloorIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_FLOOR);
        int unifiedLandmarkDetailedNameIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_DETAILED_NAME);
        int unifiedLandmarkShortwallIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_SHORTWALL);
        int unifiedLandmarkAltitudeIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ALTITUDE);
        int unifiedLandmarkTextureIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TEXTURE);
        int unifiedLandmarkEnvironmentIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_ENVIRONMENT);
        int unifiedLandmarkClockDirectionIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_CLOCK_DIRECTION);
        int unifiedLandmarkTravelByIndicateIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRAVEL_BY_INDICATE);
        int unifiedLandmarkRfidIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_RFID);
        int unifiedLandmarkLandmarkNameIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NAME);
        int unifiedLandmarkLandmarkNumberIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_NUMBER);
        int unifiedLandmarkLocationStringIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION_STRING);
        int unifiedLandmarkLocationIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LOCATION);
        int unifiedLandmarkIsDestinationIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_DESTINATION);
        int unifiedLandmarkMultiLandmarkTypeIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_MULTI_LANDMARK_TYPE);
        int unifiedLandmarkTagLocationIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TAG_LOCATION);
        int unifiedLandmarkTransferIdIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_TRANSFER_ID);
        int unifiedLandmarkLandmarkTypeIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_LANDMARK_TYPE);
        int unifiedLandmarkIsVirtualTagIndex = _cursor.getColumnIndex(PerceptDbHelper.UnifiedLandmarkContract.COLUMN_NAME_UNIFIED_LANDMARK_IS_VIRTUAL_TAG);


        unifiedLandmarkBase.setId(_cursor.getLong(unifiedLandmarkIdIndex));
        unifiedLandmarkBase.setName(_cursor.getString(unifiedLandmarkNameIndex));
        unifiedLandmarkBase.setCount(_cursor.getDouble(unifiedLandmarkCountIndex));
        unifiedLandmarkBase.setVertical(_cursor.getDouble(unifiedLandmarkVerticalIndex));
        unifiedLandmarkBase.setLayer(_cursor.getDouble(unifiedLandmarkLayerIndex));
        unifiedLandmarkBase.setOpening(_cursor.getDouble(unifiedLandmarkOpeningIndex));
        unifiedLandmarkBase.setCloseIndicate(_cursor.getDouble(unifiedLandmarkCloseIndicateIndex));
        unifiedLandmarkBase.setFireDoorIndicate(_cursor.getDouble(unifiedLandmarkFireDoorIndicateIndex));
        unifiedLandmarkBase.setHallwayIndicate(Boolean.parseBoolean(_cursor.getString(unifiedLandmarkHallwayIndicateIndex)));
        unifiedLandmarkBase.setBuildingId(_cursor.getLong(unifiedLandmarkBuildingIdIndex));
        unifiedLandmarkBase.setFloor(_cursor.getInt(unifiedLandmarkFloorIndex));
        unifiedLandmarkBase.setDetailedName(_cursor.getString(unifiedLandmarkDetailedNameIndex));
        unifiedLandmarkBase.setShortwall(_cursor.getLong(unifiedLandmarkShortwallIndex));
        unifiedLandmarkBase.setAltitude(_cursor.getDouble(unifiedLandmarkAltitudeIndex));
        unifiedLandmarkBase.setTexture(_cursor.getString(unifiedLandmarkTextureIndex));
        unifiedLandmarkBase.setEnvironment(_cursor.getString(unifiedLandmarkEnvironmentIndex));
        unifiedLandmarkBase.setClockDirection(_cursor.getInt(unifiedLandmarkClockDirectionIndex));
        unifiedLandmarkBase.setTravelByIndicate(_cursor.getInt(unifiedLandmarkTravelByIndicateIndex));
        unifiedLandmarkBase.setRfid(_cursor.getLong(unifiedLandmarkRfidIndex));
        unifiedLandmarkBase.setLandmarkName(_cursor.getString(unifiedLandmarkLandmarkNameIndex));
        unifiedLandmarkBase.setLandmarkNumber(_cursor.getLong(unifiedLandmarkLandmarkNumberIndex));
        unifiedLandmarkBase.setLocationString(_cursor.getString(unifiedLandmarkLocationStringIndex));
        unifiedLandmarkBase.setLocation(_cursor.getString(unifiedLandmarkLocationIndex));
        unifiedLandmarkBase.setIsDestination(Boolean.parseBoolean(_cursor.getString(unifiedLandmarkIsDestinationIndex)));
        unifiedLandmarkBase.setMultiLandmarkType(_cursor.getString(unifiedLandmarkMultiLandmarkTypeIndex));
        unifiedLandmarkBase.setTagLocation(_cursor.getString(unifiedLandmarkTagLocationIndex));
        unifiedLandmarkBase.setTransferId(_cursor.getLong(unifiedLandmarkTransferIdIndex));
        unifiedLandmarkBase.setLandmarkType(_cursor.getString(unifiedLandmarkLandmarkTypeIndex));
        unifiedLandmarkBase.setIsVirtualTag(Boolean.parseBoolean(_cursor.getString(unifiedLandmarkIsVirtualTagIndex)));

        return unifiedLandmarkBase;
    }

    /**
     * FloorTile Table
     * */
    public FloorTileBase createFloorTileBase (FloorTileBase _floorTileBase){

        ContentValues contentValues = new ContentValues();
        contentValues.put(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_ID, _floorTileBase.getId());
        contentValues.put(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_BUILDING_ID, _floorTileBase.getBuildingId());
        contentValues.put(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_FLOOR, _floorTileBase.getFloor());
        contentValues.put(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_TILE_URL, _floorTileBase.getTileUrl());
        contentValues.put(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_DISPLAY_NAME, _floorTileBase.getDisplayName());

        long insertId = sqLiteDatabase.insert(PerceptDbHelper.FloorTileContract.TABLE_NAME, null, contentValues);
        Cursor cursor = sqLiteDatabase.query(PerceptDbHelper.FloorTileContract.TABLE_NAME,
                floorTileAllColumns, PerceptDbHelper.FloorTileContract.UID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FloorTileBase newFloorTileBase = cursorToFloorTileBase(cursor);
        cursor.close();
        return  newFloorTileBase;
    }

    public void deleteFloorTileBase(FloorTileBase _floorTileBase){
        long id = _floorTileBase.getId();
        sqLiteDatabase.delete(PerceptDbHelper.FloorTileContract.TABLE_NAME,
                PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_ID + " = " + id, null);
    }

    public void deleteAllFloorTileBase(){
        sqLiteDatabase.delete(PerceptDbHelper.FloorTileContract.TABLE_NAME, null, null);
    }

    public List<FloorTileBase> getAllFloorTileBasesByBuildingId(long _buildingId){
        List<FloorTileBase> floorTileBases = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(PerceptDbHelper.FloorTileContract.TABLE_NAME,
                floorTileAllColumns, PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_BUILDING_ID + " = " + _buildingId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            FloorTileBase floorTileBase = cursorToFloorTileBase(cursor);
            floorTileBases.add(floorTileBase);
            cursor.moveToNext();
        }

        //make sure to close the cursor
        cursor.close();
        return floorTileBases;
    }

    private FloorTileBase cursorToFloorTileBase (Cursor _cursor){
        FloorTileBase floorTileBase = new FloorTileBase();


        int floorTileIdIndex = _cursor.getColumnIndex(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_ID);
        int floorTileBuildingIdIndex = _cursor.getColumnIndex(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_BUILDING_ID);
        int floorTileFloorIndex = _cursor.getColumnIndex(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_FLOOR);
        int floorTileUrlIndex = _cursor.getColumnIndex(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_TILE_URL);
        int floorTileDisplayNameIdIndex = _cursor.getColumnIndex(PerceptDbHelper.FloorTileContract.COLUMN_NAME_FLOOR_TILE_DISPLAY_NAME);


        floorTileBase.setId(_cursor.getLong(floorTileIdIndex));
        floorTileBase.setBuildingId(_cursor.getLong(floorTileBuildingIdIndex));
        floorTileBase.setFloor(_cursor.getInt(floorTileFloorIndex));
        floorTileBase.setTileUrl(_cursor.getString(floorTileUrlIndex));
        floorTileBase.setDisplayName(_cursor.getString(floorTileDisplayNameIdIndex));

        return floorTileBase;
    }

}
