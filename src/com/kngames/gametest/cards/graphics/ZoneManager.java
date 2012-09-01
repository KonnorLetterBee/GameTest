package com.kngames.gametest.cards.graphics;

import java.util.ArrayList;
import java.util.HashMap;

import com.kngames.gametest.regame.gamestruct.Game;

import android.graphics.Canvas;
import android.util.Log;

public class ZoneManager {
	protected static ZoneManager zoneMan = null;
	protected Game game = null;
	public Game getGame() { return game; }
	
	private HashMap<String,GameZone> zoneList;
	private ArrayList<GameZone> drawOrder;
	
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
		if (zoneMan == null)  zoneMan = new ZoneManager();
		else  Log.e(TAG, "ZoneManager already instantiated!");
		return zoneMan;
	}
	
	//	returns the instance of the ZoneManager that already exists (even if there is none)
	public static ZoneManager getZoneManager() {
		return zoneMan;
	}
	
	//	sets the stored ZoneManager to null
	public static void destroy() {
		zoneMan = null;
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
		this.generateDrawList();
	}
	
	public void update() {
		GameZone[] zones = getAllZones();
		for (GameZone z : zones) z.update();
	}
	
	//	draws all GameZones in the ZoneManager to the Canvas
	public void draw(Canvas canvas) {
		for (GameZone z : drawOrder) {
			z.draw(canvas);
		}
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
	
	//	generates the draw list for all zones in this ZoneManager, based on each zone's 
	public void generateDrawList() {
		drawOrder = new ArrayList<GameZone>();
		GameZone[] zones = getAllZones();
		for (int i = 0; i < zones.length; i++) {
			boolean added = false;
			for (int j = 0; j < drawOrder.size(); j++) {
				if (zones[i].drawPriority <= drawOrder.get(j).drawPriority) {
					drawOrder.add(j, zones[i]);
					added = true;
					break;
				}
			}
			if (!added) drawOrder.add(drawOrder.size(), zones[i]);
		}
	}
	
	//	retrieves all zones in this ZoneManager and returns them in an array
	public GameZone[] getAllZones() {
		GameZone[] zones = new GameZone[zoneList.size()];
		zoneList.values().toArray(zones);
		return zones;
	}
}