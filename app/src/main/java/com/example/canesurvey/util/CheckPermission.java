package com.example.canesurvey.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

public class CheckPermission {
    private static int MY_PERMISSIONS_REQUEST=10;

    public static void checkLocationPermission(Activity activity)
    {
        if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET},MY_PERMISSIONS_REQUEST);
            return;
        }
    }

}
