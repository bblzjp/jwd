package com.example.jwd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class clzk1 extends Activity {
	
	private ListView listView; 
	private DBUtil dbUtil;
	private SimpleAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.item_wz);
	
	dbUtil = new DBUtil();

	listView = (ListView) findViewById(R.id.listView); 
	
	
	final Dialog dialog = new Dialog(clzk1.this);  
    dialog.setContentView(R.layout.dialog_add);  
    dialog.setTitle("请输入需要查询的用户名");  
    Window dialogWindow = dialog.getWindow();  
    WindowManager.LayoutParams lp = dialogWindow.getAttributes();  
    dialogWindow.setGravity(Gravity.CENTER);  
    dialogWindow.setAttributes(lp);
    
    final EditText cNameEditText = (EditText) dialog.findViewById(R.id.editText1);  
    Button btnConfirm = (Button) dialog.findViewById(R.id.button1);  
    Button btnCancel = (Button) dialog.findViewById(R.id.button2); 
    
    btnConfirm.setOnClickListener(new OnClickListener() {  
    	  
        @Override  
        public void onClick(View v) {  
              
            dbUtil.wzInfo(cNameEditText.getText().toString());   
        	listView.setVisibility(View.VISIBLE);
        	dialog.dismiss(); 
        	Toast.makeText(clzk1.this, "成功添加数据", Toast.LENGTH_SHORT).show();
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  

           list = dbUtil.wzInfo(cNameEditText.getText().toString());  
           
            adapter = new SimpleAdapter(  
                    clzk1.this,   
                    list,   
                    R.layout.item,   
                    new String[] { "时间","经度","纬度" },   
                    new int[] { R.id.txt_sj,R.id.txt_jd,R.id.txt_wd});  

            listView.setAdapter(adapter);
        }  
    });  

    btnCancel.setOnClickListener(new OnClickListener() {  

        @Override  
        public void onClick(View v) {  
            dialog.dismiss();  
             
        }  
    });  
    dialog.show(); 
	
	
	
	}
	
}
