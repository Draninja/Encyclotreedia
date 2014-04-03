package com.example.encyclotreedia;

public class Data {
	int _id;
	String _name;
	String _latin;
	String _description;
	
	public Data(){
	}
	
	public Data(int id, String name, String latin, String description){
		this._id = id;
		this._name = name;
		this._latin = latin;
		this._description = description;
	}
	
	public Data(String name, String latin, String description){
		this._name = name;
		this._latin = latin;
		this._description = description;
	}
	
	public int getId(){
		return this._id;
	}
	
	public void setId(int id){
		this._id = id;
	}
	
	public String getName(){
		return this._name;
	}
	
	public void setName(String name){
		this._name = name;
	}
	
	public String getLatin(){
		return this._latin;
	}
	
	public void setLatin(String latin){
		this._latin = latin;
	}
	
	public String getDescription(){
		return this._description;
	}
	
	public void setDescription(String description){
		this._description = description;
	}
	public String __toString(){
		return this._name;
	}
}



