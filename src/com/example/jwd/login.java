package com.example.jwd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class login extends Activity {
Button btnlogin,btnexit; //登录和退出按钮对像
EditText edituser,editpass; //用户名和密码输入框对象
boolean data=false; //调用webservice 近回的数据,验证成功true,失败false
HttpThread thread=null; //线程对像
String name=""; //用户名
String pass=""; //口令
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.login);
btnlogin=(Button)findViewById(R.id.BtnLogin); //获得用户名和密码按钮实例
btnexit=(Button)findViewById(R.id.BtnRegister);
edituser=(EditText)findViewById(R.id.EditTextUser); //获得用户名和密码Edittext
editpass=(EditText)findViewById(R.id.EditTextPassWord);
 //获取上次保存的用户名和密码
SharedPreferences sp=getSharedPreferences("login",Context.MODE_PRIVATE);
String tempstr=sp.getString("username", "");
edituser.setText(tempstr);
editpass.setText(sp.getString("pass", ""));
//退出按钮点击事件
btnexit.setOnClickListener(new View.OnClickListener() {

public void onClick(View arg0) {
	Intent intent=new Intent();
	intent.setClass(login.this,Register.class);
	startActivity(intent); 

}
});
//登录按钮点击事件
btnlogin.setOnClickListener(new View.OnClickListener() {

public void onClick(View arg0) {
// TODO Auto-generated method stub


name=edituser.getText().toString();
pass=editpass.getText().toString();

ResponseOnClickLogin(name, pass);


}
});
}

public void ResponseOnClickLogin(String username,String password){

thread=new HttpThread(handlerwdy); //建立线程实例 

HashMap <String ,Object> params=new HashMap<String ,Object>();
try{
String strvalidate="galyglxxxt";
strvalidate=new String(strvalidate.toString().getBytes(),"UTF-8");
params.put("username", username);//加入参数
params.put("pass", password);
params.put("validate", strvalidate);
}catch(Exception ex){
ex.printStackTrace();
}
String url="http://42.121.125.207:8081/appgps/Service1.asmx";//webserivce地址
 String nameSpace = "http://tempuri.org/"; //空间名,可修改
 String methodName = "ValidateUsername"; //需调用webservice名称
 thread.doStart(url, nameSpace, methodName, params); //启动线程

 }
//生成消息对象
Handler handlerwdy=new Handler(){

public void handleMessage(Message m){
switch(m.what){
case 1:
data=m.getData().getBoolean("data"); //从消息在拿出数据

if(data){
CheckBox cb=(CheckBox)findViewById(R.id.CheckBox01); //如果要保存用户名和密码
if(cb.isChecked())
{ 
SharedPreferences sp=getSharedPreferences("login",Context.MODE_PRIVATE);
Editor editor=sp.edit();
String tempname=name;
editor.putString("username", name);
editor.putString("pass", pass);
editor.commit();
}
else
{
SharedPreferences sp=getSharedPreferences("login",Context.MODE_PRIVATE);
Editor editor=sp.edit();
editor.putString("username", "");
editor.putString("pass", "");
editor.commit();
}
//登录成功后的提示　　　　　　　　　　　　　　　　　　
Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT)
.show();
//成功后界面要交给项目的主界面了
Intent in=new Intent(login.this,MainActivity.class);
//把用户名传给下一个activity
Bundle bundle = new Bundle(); 
bundle.putString("KEY_USERNAME",name); 
in.putExtras(bundle); 
login.this.startActivity(in);

}
else
{
//验证不通过,给出个提示
SharedPreferences sp=getSharedPreferences("login",Context.MODE_PRIVATE);
Editor editor=sp.edit();
editor.putString("username", "");
editor.putString("pass", "");
editor.commit();
new AlertDialog.Builder(login.this)
.setTitle("登录失败") 
.setMessage("请确认输入有误错误") 
.setIcon(R.drawable.cancel)
.setNeutralButton("返回登录", new DialogInterface.OnClickListener() { 
public void onClick(DialogInterface dlg, int sumthin) { 
// do nothing ?C it will close on its own 
} 
}) 
.show();
}
break; 
case 2:
//收到了调用出错的消息　　　　　　　
new AlertDialog.Builder(login.this)
.setTitle("出错:") 
.setMessage(m.getData().getString("error")) 
.setNeutralButton("Close", new DialogInterface.OnClickListener() { 
public void onClick(DialogInterface dlg, int sumthin) { 
// do nothing ?C it will close on its own 
} 
}) 
.show();
break;

} 
} 
};
//线程类

public class HttpThread extends Thread{
private Handler handle=null;
String url=null;
String nameSpace=null;
String methodName=null;
HashMap <String ,Object> params=null;
ProgressDialog progressDialog=null;


public HttpThread(Handler hander){
handle=hander;
}
//线程开始

public void doStart(String url, String nameSpace, String methodName,
HashMap<String, Object> params) {
// 把参数传进来
this.url=url;
this.nameSpace=nameSpace;
this.methodName=methodName;
this.params=params;
//告诉使用者，请求开始了
progressDialog=new ProgressDialog(login.this);
progressDialog.setTitle("网络连接");
progressDialog.setMessage("正在请求，请稍等......");
progressDialog.setIndeterminate(true);
//progressDialog=ProgressDialog.show(clswdy.this, "网络连接","正在验证，请稍等......",true,true);
progressDialog.setButton("取消", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int i) {
progressDialog.cancel();

}
});
progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
public void onCancel(DialogInterface dialog) {
}
});
progressDialog.show();
this.start(); //线程开始了
}
/**

*/
@Override
public void run() {
// TODO Auto-generated method stub
super.run();
try{
//web service请求,result为返回结果
boolean result= CallWebService();

if(result){

//取消进度对话框
progressDialog.dismiss();
//clswdy.this.setProgressBarIndeterminateVisibility(false);
//构造消息,验证通过了
Message message=handle.obtainMessage();
Bundle b=new Bundle();
message.what=1; //这里是消息的类型
b.putBoolean("data", true); //这里是消息传送的数据

message.setData(b);
handle.sendMessage(message);
}
else
{
progressDialog.dismiss();

Message message=handle.obtainMessage();
Bundle b=new Bundle();
message.what=1;
b.putBoolean("data", false);
message.setData(b);
handle.sendMessage(message);

}
}catch(Exception ex){
progressDialog.dismiss();
// 构造消息，程序出错了
Message message=handle.obtainMessage();
Bundle b=new Bundle();
message.what=2; 

b.putString("error", ex.getMessage());

message.setData(b);
handle.sendMessage(message);


}finally{

}
}

/**

* 
*/
protected boolean CallWebService() throws Exception{
String SOAP_ACTION = nameSpace + methodName; 
boolean response=false;
SoapObject request=new SoapObject(nameSpace,methodName);
// boolean request=false;
SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);

envelope.dotNet=true; //.net 支持

// 参数

if(params != null && !params.isEmpty() ){
for(Iterator it=params.entrySet().iterator();it.hasNext();){
Map.Entry e=(Entry) it.next();
request.addProperty(e.getKey().toString(),e.getValue());

}
}
envelope.bodyOut=request;
//
AndroidHttpTransport androidHttpTrandsport=new AndroidHttpTransport(url);
//HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
SoapObject result=null;
try{
//web service请求
androidHttpTrandsport.call(SOAP_ACTION, envelope);
//得到返回结果
Object temp=envelope.getResult();
response=Boolean.parseBoolean(temp.toString());
}catch(Exception ex){
throw ex;
}
return response;

}
}
}