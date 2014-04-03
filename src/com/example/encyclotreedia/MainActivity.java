package com.example.encyclotreedia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

public class MainActivity extends Activity {
	private ListView listView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main); 
        final TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabSpec BrowseTab = tabs.newTabSpec("browse");
        TabSpec ShowTab = tabs.newTabSpec("show");
        TabSpec ScanTab = tabs.newTabSpec("scan");
        
        BrowseTab.setIndicator("Browse",null);
        BrowseTab.setContent(R.id.Browse);
        
        ShowTab.setIndicator("Show",null);
        ShowTab.setContent(R.id.Show);
        
        ScanTab.setIndicator("Scan",null);
        Intent ScanIntent = new Intent().setClass(this,ScanActivity.class);
//        ScanTab.setContent(R.id.Scan);
        ScanTab.setContent(ScanIntent);
        
        tabs.addTab(BrowseTab);
        tabs.addTab(ShowTab);
        tabs.addTab(ScanTab);
        tabs.getTabWidget().getChildAt(0).setBackgroundColor(Color.GRAY);
        tabs.getTabWidget().getChildAt(1).setBackgroundColor(Color.GRAY);
        tabs.getTabWidget().getChildAt(2).setBackgroundColor(Color.GRAY);
        
        final DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase database = db.getWritableDatabase();
        database.execSQL("delete from datas");
        db.addData(new Data(1,"Poison Ivy", "Toxicodendron rydbergii", "Poison ivy belongs to the family Anacardiaceae (the Sumac family)." +
        		" It grows primarily in temperate climates in the Americas and Asia."));
        db.addData(new Data(2,"Poison Oak", "Toxicodendron diversilobum", "Toxicodendron diversilobum, " +
        		"commonly named Pacific poison oak or western poison oak (syn. Rhus diversiloba), " +
        		"is a woody vine or shrub in the Anacardiaceae (sumac) family." +
        		" It is widely distributed in western North America," +
        		" inhabiting conifer and mixed broadleaf forests, woodlands, grasslands, and chaparral biomes."));
        
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
            	tabs.setCurrentTab(1);
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
