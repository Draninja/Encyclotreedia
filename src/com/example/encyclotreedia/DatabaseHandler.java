package com.example.encyclotreedia;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	  
	    // Database Name
	private static final String DATABASE_NAME = "datasManager";
	 
	    // Datas table name
	private static final String TABLE_DATA = "datas";
	 
	    // Datas Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_LATIN = "latin";
	private static final String KEY_DESCRIPTION = "description";
	
	
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DATAS_TABLE = "CREATE TABLE " + TABLE_DATA + "("
	                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
	                + KEY_LATIN + " TEXT,"
	                + KEY_DESCRIPTION + " TEXT" + ")";
	        db.execSQL(CREATE_DATAS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
	}
	
	// Adding new data
	public void addData(Data data) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, data.getName());
	    values.put(KEY_LATIN, data.getLatin());
	    values.put(KEY_DESCRIPTION, data.getDescription()); 
	 
	    // Inserting Row
	    db.insert(TABLE_DATA, null, values);
	    db.close(); // Closing database connection
	}
	 
	// Getting single data
	public Data getData(int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID,
	            KEY_NAME, KEY_LATIN, KEY_DESCRIPTION }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Data data = new Data(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2), cursor.getString(3));
		return data;
	}
	
	public Data getDataByName(String name) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID,
	            KEY_NAME, KEY_LATIN, KEY_DESCRIPTION }, KEY_NAME + "=?",
	            new String[] { name }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Data data = new Data(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2), cursor.getString(3));
		return data;
	}
	
	public List<Data> getAllDatas() {
	    List<Data> dataList = new ArrayList<Data>();
	    String selectQuery = "SELECT  * FROM " + TABLE_DATA;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    if (cursor.moveToFirst()) {
	        do {
	            Data data = new Data();
	            data.setId(Integer.parseInt(cursor.getString(0)));
	            data.setName(cursor.getString(1));
	            data.setLatin(cursor.getString(2));
	            data.setDescription(cursor.getString(3));
	            dataList.add(data);
	        } while (cursor.moveToNext());
	    }
	    return dataList;
	}
	 
	public int getDatasCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        return cursor.getCount();
	}
	// Updating single data
	public int updateData(Data data) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, data.getName());
	    values.put(KEY_LATIN, data.getLatin());
	    values.put(KEY_DESCRIPTION, data.getDescription());
	 
	    // updating row
	    return db.update(TABLE_DATA, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(data.getId()) });
	}
	 
	// Deleting single data
	public void deleteData(Data data) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_DATA, KEY_ID + " = ?",
	            new String[] { String.valueOf(data.getId()) });
	    db.close();
	}

}
