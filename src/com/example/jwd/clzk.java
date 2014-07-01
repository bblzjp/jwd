package com.example.jwd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class clzk extends Activity {
	
	private ListView listView; 
	private DBUtil dbUtil;
	private SimpleAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.item);
	
	dbUtil = new DBUtil();

	listView = (ListView) findViewById(R.id.listView); 
	
	
	listView.setVisibility(View.VISIBLE);

    List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
    
    list = dbUtil.getAllInfo();  
    
    adapter = new SimpleAdapter(  
            clzk.this,   
            list,   
            R.layout.item,   
            new String[] { "车速", "co2排放量","油耗" },   
            new int[] { R.id.txt_Cno, R.id.txt_Cname,R.id.txt_Cnum});  

    listView.setAdapter(adapter);
    
	}
	
	
}
