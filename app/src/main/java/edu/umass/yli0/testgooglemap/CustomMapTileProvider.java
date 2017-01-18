package edu.umass.yli0.testgooglemap;

/**
 * Created by li on 7/23/2015.
 */

import android.content.res.AssetManager;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomMapTileProvider implements TileProvider {
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;

    private AssetManager mAssets;
    private String mTileFilePath;

    public CustomMapTileProvider(AssetManager assets, String tileFilePath) {
        mAssets = assets;
        mTileFilePath = tileFilePath;
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        //y = fixYCoordinate(y, zoom);
        byte[] image = readTileImage(x, y, zoom);
        return image == null ? null : new Tile(TILE_WIDTH, TILE_HEIGHT, image);
    }

    private byte[] readTileImage(int x, int y, int zoom) {
        InputStream in = null;
        //FileInputStream in = null;
        ByteArrayOutputStream buffer = null;

        try {

            in = mAssets.open(getTileFile(x, y, zoom));
            buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[BUFFER_SIZE];

            while ((nRead = in.read(data, 0, BUFFER_SIZE)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception ignored) {
                }
            if (buffer != null)
                try {
                    buffer.close();
                } catch (Exception ignored) {
                }
        }
    }

    /*private int fixYCoordinate(int y, int zoom) {
        int size = 1 << zoom; // size = 2^zoom
        return size - 1 - y;
    }*/

    private String getTileFile(int x, int y, int zoom) {
        //File sdcard = Environment.getExternalStorageDirectory();
        //String tileFile = "/Apeiara/" + zoom + '/' + x + '/' + y + ".png";
        //File file = new File(sdcard,tileFile);
        String temp = "http://perceptdata.azurewebsites.net/";
        //String debug = mTileFilePath.replace(temp,"");

        return mTileFilePath.replace(temp,"") + zoom + '/' + x + '/' + y + ".png";

        //return "Blueprints/UMass/Knowles/floor3/" + zoom + '/' + x + '/' + y + ".png";
    }
}
