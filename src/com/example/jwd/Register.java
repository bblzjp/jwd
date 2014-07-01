package com.example.jwd;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private Button button1;
	private Button button2;
	private DBUtil dbUtil;
	protected void onCreate(Bundle savedInstanceState){
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
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.register);
		editText1=(EditText)findViewById(R.id.editText1);
		editText2=(EditText)findViewById(R.id.editText2);
		editText3=(EditText)findViewById(R.id.editText3);
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		dbUtil=new DBUtil();
		editText1.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(editText1.getText().toString().trim().length()<4){
						Toast.makeText(Register.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		editText2.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(editText2.getText().toString().trim().length()<6){
						Toast.makeText(Register.this, "密码不能小于8个字符", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		editText3.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(!editText3.getText().toString().trim().equals(editText2.getText().toString().trim())){
						Toast.makeText(Register.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show(); 
					}
				}
			}
			
		});
		
	
	
	button2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(Register.this,login.class);
			startActivity(intent); 
		}
	});
	
	final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	final String tmDevice, tmSerial, tmPhone, androidId;
	tmDevice = "" + tm.getDeviceId();
	tmSerial = "" + tm.getSimSerialNumber();
	androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
	UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	final String uniqueId = deviceUuid.toString();
	
		button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				
				dbUtil.insertUser(editText1.getText().toString(), editText2.getText().toString(),uniqueId);
				
	            Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();  
			}
			
		});
	}
}
