package com.kngames.gametest.cards.graphics;

import java.util.HashMap;

import com.kngames.gametest.regame.gamestruct.Game;

import android.util.Log;

public class ZoneManager {
	protected static ZoneManager zoneMan = null;
	protected Game game = null;
	public Game getGame() { return game; }
	
	private HashMap<String,GameZone> zoneList;
	
	private static final String TAG = ZoneManager.class.getSimpleName();
	
	//	constructs a new ZoneManager
	//	since ZoneManager is a singleton, instantiate the ZoneManager using initZoneManager
	protected ZoneManager() {
		zoneList = new HashMap<String, GameZone>();
		Log.d(TAG, "ZoneManager instantiated");
	}
	
	//	instantiates a new ZoneManager if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static ZoneManager initZoneManager() {
		if (zoneMan == null) return new ZoneManager();
		else {
			Log.e(TAG, "ZoneManager already instantiated!");
			return zoneMan;
		}
	}
	
	//	returns the instance of the ZoneManager that already exists (even if there is none)
	public static ZoneManager getZoneManager() {
		return zoneMan;
	}
	
	//	sets the game object this ZoneManager is watching
	public void setGame(Game g) {
		game = g;
	}
	
	//	calls the postInit methods of all GameZones contained in the ZoneManager
	//	necessary for forming connections between zones
	public void postInit() {
		GameZone[] zones = getAllZones();
		for (GameZone z : zones) z.postInit();
	}
	
	public void updateZones() {
		GameZone[] zones = getAllZones();
		for (GameZone z : zones) z.update();
	}
	
	//	adds a new GameZone to this ZoneManager
	public void addZone(String key, GameZone zone) {
		zone.setManager(this);
		zoneList.put(key, zone);
	}
	
	//	searches for a Zone stored in ZoneManager, returns null if no zone is found
	public GameZone getZone(String key) {
		return zoneList.get(key);
	}
	
	//	retrieves all zones in this ZoneManager and returns them in an array
	public GameZone[] getAllZones() {
		GameZone[] zones = new GameZone[zoneList.size()];
		zoneList.values().toArray(zones);
		return zones;
	}
}