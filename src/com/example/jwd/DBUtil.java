package com.example.jwd;

import java.sql.Connection;  
import java.util.ArrayList;  
import java.util.Date;
import java.util.HashMap;
import java.util.List;

  
public class DBUtil {  
    private ArrayList<String> arrayList = new ArrayList<String>();  
    private ArrayList<String> brrayList = new ArrayList<String>();  
    private ArrayList<String> crrayList = new ArrayList<String>();  
    private HttpConnSoap Soap = new HttpConnSoap();  
  
    public static Connection getConnection() {  
        Connection con = null;  
        try {  
            //Class.forName("org.gjt.mm.mysql.Driver");  
            //con=DriverManager.getConnection("jdbc:mysql://192.168.0.106:3306/test?useUnicode=true&characterEncoding=UTF-8","root","initial");               
        } catch (Exception e) {  
            //e.printStackTrace();  
        }  
        return con;  
    }  
    
    public List<HashMap<String, String>> getAllInfo() {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
  
        arrayList.clear();  
        brrayList.clear();  
        crrayList.clear();  
  
        crrayList = Soap.GetWebServre("selectAllCargoInfor", arrayList, brrayList);  
  
        HashMap<String, String> tempHash = new HashMap<String, String>();  
        tempHash.put("����", "����");  
        tempHash.put("co2�ŷ���", "co2�ŷ���");  
        tempHash.put("�ͺ�", "�ͺ�");  
        list.add(tempHash);  
          
        for (int j = 0; j < crrayList.size(); j += 3) {  
            HashMap<String, String> hashMap = new HashMap<String, String>();  
            hashMap.put("����", crrayList.get(j));  
            hashMap.put("co2�ŷ���", crrayList.get(j + 1));  
            hashMap.put("�ͺ�", crrayList.get(j + 2));  
            list.add(hashMap);  
        }  
  
        return list;  
    } 
    public List<HashMap<String, String>> wzInfo(String name) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
  
        arrayList.clear();  
        brrayList.clear();  
        crrayList.clear();  
        
        arrayList.add("name"); 
        brrayList.add(name); 
  
        crrayList = Soap.GetWebServre("selectNameCargoInfor", arrayList, brrayList);  
  
        HashMap<String, String> tempHash = new HashMap<String, String>();  
        tempHash.put("ʱ��", "ʱ��");  
        tempHash.put("����", "����");  
        tempHash.put("γ��", "γ��");  
        list.add(tempHash);  
          
        for (int j = 0; j < crrayList.size(); j += 3) {  
            HashMap<String, String> hashMap = new HashMap<String, String>();  
            hashMap.put("ʱ��", crrayList.get(j));  
            hashMap.put("����", crrayList.get(j + 1));  
            hashMap.put("γ��", crrayList.get(j + 2));  
            list.add(hashMap);  
        }  
  
        return list;  
    } 
  
    public List<HashMap<String, String>> selectSpeed() {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
  
        arrayList.clear();  
        brrayList.clear();  
        crrayList.clear();  
  
        crrayList = Soap.GetWebServre("selectSpeed", arrayList, brrayList);  
  
        HashMap<String, String> tempHash = new HashMap<String, String>();  
        tempHash.put("speed", "Cno");  
        tempHash.put("Co2", "Cname");   
        list.add(tempHash);  
          
        for (int j = 0; j < crrayList.size(); j += 3) {  
            HashMap<String, String> hashMap = new HashMap<String, String>();  
            hashMap.put("Cno", crrayList.get(j));  
            hashMap.put("Cname", crrayList.get(j + 1));  
            list.add(hashMap);  
        }  
  
        return list;  
    }  

    public void insertCargoInfo(String jd, String wd,String jqm,String sj) {  
  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("jd");  
        arrayList.add("wd");  
        arrayList.add("jqm"); 
        arrayList.add("sj");
        brrayList.add(jd);  
        brrayList.add(wd);  
        brrayList.add(jqm); 
        brrayList.add(sj);
         
          
        Soap.GetWebServre("insertCargoInfo", arrayList, brrayList);  
    }
    public void insertUser(String name,String password,String jiqima){
    	arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("name");  
        arrayList.add("password"); 
        arrayList.add("jiqima");
        brrayList.add(name);  
        brrayList.add(password);  
        brrayList.add(jiqima);
          
        Soap.GetWebServre("insertUser", arrayList, brrayList);  
    }
      

} 
