package com.kngames.gametest.cards.graphics;

import com.kngames.gametest.engine.graphics.AnimationComponent;
import com.kngames.gametest.engine.graphics.MovementComponent;
import com.kngames.gametest.engine.interfaces.*;

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
	protected static GameZone selectedZone = null;
	
	//	position and animation components of GameZones
	protected MovementComponent move;
	protected int width;
	protected int height;
	protected AnimationComponent ani;
	
	protected ZoneManager manager;
	
	private static final String TAG = GameZone.class.getSimpleName();
	
	protected static int screenWidth;
	protected static int screenHeight;
	
	public static final int PRESERVE_HEIGHT = 0;
	public static final int PRESERVE_WIDTH = 1;
	public static final int STRETCH = 2;
	
	public static final int TOP_LEFT = 0;
	public static final int TOP_RIGHT = 1;
	public static final int BOTTOM_LEFT = 2;
	public static final int BOTTOM_RIGHT = 3;
	
	public GameZone(Rect area) {
		//this.area = area;
		
		this.move = new MovementComponent(area.left, area.top, 0, 0);
		this.width = area.right - area.left;
		this.height = area.bottom - area.top;
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
		
		this.width = calcWidth;
		this.height = calcHeight;
		
		switch (originCorner) {
		case TOP_LEFT:		
			//area =	new Rect(x, y, x + calcWidth, calcHeight + y);
			move = new MovementComponent(x, y, 0, 0);
			break;
		case TOP_RIGHT:		
			//area =	new Rect(x - calcWidth, y, x, calcHeight + y);
			move = new MovementComponent(x - calcWidth, y, 0, 0);
			break;
		case BOTTOM_LEFT:	
			//area =	new Rect(x, y - calcHeight, x + calcWidth, y);
			move = new MovementComponent(x, y - calcHeight, 0, 0);
			break;
		case BOTTOM_RIGHT:	
			//area =	new Rect(x - calcWidth, y - calcHeight, x, y);
			move = new MovementComponent(x - calcWidth, y - calcHeight, 0, 0);
			break;
		default:
			Log.e(TAG, "invalid origin corner indicator, defaulting to upper-left");
			move = new MovementComponent(x, y, 0, 0);  break;
		}
	}
	
	public void testLocation (Rect r, MovementComponent m) {
		if (r.left != (int)m.x() || r.top != (int)m.y()) {
			Log.d(TAG, String.format("Error, difference in x or y component"));
			Log.d(TAG, String.format("x(rect) = %d, x(move) = %d", r.left, m.x()));
			Log.d(TAG, String.format("y(rect) = %d, y(move) = %d", r.top, m.y()));
		}
	}
	
	public GameZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		this((int)(screenWidth * x), (int)(screenHeight * y), originCorner, width, height, sizeMode);
	}
	
	public abstract void postInit();

	///
	///	Getters and Setters
	///
		
	public int left() { return (int)move.x(); }
	public int right() { return (int)move.x() + width; }
	public int top() { return (int)move.y(); }
	public int bottom() { return (int)move.y() + height; }
	
	public float percLeft() { return (float)left() / screenWidth; }
	public float percRight() { return (float)right() / screenWidth; }
	public float percTop() { return (float)top() / screenHeight; }
	public float percBottom() { return (float)bottom() / screenHeight; }
	
	public int width() { return width; }
	public int height() { return height; }
	
	public float percWidth() { return (float)width / screenWidth; }
	public float percHeight() { return (float)height / screenHeight; }
	
	public float centerX() { return left() + ((float)width / 2); }
	public float centerY() { return top() + ((float)height / 2); }
	
	public Rect generateRect() {
		int x = (int) move.x();
		int y = (int) move.y();
		return new Rect(x, y, x + width, y + height);
	}
	
	public void setX(int val) { move.setX(val); }
	public void setY(int val) { move.setY(val); }
	public void setWidth(int val) { this.width = val; }
	public void setHeight(int val) { this.height = val; }
	
	public ZoneManager getManager() { return manager; }
	public void setManager(ZoneManager man) { manager = man; }
	
	public static void initZones(int width, int height) {
		screenHeight = height;
		screenWidth = width;
	}
	
	public boolean isTouched(float x, float y, boolean allowBorder) {
		if (allowBorder) return (x >= move.x()) && 
					(x <= move.x() + width) && 
					(y >= move.y()) && 
					(y <= move.y() + height);
		else return (x > move.x()) && 
					(x < move.x() + width) && 
					(y > move.y()) && 
					(y < move.y() + height);
	}
	
	public void handleDownTouch(MotionEvent event) {
		selectedZone = this;
	}
	
	public abstract void handleOffDownTouch(MotionEvent event);

	public abstract void handleMoveTouch(MotionEvent event);

	public void handleUpTouch(MotionEvent event) {
		if (selectedZone == this) 
			this.handlePressTouch(event);
		selectedZone = null;
	}
	
	public abstract void handlePressTouch(MotionEvent event);
	
	public abstract void update();
	
	public abstract void draw(Canvas canvas);
}
