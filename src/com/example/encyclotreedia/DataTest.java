package com.example.encyclotreedia;
import java.util.List;

import com.example.encyclotreedia.Data;
import com.example.encyclotreedia.DatabaseHandler;
 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
public class DataTest extends Activity {

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	         
	        DatabaseHandler db = new DatabaseHandler(this);
	         
	        /**
	         * CRUD Operations
	         * */
	        // Inserting Contacts
	        Log.d("Insert: ", "Inserting .."); 
//	        db.addData(new Data(1, "Poison Ivy", "Shit's bad, yo."));        
	         
	        // Reading all contacts
	        Log.d("Reading: ", "Reading all contacts.."); 
	        List<Data> data = db.getAllDatas();       
	         
	        for (Data cn : data) {
	            String log = "Id: "+cn.getId()+" ,Name: " + cn.getName() + " ,Description: " + cn.getDescription();
	        Log.d("Name: ", log);
	    }
	    }
}
