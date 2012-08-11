package com.kngames.gametest.cards.graphics;

import android.util.SparseArray;

public class IDObject {
	//	static structures designed to persist across all instantiations
	private static int CURRENT_ID_NUM = 0;
	private static SparseArray<String> idMap = new SparseArray<String>();
	public static int getIdNumber(String typeName) {
		int id = CURRENT_ID_NUM++;
		idMap.put(id, typeName);
		return id;
	}
	
	private String ENTITY_NAME;
	public String getName() { return ENTITY_NAME; }
	
	private int ENTITY_ID;
	public int getId() { return ENTITY_ID; }
	
	public IDObject(String name) {
		ENTITY_NAME = name;
		ENTITY_ID = getIdNumber(name);
	}
}