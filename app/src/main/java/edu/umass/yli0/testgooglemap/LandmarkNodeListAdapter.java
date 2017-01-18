package edu.umass.yli0.testgooglemap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import edu.fiveglabs.percept.Models.TAO.LandmarkNode;

/**
 * Created by li on 8/21/2015.
 */
public class LandmarkNodeListAdapter extends BaseAdapter {

    private ArrayList<LandmarkNode> landmarkNodes;
    private LayoutInflater inflater;

    public LandmarkNodeListAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.landmarkNodes = new ArrayList<>();
    }

    public void replaceWith(Collection<LandmarkNode> newLandmarkNodes){
        this.landmarkNodes.clear();
        this.landmarkNodes.addAll(newLandmarkNodes);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return landmarkNodes.size();
    }

    @Override
    public LandmarkNode getItem(int position) {
        return landmarkNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    private void bind(LandmarkNode landmarkNode, View view){
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.bleTextView.setText(String.format("BLE: " + landmarkNode.getBle() + "    "));
        holder.rfidTextView.setText(String.format("RFID: " + landmarkNode.getRfid()));
        holder.locationStringTextView.setText("LocationString: " + landmarkNode.getLocationString());
        holder.idTextView.setText("Id: " + landmarkNode.getId() + "    ");
        holder.buildingIdTextView.setText("     BuildingId: " + landmarkNode.getBuildingId());
        holder.landmarkNameTextView.setText("LandmarkName: " + landmarkNode.getLandmarkName());
        holder.landmarkNumberTextView.setText("LandmarkNumber: " + landmarkNode.getLandmarkNumber());
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.landmark_item, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView bleTextView;
        final TextView rfidTextView;
        final TextView locationStringTextView;
        final TextView idTextView;
        final TextView buildingIdTextView;
        final TextView landmarkNameTextView;
        final TextView landmarkNumberTextView;

        ViewHolder(View view) {
            bleTextView = (TextView) view.findViewWithTag("ble");
            rfidTextView = (TextView) view.findViewWithTag("rfid");
            locationStringTextView = (TextView) view.findViewWithTag("location_string");
            idTextView = (TextView) view.findViewWithTag("id");
            buildingIdTextView = (TextView) view.findViewWithTag("building_id");
            landmarkNameTextView = (TextView) view.findViewWithTag("landmark_name");
            landmarkNumberTextView = (TextView) view.findViewWithTag("landmark_number");
        }
    }
}
