package com.example.jwd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class sjsz extends Activity {
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btn10;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjxz);
		
		btn5=(Button) findViewById(R.id.button5);
		btn5.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(sjsz.this,shimiao.class);
				startActivity(intent); 
				Toast.makeText(sjsz.this, "设置成功,每10秒上传一次数据", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn6=(Button) findViewById(R.id.button6);
		btn6.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(sjsz.this,sanshimiao.class);
				startActivity(intent); 
				Toast.makeText(sjsz.this, "设置成功,每30秒上传一次数据", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn7=(Button) findViewById(R.id.button7);
		btn7.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(sjsz.this,yifen.class);
				startActivity(intent); 
				Toast.makeText(sjsz.this, "设置成功,每1分钟上传一次数据", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn8=(Button) findViewById(R.id.button8);
		btn8.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(sjsz.this,wufen.class);
				startActivity(intent); 
				Toast.makeText(sjsz.this, "设置成功,每5分钟上传一次数据", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn9=(Button) findViewById(R.id.button9);
		btn9.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(sjsz.this,shifen.class);
				startActivity(intent); 
				Toast.makeText(sjsz.this, "设置成功,每10分钟上传一次数据", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn10=(Button) findViewById(R.id.button10);
		btn10.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent();
				intent.setClass(sjsz.this,sanshifen.class);
				startActivity(intent); 
				Toast.makeText(sjsz.this, "设置成功,每30分钟上传一次数据", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
