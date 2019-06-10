package com.example.canesurvey;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.example.canesurvey.Comman.CommanData;
import com.example.canesurvey.util.CheckPermission;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {


    private LocationManager mLocationManager;
    private static MainActivity mActivity;
    private ArrayList<GpsTestListener> mGpsTestListeners = new ArrayList<GpsTestListener>();
    private boolean mStarted = false;
    private LocationProvider mProvider;
    private long minTime; // Min Time between location updates, in milliseconds
    private float minDistance; // Min Distance between location updates, in meters
    private GpsTestListener currentListener;
    private Fragment fragment;
    private GeomagneticField mGeomagneticField;
    private Location mLastLocation;
    private TelephonyManager telephonyManager;

    static MainActivity getInstance() {
        return mActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mActivity=this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BlankFragment fr=new BlankFragment();
        fragment=fr;
        this.setTitle(fr.Tag);
        getSupportFragmentManager().beginTransaction().add(R.id.fholder, fragment).commit();
        Init();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newsurvey) {
            // Handle the camera action

            CapturePlot fr = new CapturePlot();
            fragment=fr;
            this.setTitle(fr.TAG);
            getSupportFragmentManager().beginTransaction().replace(R.id.fholder, fragment).addToBackStack(fr.TAG).commit();

        } else if (id == R.id.nav_pendingshare) {

        } else if (id == R.id.nav_nonmemtomem) {

        } else if (id == R.id.nav_editplot) {

        } else if (id == R.id.nav_dataimport) {
            ImportData fr = new ImportData();
            fragment=fr;
            this.setTitle(fr.TAG);
            getSupportFragmentManager().beginTransaction().replace(R.id.fholder, fragment).addToBackStack(fr.TAG).commit();

        } else if(id==R.id.nav_logout){
            //CommanData.conn.oprator.truncateTable();
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Init() {

        CheckPermission.checkLocationPermission(this);


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //promptEnableGps();
        mProvider = mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            promptEnableGps();
        }
    }


    private void promptEnableGps() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.enable_gps_message))
                .setPositiveButton(getString(R.string.enable_gps_positive_button),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        }
                )
                .setNegativeButton(getString(R.string.enable_gps_negative_button),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                )
                .show();
    }

    void addGpsListener(GpsTestListener listener) {
        if (mLocationManager == null) {
            return;
        }
        currentListener=listener;
        if (!mStarted) {

            CheckPermission.checkLocationPermission(this);
            mStarted=true;
            mLocationManager
                    .requestLocationUpdates(mProvider.getName(), 100, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation=location;
        updateGeomagneticField();
        currentListener.onLocationChanged(location);
    }

    private void updateGeomagneticField() {
        mGeomagneticField = new GeomagneticField((float) mLastLocation.getLatitude(),
                (float) mLastLocation.getLongitude(), (float) mLastLocation.getAltitude(),
                mLastLocation.getTime());
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
