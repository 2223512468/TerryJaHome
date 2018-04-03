package com.jajahome.util;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.jajahome.constant.Constant;
import com.jajahome.widget.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SunnyCoffee
 * @date 2014-1-19
 * @version 1.0
 * @desc 定位服务
 *
 */
public class LocationSvc extends Service implements LocationListener {

	private static final String TAG = "LocationSvc";
	private LocationManager locationManager;
	private static Geocoder geocoder;
	private String mcityName, mAdminArea, mUserCity;
	public static SharedPreferencesUtils sharedPreferencesUtils;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		geocoder = new Geocoder(this);
		sharedPreferencesUtils = new SharedPreferencesUtils(this);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	}

	@Override
	public void onStart(Intent intent, int startId) {
		if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		} else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		} else Toast.makeText(this, "无法定位", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean stopService(Intent name) {
		return super.stopService(name);
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "Get the current position \n" + location);

		List<Address> addList = new ArrayList<>();
		String latitude = location.getLatitude() + "";
		String longitude = location.getLongitude() + "";
		try {
			addList = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (addList != null && addList.size() > 0) {
			for (int i = 0; i < addList.size(); i++) {
				Address add = addList.get(0);
				mcityName = add.getLocality();
				mAdminArea = add.getAdminArea();
				mUserCity = mAdminArea + " " + mcityName;
			}
		}

		sharedPreferencesUtils.saveUserCity(mcityName);
		//通知Activity
		Intent intent = new Intent();
		intent.setAction(Constant.LOCATION_ACTION);
		intent.putExtra(Constant.LOCATION, mUserCity);
		intent.putExtra(Constant.LOCATION_NAME ,mcityName);
		sendBroadcast(intent);

		// 如果只是需要定位一次，这里就移除监听，停掉服务。如果要进行实时定位，可以在退出应用或者其他时刻停掉定位服务。
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			return;
		}
		locationManager.removeUpdates(this);
		stopSelf();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
