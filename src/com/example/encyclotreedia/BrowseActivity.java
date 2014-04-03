package com.example.encyclotreedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseActivity extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		final ListView listView;
	    setContentView(R.layout.activity_main); 
	    
	    final DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase database = db.getWritableDatabase();
        database.execSQL("delete from datas");
        db.addData(new Data(1,"Poison Ivy", "Something latin", "This plant blah blah"));
        db.addData(new Data(2,"Poison Oak", "More latin", "This plant blah blah"));
        
        List<Data> data = db.getAllDatas();       
        List<Map<String, String>> dataMap = new ArrayList<Map<String, String>>();
        
        for (Data cn : data) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("name", cn.getName());
            datum.put("latin", cn.getLatin());
            dataMap.add(datum);
        }
        
        SimpleAdapter adapter = new SimpleAdapter(this, (List<? extends Map<String, ?>>) dataMap,
                                                  android.R.layout.simple_list_item_2,
                                                  new String[] {"name", "latin"},
                                                  new int[] {android.R.id.text1,
                                                             android.R.id.text2});
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                int position, long id) {
            	Object obj = listView.getItemAtPosition(position);
            	String[] fullString = obj.toString().split("=");
            	String dataName = fullString[2].substring(0, fullString[2].length()-1);
            	Data foundData = db.getDataByName(dataName);
            	showData(foundData);
            }
        });
    }
    
    public void showData(Data data){
    	TextView dataName;
    	TextView dataLatin;
    	TextView dataDescrip;
    	
    	dataName = (TextView) findViewById(R.id.dataName);
    	dataLatin = (TextView) findViewById(R.id.dataLatin);
    	dataDescrip = (TextView) findViewById(R.id.dataDescrip);
    	
    	dataName.setText(data.getName());
    	dataLatin.setText(data.getLatin());
    	dataDescrip.setText(data.getDescription());
    }
}
