package com.kngames.gametest.regame.graphics;

import com.kngames.gametest.cards.graphics.ZoneManager;

import android.util.Log;

public class REZoneManager extends ZoneManager {
	private static final String TAG = REZoneManager.class.getSimpleName();
	
	//	constructs a new ZoneManager
	//	since ZoneManager is a singleton, instantiate the ZoneManager using initZoneManager
	protected REZoneManager() {
		super();
		Log.d(TAG, "REZoneManager instantiated");
	}
	
	//	instantiates a new ZoneManager if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static REZoneManager initREZoneManager() {
		if (zoneMan == null) zoneMan = new REZoneManager();
		else Log.e(TAG, "REZoneManager already instantiated!");
		return (REZoneManager)zoneMan;
	}
	
	//	returns the instance of the ZoneManager that already exists (even if there is none)
	public static REZoneManager getREZoneManager() {
		return (REZoneManager)zoneMan;
	}
	
	//	adds a new GameZone to this REZoneManager
	//	sets the REGameZone's game field before adding it so that it doesn't have to be done manually
	public void addZone(String key, REGameZone zone) {
		zone.setGame(game);
		Log.d(TAG, "REZoneManager zone added");
		super.addZone(key, zone);
	}
}