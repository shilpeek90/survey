package com.example.canesurvey;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.GnssMeasurementsEvent;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.canesurvey.Async.GetSpinnerData;
import com.example.canesurvey.Comman.CommanData;
import com.example.canesurvey.util.GpsTestUtil;
import com.example.canesurvey.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


public class CapturePlot extends Fragment implements GpsTestListener, View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    public String TAG = "CapturePlotFragment";
    private static final double EARTH_RADIUS = 6371000;// meters

    private OnFragmentInteractionListener mListener;
    private LinearLayout panel_location;
    private TextView txtlat1, txtlat2, txtlat3, txtlat4, txtlon1, txtlon2, txtlon3, txtlon4, txtmtr1, txtmtr2, txtmtr3, txtmtr4;
    private TextView txtarea, txtcurrentlatlon, txtaccu;
    private EditText mPlotVillCode, mPlotVillName, mVarietycode, mVarietyname, mCanetypeCode, mCanetypeName,
            mPlantationDate , mGVillCode, mGVillName,mFathername, mGCode, mGName, mMobileno, mSharePercent;
    private Spinner  mPlantation,mIrrigation ;

    private Resources mRes;
    private int currentcorer = 0;
    private double locationaccu;
    private String lat, lon;
    private Location locationA, locationB;
    List<Location> allLocations = new ArrayList<>();
    private Button btncapturelocation;
    private long mFixTime;

    StringBuilder sbuilder = null;
    View v, currenttextchangecontrol;
    private static final String METERS = Application.get().getResources().getStringArray(R.array.preferred_distance_units_values)[0];
    private static final String METERS_PER_SECOND = Application.get().getResources().getStringArray(R.array.preferred_speed_units_values)[0];
    private static final String KILOMETERS_PER_HOUR = Application.get().getResources().getStringArray(R.array.preferred_speed_units_values)[1];
    String mPrefDistanceUnits;
    String mPrefSpeedUnits;

    ArrayAdapter plantationadapter,irrigationadapter;
    ArrayList<String> plantationlist,irrigationlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRes = getResources();
        setupUnitPreferences();

        View v = inflater.inflate(R.layout.fragment_capture_plot, container, false);

        LoadControls(v);
        AddClickListener();
        ExecuteTasks();


        return v;
    }

    private void ExecuteTasks() {
        /*GetSpinnerData spinnerdatatask=new GetSpinnerData(plantationlist,plantationadapter,CommanData.conn.plantationMethod.getTableName(),false);
        spinnerdatatask.execute();

        GetSpinnerData spinnerdata2task=new GetSpinnerData(irrigationlist,plantationadapter,CommanData.conn.irrigation.getTableName(),false);
        spinnerdata2task.execute();*/
    }

    private void AddClickListener() {
        btncapturelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateCornerLatLon();
            }
        });
        //MainActivity.Init();
        MainActivity.getInstance().addGpsListener(this);
        mPlotVillCode.addTextChangedListener(this);
        mVarietycode.addTextChangedListener(this);

        mGVillCode.addTextChangedListener(this);
        mGCode.addTextChangedListener(this);
        mGCode.setOnFocusChangeListener(this);
        mPlotVillCode.setOnFocusChangeListener(this);
        mVarietycode.setOnFocusChangeListener(this);
        mCanetypeCode.setOnFocusChangeListener(this);
        mGVillCode.setOnFocusChangeListener(this);

        plantationlist = CommanData.conn.plantationMethod.getNameList();
        plantationadapter=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_dropdown_item,plantationlist);
        mPlantation.setAdapter(plantationadapter);

        irrigationlist= CommanData.conn.irrigation.getNameList();
        irrigationadapter=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_dropdown_item,irrigationlist);
        mIrrigation.setAdapter(irrigationadapter);
    }

    private void LoadControls(View v) {
        txtlat1 = v.findViewById(R.id.txt_lat1);
        txtlat2 = v.findViewById(R.id.txt_lat2);
        txtlat3 = v.findViewById(R.id.txt_lat3);
        txtlat4 = v.findViewById(R.id.txt_lat4);
        txtlon1 = v.findViewById(R.id.txt_lon1);
        txtlon2 = v.findViewById(R.id.txt_lon2);
        txtlon3 = v.findViewById(R.id.txt_lon3);
        txtlon4 = v.findViewById(R.id.txt_lon4);
        txtmtr1 = v.findViewById(R.id.txt_mtr1);
        txtmtr2 = v.findViewById(R.id.txt_mtr2);
        txtmtr3 = v.findViewById(R.id.txt_mtr3);
        txtmtr4 = v.findViewById(R.id.txt_mtr4);
        txtarea = v.findViewById(R.id.txt_area);
        txtcurrentlatlon = v.findViewById(R.id.txt_currentlatlon);
        txtaccu = v.findViewById(R.id.txt_accu);
        btncapturelocation = v.findViewById(R.id.btn_capturelocation);
        panel_location = v.findViewById(R.id.panel_latlong);

        /// get all controls field
        mPlotVillCode = v.findViewById(R.id.cplotvillcode);
        mPlotVillName = v.findViewById(R.id.cPlotVillName);
        mVarietycode = v.findViewById(R.id.cvarietycode);
        mVarietyname = v.findViewById(R.id.cVarietyName);
        mCanetypeCode = v.findViewById(R.id.cCaneTypecode);
        mCanetypeName = v.findViewById(R.id.cCanetypeName);
        mPlantationDate = v.findViewById(R.id.cPlantationDate);
        mGVillCode = v.findViewById(R.id.cGVillCode);
        mGVillName = v.findViewById(R.id.cGVillName);
        mGCode = v.findViewById(R.id.cGrowerCode);
        mGName = v.findViewById(R.id.cGrowerName);
        mMobileno = v.findViewById(R.id.cMobileno);
        mSharePercent = v.findViewById(R.id.cSharePercent);
        mFathername=v.findViewById(R.id.cFatherName);
        mPlantation = v.findViewById(R.id.cSpinnerPlantation);
        mIrrigation = v.findViewById(R.id.cSpinnerIrrigation);
        plantationlist=new ArrayList<>();
        irrigationlist=new ArrayList<>();
    }

    private void setupUnitPreferences() {
        SharedPreferences settings = Application.getPrefs();
        Application app = Application.get();

        mPrefDistanceUnits = settings
                .getString(app.getString(R.string.pref_key_preferred_distance_units_v2), METERS);
        mPrefSpeedUnits = settings
                .getString(app.getString(R.string.pref_key_preferred_speed_units_v2), METERS_PER_SECOND);

        locationA = new Location("Point A");
        locationB = new Location("Pint B");
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void gpsStart() {

    }

    @Override
    public void gpsStop() {

    }

    @Override
    public void onGpsStatusChanged(int event, GpsStatus status) {

    }

    @Override
    public void onGnssFirstFix(int ttffMillis) {

    }

    @Override
    public void onSatelliteStatusChanged(GnssStatus status) {

    }

    @Override
    public void onGnssStarted() {

    }

    @Override
    public void onGnssStopped() {

    }

    @Override
    public void onGnssMeasurementsReceived(GnssMeasurementsEvent event) {

    }

    @Override
    public void onOrientationChanged(double orientation, double tilt) {

    }

    @Override
    public void onNmeaMessage(String message, long timestamp) {

    }

    @Override
    public void onLocationChanged(Location location) {

        sbuilder = new StringBuilder();
        sbuilder.append((mRes.getString(R.string.gps_latitude_value, location.getLatitude())));
        sbuilder.append(" | ");
        sbuilder.append((mRes.getString(R.string.gps_longitude_value, location.getLongitude())));
        txtcurrentlatlon.setText(sbuilder.toString());

        locationA = location;
        sbuilder = null;
        mFixTime = location.getTime();

        if (location.hasSpeed()) {
            if (mPrefSpeedUnits.equalsIgnoreCase(METERS_PER_SECOND)) {
                //   mSpeedView.setText(mRes.getString(R.string.gps_speed_value_meters_sec, location.getSpeed()));
            } else if (mPrefSpeedUnits.equalsIgnoreCase(KILOMETERS_PER_HOUR)) {
                //  mSpeedView.setText(mRes.getString(R.string.gps_speed_value_kilometers_hour, UIUtils.toKilometersPerHour(location.getSpeed())));
            } else {
                // Miles per hour
                //  mSpeedView.setText(mRes.getString(R.string.gps_speed_value_miles_hour, UIUtils.toMilesPerHour(location.getSpeed())));
            }
        } else {
            //  mSpeedView.setText("");
        }
        if (location.hasBearing()) {
            //  mBearingView.setText(mRes.getString(R.string.gps_bearing_value, location.getBearing()));
        } else {
            //  mBearingView.setText("");
        }
        updateLocationAccuracies(location);
        //updateSpeedAndBearingAccuracies(location);
        //updateFixTime();
    }

    private void updateLocationAccuracies(Location location) {
        if (GpsTestUtil.isVerticalAccuracySupported()) {
            // mHorVertAccuracyLabelView.setText(R.string.gps_hor_and_vert_accuracy_label);
            if (location.hasAccuracy() || location.hasVerticalAccuracy()) {
                if (mPrefDistanceUnits.equalsIgnoreCase(METERS)) {
                    /*mHorVertAccuracyView.setText(mRes.getString(R.string.gps_hor_and_vert_accuracy_value_meters,
                            location.getAccuracy(),
                            location.getVerticalAccuracyMeters()));*/

                    txtaccu.setText(mRes.getString(R.string.gps_hor_and_vert_accuracy_value_meters,
                            location.getAccuracy(),
                            location.getVerticalAccuracyMeters()));
                } else {
                    // Feet
                 /*   mHorVertAccuracyView.setText(mRes.getString(R.string.gps_hor_and_vert_accuracy_value_feet,
                            UIUtils.toFeet(location.getAccuracy()),
                            UIUtils.toFeet(location.getVerticalAccuracyMeters())));*/
                    txtaccu.setText(mRes.getString(R.string.gps_hor_and_vert_accuracy_value_feet,
                            UIUtils.toFeet(location.getAccuracy()),
                            UIUtils.toFeet(location.getVerticalAccuracyMeters())));
                }
            } else {
                // mHorVertAccuracyView.setText("");
                txtaccu.setText("");
                locationaccu = 100;
            }
        } else {
            if (location.hasAccuracy()) {
                if (mPrefDistanceUnits.equalsIgnoreCase(METERS)) {
                    // mHorVertAccuracyView.setText(mRes.getString(R.string.gps_accuracy_value_meters, location.getAccuracy()));
                    txtaccu.setText(mRes.getString(R.string.gps_accuracy_value_meters, location.getAccuracy()));
                } else {
                    // Feet
                    // mHorVertAccuracyView.setText(mRes.getString(R.string.gps_accuracy_value_feet, UIUtils.toFeet(location.getAccuracy())));
                    txtaccu.setText(mRes.getString(R.string.gps_accuracy_value_feet, UIUtils.toFeet(location.getAccuracy())));
                }
            } else {
                // mHorVertAccuracyView.setText("");
                txtaccu.setText("");
            }
        }

        acccharat = txtaccu.getText().toString().indexOf("/");
        locationaccu = Double.valueOf(txtaccu.getText().toString().substring(acccharat + 1, (txtaccu.getText().toString().length() - 1)));
        if (locationaccu <= 20 && currentcorer < 4) {
            panel_location.setBackgroundColor(mRes.getColor(R.color.green));
            btncapturelocation.setEnabled(true);
        } else {
            panel_location.setBackgroundColor(mRes.getColor(R.color.red));
            btncapturelocation.setEnabled(false);
        }
    }

    int acccharat = 0;


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {


    }

    private void UpdateCornerLatLon() {
        switch (currentcorer) {
            case 0:
                txtlat1.setText(String.valueOf(locationA.getLatitude()));
                txtlon1.setText(String.valueOf(locationA.getLongitude()));
                allLocations.add(locationA);
                locationA = new Location("PointB");
                currentcorer = 1;
                break;
            case 1:
                allLocations.add(locationA);

                locationB = allLocations.get(0);
                txtmtr1.setText(String.valueOf(locationA.distanceTo(locationB)));
                txtlat2.setText(String.valueOf(locationA.getLatitude()));
                txtlon2.setText(String.valueOf(locationA.getLongitude()));
                locationA = new Location("Pointc");
                currentcorer = 2;
                break;
            case 2:

                allLocations.add(locationA);
                locationB = allLocations.get(1);
                txtmtr2.setText(String.valueOf(locationA.distanceTo(locationB)));
                txtlat3.setText(String.valueOf(locationA.getLatitude()));
                txtlon3.setText(String.valueOf(locationA.getLongitude()));
                locationA = new Location("PointD");
                currentcorer = 3;
                break;
            case 3:
                allLocations.add(locationA);
                locationB = allLocations.get(2);
                txtmtr3.setText(String.valueOf(locationA.distanceTo(locationB)));
                txtlat4.setText(String.valueOf(locationA.getLatitude()));
                txtlon4.setText(String.valueOf(locationA.getLongitude()));
                locationB = allLocations.get(0);
                txtmtr4.setText(String.valueOf(locationA.distanceTo(locationB)));
                currentcorer = 4;

                AreaCalculation();
                break;
        }
    }

    private void AreaCalculation() {
        txtarea.setText(String.valueOf(calculateAreaOfGPSPolygonOnSphereInSquareMeters(allLocations, EARTH_RADIUS)));
    }

    private static double calculateAreaOfGPSPolygonOnSphereInSquareMeters(final List<Location> locations, final double radius) {
        if (locations.size() < 3) {
            return 0;
        }

        final double diameter = radius * 2;
        final double circumference = diameter * Math.PI;
        final List<Double> listY = new ArrayList<Double>();
        final List<Double> listX = new ArrayList<Double>();
        final List<Double> listArea = new ArrayList<Double>();
        // calculate segment x and y in degrees for each point
        final double latitudeRef = locations.get(0).getLatitude();
        final double longitudeRef = locations.get(0).getLongitude();
        for (int i = 1; i < locations.size(); i++) {
            final double latitude = locations.get(i).getLatitude();
            final double longitude = locations.get(i).getLongitude();
            listY.add(calculateYSegment(latitudeRef, latitude, circumference));
            Log.d("CalculateArea", String.format("Y %s: %s", listY.size() - 1, listY.get(listY.size() - 1)));
            listX.add(calculateXSegment(longitudeRef, longitude, latitude, circumference));
            Log.d("CalculateArea", String.format("X %s: %s", listX.size() - 1, listX.get(listX.size() - 1)));
        }

        // calculate areas for each triangle segment
        for (int i = 1; i < listX.size(); i++) {
            final double x1 = listX.get(i - 1);
            final double y1 = listY.get(i - 1);
            final double x2 = listX.get(i);
            final double y2 = listY.get(i);
            listArea.add(calculateAreaInSquareMeters(x1, x2, y1, y2));
            Log.d("CalculateArea", String.format("area %s: %s", listArea.size() - 1, listArea.get(listArea.size() - 1)));
        }

        // sum areas of all triangle segments
        double areasSum = 0;
        for (final Double area : listArea) {
            areasSum = areasSum + area;
        }

        // get abolute value of area, it can't be negative
        return Math.abs(areasSum);// Math.sqrt(areasSum * areasSum);
    }

    private static Double calculateAreaInSquareMeters(final double x1, final double x2, final double y1, final double y2) {
        return (y1 * x2 - x1 * y2) / 2;
    }

    private static double calculateYSegment(final double latitudeRef, final double latitude, final double circumference) {
        return (latitude - latitudeRef) * circumference / 360.0;
    }

    private static double calculateXSegment(final double longitudeRef, final double longitude, final double latitude,
                                            final double circumference) {
        return (longitude - longitudeRef) * circumference * Math.cos(Math.toRadians(latitude)) / 360.0;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    void setCanetypename(int canetype)
    {
        switch (canetype) {
            case 1:
                mCanetypeName.setText("Early");
                break;
            case 2:
                mCanetypeName.setText("Genral");
                break;
            case 3:
                mCanetypeName.setText("Reject");
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {

            if (currenttextchangecontrol == mPlotVillCode) {
                mPlotVillName.setText(CommanData.conn.village.getVillageName(Integer.valueOf(editable.toString())));
            } else if (currenttextchangecontrol == mVarietycode) {
                mVarietyname.setText(CommanData.conn.variety.getVarietyName(Integer.valueOf(editable.toString())));
                if(mVarietyname.getText().length()>0) {
                    int canetyepe=CommanData.conn.variety.getCaneTypeCode(Integer.valueOf(editable.toString()));

                    if(canetyepe>0)
                    {
                        mCanetypeCode.setText(String.valueOf(canetyepe));
                        setCanetypename(canetyepe);
                    }
                }
            }  else if (currenttextchangecontrol == mGVillCode) {
                mGVillName.setText(CommanData.conn.village.getVillageName(Integer.valueOf(editable.toString())));
            } else if (currenttextchangecontrol == mGCode) {
                if (mGVillCode.getText().length() > 0) {
                    mGName.setText(CommanData.conn.grower.getGrowerName(Integer.valueOf(mGVillCode.getText().toString()), Integer.valueOf(editable.toString())));
                    mFathername.setText(CommanData.conn.grower.getFatherName(Integer.valueOf(mGVillCode.getText().toString()), Integer.valueOf(editable.toString())));
                }
                else
                    mGCode.setError("First Fill Village Code");
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            currenttextchangecontrol = view;
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
