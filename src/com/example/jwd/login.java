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
Button btnlogin,btnexit; //��¼���˳���ť����
EditText edituser,editpass; //�û�����������������
boolean data=false; //����webservice ���ص�����,��֤�ɹ�true,ʧ��false
HttpThread thread=null; //�̶߳���
String name=""; //�û���
String pass=""; //����
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.login);
btnlogin=(Button)findViewById(R.id.BtnLogin); //����û��������밴ťʵ��
btnexit=(Button)findViewById(R.id.BtnRegister);
edituser=(EditText)findViewById(R.id.EditTextUser); //����û���������Edittext
editpass=(EditText)findViewById(R.id.EditTextPassWord);
 //��ȡ�ϴα�����û���������
SharedPreferences sp=getSharedPreferences("login",Context.MODE_PRIVATE);
String tempstr=sp.getString("username", "");
edituser.setText(tempstr);
editpass.setText(sp.getString("pass", ""));
//�˳���ť����¼�
btnexit.setOnClickListener(new View.OnClickListener() {

public void onClick(View arg0) {
	Intent intent=new Intent();
	intent.setClass(login.this,Register.class);
	startActivity(intent); 

}
});
//��¼��ť����¼�
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

thread=new HttpThread(handlerwdy); //�����߳�ʵ�� 

HashMap <String ,Object> params=new HashMap<String ,Object>();
try{
String strvalidate="galyglxxxt";
strvalidate=new String(strvalidate.toString().getBytes(),"UTF-8");
params.put("username", username);//�������
params.put("pass", password);
params.put("validate", strvalidate);
}catch(Exception ex){
ex.printStackTrace();
}
String url="http://42.121.125.207:8081/appgps/Service1.asmx";//webserivce��ַ
 String nameSpace = "http://tempuri.org/"; //�ռ���,���޸�
 String methodName = "ValidateUsername"; //�����webservice����
 thread.doStart(url, nameSpace, methodName, params); //�����߳�

 }
//������Ϣ����
Handler handlerwdy=new Handler(){

public void handleMessage(Message m){
switch(m.what){
case 1:
data=m.getData().getBoolean("data"); //����Ϣ���ó�����

if(data){
CheckBox cb=(CheckBox)findViewById(R.id.CheckBox01); //���Ҫ�����û���������
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
//��¼�ɹ������ʾ������������������������������������
Toast.makeText(login.this, "��¼�ɹ�", Toast.LENGTH_SHORT)
.show();
//�ɹ������Ҫ������Ŀ����������
Intent in=new Intent(login.this,MainActivity.class);
//���û���������һ��activity
Bundle bundle = new Bundle(); 
bundle.putString("KEY_USERNAME",name); 
in.putExtras(bundle); 
login.this.startActivity(in);

}
else
{
//��֤��ͨ��,��������ʾ
SharedPreferences sp=getSharedPreferences("login",Context.MODE_PRIVATE);
Editor editor=sp.edit();
editor.putString("username", "");
editor.putString("pass", "");
editor.commit();
new AlertDialog.Builder(login.this)
.setTitle("��¼ʧ��") 
.setMessage("��ȷ�������������") 
.setIcon(R.drawable.cancel)
.setNeutralButton("���ص�¼", new DialogInterface.OnClickListener() { 
public void onClick(DialogInterface dlg, int sumthin) { 
// do nothing ?C it will close on its own 
} 
}) 
.show();
}
break; 
case 2:
//�յ��˵��ó������Ϣ��������������
new AlertDialog.Builder(login.this)
.setTitle("����:") 
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
//�߳���

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
//�߳̿�ʼ

public void doStart(String url, String nameSpace, String methodName,
HashMap<String, Object> params) {
// �Ѳ���������
this.url=url;
this.nameSpace=nameSpace;
this.methodName=methodName;
this.params=params;
//����ʹ���ߣ�����ʼ��
progressDialog=new ProgressDialog(login.this);
progressDialog.setTitle("��������");
progressDialog.setMessage("�����������Ե�......");
progressDialog.setIndeterminate(true);
//progressDialog=ProgressDialog.show(clswdy.this, "��������","������֤�����Ե�......",true,true);
progressDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int i) {
progressDialog.cancel();

}
});
progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
public void onCancel(DialogInterface dialog) {
}
});
progressDialog.show();
this.start(); //�߳̿�ʼ��
}
/**

*/
@Override
public void run() {
// TODO Auto-generated method stub
super.run();
try{
//web service����,resultΪ���ؽ��
boolean result= CallWebService();

if(result){

//ȡ�����ȶԻ���
progressDialog.dismiss();
//clswdy.this.setProgressBarIndeterminateVisibility(false);
//������Ϣ,��֤ͨ����
Message message=handle.obtainMessage();
Bundle b=new Bundle();
message.what=1; //��������Ϣ������
b.putBoolean("data", true); //��������Ϣ���͵�����

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
// ������Ϣ�����������
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

envelope.dotNet=true; //.net ֧��

// ����

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
//web service����
androidHttpTrandsport.call(SOAP_ACTION, envelope);
//�õ����ؽ��
Object temp=envelope.getResult();
response=Boolean.parseBoolean(temp.toString());
}catch(Exception ex){
throw ex;
}
return response;

}
}
}