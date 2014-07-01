package com.example.jwd;

import java.text.DateFormat;
import java.util.ArrayList;  
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;  
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class wufen extends Activity {

	private final String TAG = "MainActivity";
	private double latitude = 0.0;
	private double longitude = 0.0;
	private TextView latitude_tv;
	private TextView longitude_tv;
	private TextView jiqi_tv;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private ListView listView; 
	private SimpleAdapter adapter;
	private DBUtil dbUtil;
	private TextView sj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
		.detectDiskWrites()
		.detectNetwork()
		.penaltyLog()
		.build());
StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects()
		.detectLeakedClosableObjects()
		.penaltyLog()
		.penaltyDeath()
		.build());
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sj=(TextView) findViewById(R.id.textView4);
		final Date now = new Date();
		Calendar cal = Calendar.getInstance();
		
		jiqi_tv=(TextView) findViewById(R.id.textView2);
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		final String uniqueId = deviceUuid.toString();
		jiqi_tv.setText(uniqueId);
		
		btn3=(Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(wufen.this,Register.class);
				startActivity(intent); 
			}
		});
		
		btn4=(Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(wufen.this, sjsz.class);
				startActivity(intent);
			}
		});
		
		listView = (ListView) findViewById(R.id.listView); 
		btn5=(Button) findViewById(R.id.button5);
		
		btn5.setOnClickListener(new OnClickListener(){
			public void onClick(View v) { 
				Intent intent=new Intent();
				intent.setClass(wufen.this,clzk.class);
				startActivity(intent); 
			}
		});
		
		btn2=(Button) findViewById(R.id.button2);
		dbUtil=new DBUtil();
		DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		String str1 = d6.format(now);
		sj.setText(""+str1);
		Timer timer=new Timer(true);
		TimerTask task=new TimerTask(){
			public void run(){
				final Date now1 = new Date();
				DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
				
				String str1 = d6.format(now1);
				
				dbUtil.insertCargoInfo(String.valueOf(latitude), String.valueOf(longitude),uniqueId,str1);  
				
			}
			
		};
		timer.schedule(task,0,5*60*1000);

		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Date now2 = new Date();
				String str1 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(now2);
				sj.setText(""+str1);
				 dbUtil.insertCargoInfo(String.valueOf(latitude), String.valueOf(longitude),uniqueId,str1);  
	                
	                hideButton(false);  
	                Toast.makeText(wufen.this, "成功上传数据", Toast.LENGTH_SHORT).show(); 
			}
		});
		
		
		

		
		latitude_tv = (TextView) findViewById(R.id.latitude);
		longitude_tv = (TextView) findViewById(R.id.longitude);
		

		
		
		
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
		} else {
			LocationListener locationListener = new LocationListener() {
				
				
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {

				}

				
				@Override
				public void onProviderEnabled(String provider) {

				}

				
				@Override
				public void onProviderDisabled(String provider) {

				}

				
				@Override
				public void onLocationChanged(Location location) {
					
					if (location != null) {
						Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
//						Log.i(TAG, "latitude = " + latitude + ",longitude = " + longitude);
						latitude = location.getLatitude(); 
						longitude = location.getLongitude(); 
						
						latitude_tv.setText(latitude + "");
						longitude_tv.setText(longitude + "");
						
					}
				}
				
			};
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude(); 
				longitude = location.getLongitude(); 
//				latitude_tv.setText(latitude + "");
//				longitude_tv.setText(longitude + "");
//				Log.i(TAG, "latitude = " + latitude + ",longitude = " + longitude);
			}
		}

	}

    private void hideButton(boolean result) {  
        if (result) {  
            btn2.setVisibility(View.GONE);  
        } else {  
            btn2.setVisibility(View.VISIBLE);   
        }  
    } 
    public void onBackPressed()  
    {  
            wufen.this.finish();  
        
    }
}
