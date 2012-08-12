package com.kngames.gametest.cards.graphics;

import com.kngames.gametest.engine.iface.*;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

//	GameZone -
//
//	game zones are areas on the screen that support a multitude of functions, such as keeping track of
//	DrawObjects, providing drawing functions for those objects (and the zone as a whole), handling/passing
//	touch input, and are designed to be scaled to different screen sizes (based on the size of the zone)
//
//	this class is designed to be subclassed to provide differentiating touch behaviors and drawing algorithms
public abstract class GameZone implements Touchable, Drawable {
	
	protected Rect area;
	protected ZoneManager manager;
	
	private static final String TAG = GameZone.class.getSimpleName();
	
	protected static int screenWidth;
	protected static int screenHeight;
	
	/*
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	*/
	
	public static final int PRESERVE_HEIGHT = 0;
	public static final int PRESERVE_WIDTH = 1;
	public static final int STRETCH = 2;
	
	public static final int TOP_LEFT = 0;
	public static final int TOP_RIGHT = 1;
	public static final int BOTTOM_LEFT = 2;
	public static final int BOTTOM_RIGHT = 3;
	
	public GameZone(Rect area) {
		this.area = area;
	}
	
	//	PRESERVE_HEIGHT: height = percentage of total screen height, width = percentage of calculated height
	//	PRESERVE_WIDTH: width = percentage of total screen width, height = percentage of calculated width
	//	STRETCH: both values are percentage of screen
	public GameZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		int calcWidth;
		int calcHeight;
		
		//	calculate the length and width of the zone
		switch (sizeMode) {
		case PRESERVE_HEIGHT:
			calcHeight = (int)(screenHeight * height);
			calcWidth = (int)(calcHeight * width);
			break;
		case PRESERVE_WIDTH:
			calcWidth = (int)(screenWidth * width);
			calcHeight = (int)(calcWidth * height);
			break;
		case STRETCH:
			calcWidth = (int)(screenWidth * width);
			calcHeight = (int)(screenHeight * height);
			break;
		default:
			Log.e(TAG, "invalid size mode, defaulting to STRETCH");
			calcWidth = (int)(screenWidth * width);
			calcHeight = (int)(screenHeight * height);
			break;
		}
		
		switch (originCorner) {
		case TOP_LEFT:	area =		new Rect(x, y, x + calcWidth, calcHeight + y);  break;
		case TOP_RIGHT:	area =		new Rect(x - calcWidth, y, x, calcHeight + y);  break;
		case BOTTOM_LEFT:	area =	new Rect(x, y - calcHeight, x + calcWidth, y);  break;
		case BOTTOM_RIGHT:	area =	new Rect(x - calcWidth, y - calcHeight, x, y);	break;
		default:
			Log.e(TAG, "invalid origin corner indicator, defaulting to upper-left");
			area = new Rect(x, y, calcWidth + x, calcHeight + y);  break;
		}
	}
	
	public GameZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		this((int)(screenWidth * x), (int)(screenHeight * y), originCorner, width, height, sizeMode);
	}
	
	public abstract void postInit();

	///
	///	Getters and Setters
	///
		
	public int left() { return area.left; }
	public int right() { return area.right; }
	public int top() { return area.top; }
	public int bottom() { return area.bottom; }
	public float centerX() { return area.exactCenterX(); }
	public float centerY() { return area.exactCenterY(); }
	
	public void setLeft(int val) { area.left = val; }
	public void setRight(int val) { area.right = val; }
	public void setTop(int val) { area.top = val; }
	public void setBottom(int val) { area.bottom = val; }
	
	public ZoneManager getManager() { return manager; }
	public void setManager(ZoneManager man) { manager = man; }
	
	public static void initZones(int width, int height) {
		screenHeight = height;
		screenWidth = width;
	}
	
	public boolean isTouched(float x, float y) {
		return area.contains((int)x, (int)y);
	}
	
	public abstract void handleDownTouch(MotionEvent event);
	
	public abstract void handleOffDownTouch(MotionEvent event);

	public abstract void handleMoveTouch(MotionEvent event);

	public abstract void handleUpTouch(MotionEvent event);
	
	public abstract void update();
	
	public abstract void draw(Canvas canvas);
}
