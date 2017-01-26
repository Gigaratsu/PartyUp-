package com.benjaminboyce.partyup;

import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static String DATABASENAME 	= "partyup";
	public static String TABLE 			= "member_table";
	public static String colMemID		= "id";
	public static String colMemName    = "name";
	public static String colMemPhone 		= "phone";
	public static String colMemLat 	= "lat";
	public static String colMemLon    = "lon";
	public static String colMemStat	= "status";
	private ArrayList<PartyMember> party = new ArrayList<PartyMember>();
	private PartyMember mem;
	Context c;

	public DatabaseHelper(Context context) {
		super(context, DATABASENAME, null, 33);
		c = context;
	}
	
	public void deleteTable(){
			getWritableDatabase().execSQL("DROP TABLE member_table");
			this.getWritableDatabase().close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE if not exists member_table(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name"   + " TEXT ,"
				+ "phone"+ " TEXT,"
				+ "lat" + " TEXT,"
				+ "lon"+ " TEXT,"
				+ "status"+ " TEXT);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE);
		onCreate(db);
	}


	public void addMem(PartyMember mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", mem.getName());
		contentValues.put("phone", mem.getNumber());
		contentValues.put("lat", mem.getLat());
		contentValues.put("lon", mem.getLon());
		contentValues.put("status", mem.getStatus());
		db.insert("member_table", null, contentValues);
		db.close();

	}
	
	
	public ArrayList<PartyMember> getPartyMembers() {

		party.clear();
		
		Double aLat;
		Double aLon;
		PartyMember mem;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM member_table", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				 while(cursor.moveToNext()){
					mem = new PartyMember();
					mem.setId(cursor.getString(cursor.getColumnIndex("id")));
					mem.setName(cursor.getString(cursor.getColumnIndex("name")));
					mem.setNumber(cursor.getString(cursor.getColumnIndex("phone")));
					aLat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
					mem.setLat(aLat);
					aLon = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lon")));
					mem.setLon(aLon);
					mem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
					party.add(mem);
				}
			}
		}
		cursor.close();
		db.close();
		return party;
	}
	
	public ArrayList<PartyMember> getPartyMembersByName() {
		party.clear();
		
		Double aLon;
		Double aLat;

		SQLiteDatabase db = this.getWritableDatabase();
		//Cursor cursor = db.rawQuery("select * from member_table where name = ? ", new String[]{d});
		Cursor cursor = db.rawQuery("SELECT * FROM member_table order by name", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {//if there is something in the cursor at the first point
				do {
					mem = new PartyMember();
					mem.setId(cursor.getString(cursor.getColumnIndex("id")));
					mem.setName(cursor.getString(cursor.getColumnIndex("name")));
					mem.setNumber(cursor.getString(cursor.getColumnIndex("phone")));
					aLat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
					mem.setLat(aLat);
					aLon = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lon")));
					mem.setLon(aLon);
					mem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
					party.add(mem);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return party;
	}

	// updating

	public void updateItem(PartyMember mem) {
		
		Log.i("PartyUp", "Inside the updatePartyMember");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", mem.getName());
		contentValues.put("phone", mem.getNumber());
		contentValues.put("lat", mem.getLat());
		contentValues.put("lon", mem.getLon());
		contentValues.put("status", mem.getStatus());
		db.update("member_table", contentValues, "id="
				+ mem.getId(), null);

		db.close();
	}


	public void deleteItem(String item_id) {
		String[] args = { item_id };
		getWritableDatabase().delete("member_table", "id=?", args);
	}

	
}
