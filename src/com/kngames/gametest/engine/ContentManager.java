package com.kngames.gametest.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

//	ContentManager -
//
//	a singleton class designed to handle loading all needed Bitmaps from the Android resources
public class ContentManager {
	private static ContentManager contentMan = null;
	
	private SparseArray<Bitmap> content;
	private Resources res;
	
	//	constructs a new ContentManager
	//	since ContentManager is a singleton, instantiate the ContentManager using getContentManager
	private ContentManager(Resources res) {
		content = new SparseArray<Bitmap>();
		this.res = res;
	}
	
	//	instantiates a new ContentManager if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static ContentManager getContentManager(Resources resource) {
		if (contentMan == null) return new ContentManager(resource);
		else return contentMan;
	}
	
	//	returns a Bitmap with a given ID from the ContentManager
	//	if the Bitmap doesn't exist in the ContentManager, load it and store it first
	public Bitmap getBitmap(int id) {
		Bitmap temp = content.get(id);
		if (temp != null) return temp;
		else {
			temp = BitmapFactory.decodeResource(res, id);
			content.put(id, temp);
			return temp;
		}
	}
	
	//	returns a Bitmap with a given ID scaled to a specified width and height from the ContentManager
	//	if the Bitmap doesn't exist in the ContentManager, load it and store it first
	public Bitmap getScaledBitmap(int id, int width, int height) {
		return Bitmap.createScaledBitmap(getBitmap(id), width, height, false);
	}
}
