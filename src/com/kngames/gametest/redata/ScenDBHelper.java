package com.kngames.gametest.redata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScenDBHelper extends SQLiteOpenHelper {
	
	static final String dbname = "ScenDB";
	
	static final String scentable = "ScenarioTable";
	static final String customscentable = "CustomScenarioTable";
	
	static final String scentableID = "ScenID";
	static final String scentableName = "ScenName";
	static final String scentableContents = "ScenContents";
	static final String scentableDescript = "ScenDescription";
	static final String scentableNotes = "ScenNotes";
	static final String scentableGamemode = "ScenMode";
	static final String scentableBasics = "ScenBasics";

	
	public ScenDBHelper(Context context){
		super(context, dbname, null, 1); 
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE " + scentable + " (" + 
				scentableID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				scentableName + " TEXT, " + 
				scentableContents + " TEXT, " +
				scentableDescript + " TEXT, " +
				scentableNotes + " TEXT, " +
				scentableGamemode + " INTEGER, " +
				scentableBasics + " INTEGER)");
		
		db.execSQL("CREATE TABLE " + customscentable + " (" + 
				scentableID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				scentableName + " TEXT, " + 
				scentableContents + " TEXT, " +
				scentableDescript + " TEXT, " +
				scentableNotes + " TEXT, " +
				scentableGamemode + " INTEGER, " +
				scentableBasics + " INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + customscentable);
		onCreate(db);
	}
	
	public int addScenario(Scenario scen) {
		int basics = 0;
		if (scen.useBasics() == true) basics = 1;
		return addScenario(scen.name(), scen.generateSingleTagString(), scen.description(), scen.notes(), scen.mode(), basics);
	}
	
	public int addScenario(String name, String contents, String description, String notes, int gamemode, int basics){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues ing = new ContentValues();
		ing.put(scentableName, name);
		ing.put(scentableContents, contents);
		ing.put(scentableDescript, description);
		ing.put(scentableNotes, notes);
		ing.put(scentableGamemode, gamemode);
		ing.put(scentableBasics, basics);
		db.insert(customscentable, null, ing);
		
		int lastID = -1;
		Cursor c = db.rawQuery("SELECT "+scentableID+" from "+customscentable+" order by "+scentableID+" DESC limit 1", new String [] {});
		if (c != null && c.moveToFirst()) {
		    lastID = (int)c.getLong(0);
		}
		return lastID;
	}
	
	public void loadBuiltInScenarios (Scenario[] scen) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (Scenario s : scen) {
			ContentValues ing = new ContentValues();
			ing.put(scentableName, s.name());
			ing.put(scentableContents, s.generateSingleTagString());
			ing.put(scentableDescript, s.description());
			ing.put(scentableNotes, s.notes());
			ing.put(scentableGamemode, s.mode());
			ing.put(scentableBasics, s.useBasics());
			db.insert(scentable, null, ing);
		}
		db.close();
	}
	
	public void deleteScenario(int ID){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(customscentable, scentableID + "="+ID,null);
	}
	
	static int version = 1;
	public void forceUpgrade() {
		SQLiteDatabase db = this.getWritableDatabase();
		onUpgrade(db, version, version+1);
		version = 1 - version;
	}
	
	public boolean scenarioExists(int ID){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT * from "+customscentable+" WHERE "+scentableID+"='"+ID+"'", new String [] {});
		return cur.moveToFirst();		
	}
	
	public Cursor getScenario(int ID){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT * from "+customscentable+" WHERE "+scentableID+"='"+ID+"'", new String [] {});
		cur.moveToFirst();
		return cur;
	}
	
	public Cursor getAllScenarios(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT * from "+customscentable, new String [] {});
		cur.moveToFirst();
		return cur;
	}
		
	public void updateScenario(int id, Scenario scen) {
		updateName(id, scen.name());
		updateContents(id, scen.generateSingleTagString());
		updateDescription(id, scen.description());
		updateNotes(id, scen.notes());
		updateGameMode(id, scen.mode());
		int basics = 0;
		if (scen.useBasics() == true) basics = 1;
		updateBasic(id, basics);
	}
	
	public void updateName(int ID, String name){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+customscentable+" SET "+scentableName+"='"+name+"' WHERE "+scentableID+"="+ID);
	}
	
	public void updateContents(int ID, String contents){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+customscentable+" SET "+scentableContents+"='"+contents+"' WHERE "+scentableID+"="+ID);
	}

	public void updateDescription(int ID, String descript){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+customscentable+" SET "+scentableDescript+"='"+descript+"' WHERE "+scentableID+"="+ID);
	}
	
	public void updateNotes(int ID, String notes){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+customscentable+" SET "+scentableNotes+"='"+notes+"' WHERE "+scentableID+"="+ID);
	}

	public void updateGameMode(int ID, int mode){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+customscentable+" SET "+scentableGamemode+"="+mode+" WHERE "+scentableID+"="+ID);
	}

	public void updateBasic(int ID, int basics){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+customscentable+" SET "+scentableBasics+"="+basics+" WHERE "+scentableID+"="+ID);
	}
}
