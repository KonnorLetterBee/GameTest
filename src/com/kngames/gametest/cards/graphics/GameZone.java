package com.kngames.gametest.cards.graphics;

import com.kngames.gametest.engine.graphics.AnimationComponent;
import com.kngames.gametest.engine.graphics.MovementComponent;
import com.kngames.gametest.engine.interfaces.*;

import android.graphics.Canvas;
import android.graphics.Point;
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
	protected int drawPriority = 0;		//	higher values get drawn first
	
	private static final String TAG = GameZone.class.getSimpleName();
	
	protected static int screenWidth;
	protected static int screenHeight;
	
	//	values for determining scaling behavior
	//	PRESERVE_HEIGHT: height = percentage of total screen height, width = percentage of calculated height
	//	PRESERVE_WIDTH: width = percentage of total screen width, height = percentage of calculated width
	//	STRETCH: both values are percentage of screen
	public static final int PRESERVE_HEIGHT = 0;
	public static final int PRESERVE_WIDTH = 1;
	public static final int STRETCH = 2;
	
	//	values for determining positioning of the origin
	public static final int TOP_LEFT = 0;
	public static final int TOP_RIGHT = 1;
	public static final int BOTTOM_LEFT = 2;
	public static final int BOTTOM_RIGHT = 3;
	
	protected boolean active = true;
	protected Point activePoint;
	protected Point inactivePoint;
	protected boolean hasInactiveState = false;
	
	private static final float ZONE_MOVE_SPEED = 20f;
	
	//	constructs a new rectangular GameZone
	//	x, y - absolute coordinates in 2D space where the origin is located
	//	originCorner - the corner of the rectangle where the origin is located
	//		(in the event of anything other than TOP_LEFT, the actual coordinates stored in the object
	//		will be modified to correspond to the top left corner of the rectangle, this is just to make
	//		instantiating GameZones easier)
	//	width, height - the width and height expressed as a percentage of screen width/height
	//	sizeMode - determines the behavior of the width and height variables:
	//		PRESERVE_HEIGHT: height = percentage of total screen height, width = percentage of calculated height
	//		PRESERVE_WIDTH: width = percentage of total screen width, height = percentage of calculated width
	//		STRETCH: both values are percentage of screen
	//	drawOrder - determines when this GameZone should be drawn, zones with higher numbers get drawn over 
	//		zones with lower numbers
	public GameZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
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
			move = new MovementComponent(x, y, 0, 0);
			break;
		case TOP_RIGHT:		
			move = new MovementComponent(x - calcWidth, y, 0, 0);
			break;
		case BOTTOM_LEFT:	
			move = new MovementComponent(x, y - calcHeight, 0, 0);
			break;
		case BOTTOM_RIGHT:	
			move = new MovementComponent(x - calcWidth, y - calcHeight, 0, 0);
			break;
		default:
			Log.e(TAG, "invalid origin corner indicator, defaulting to upper-left");
			move = new MovementComponent(x, y, 0, 0);  break;
		}
		
		this.activePoint = new Point((int)move.x(), (int)move.y());
		this.ani = new AnimationComponent(move);
		this.drawPriority = drawOrder;
	}
	
	//	constructs a new rectangular GameZone
	//	x, y - coordinates in 2D space where the origin is located expressed as a percentage of screen width/height
	//	originCorner - the corner of the rectangle where the origin is located
	//		(in the event of anything other than TOP_LEFT, the actual coordinates stored in the object
	//		will be modified to correspond to the top left corner of the rectangle, this is just to make
	//		instantiating GameZones easier)
	//	width, height - the width and height expressed as a percentage of screen width/height
	//	sizeMode - determines the behavior of the width and height variables:
	//		PRESERVE_HEIGHT: height = percentage of total screen height, width = percentage of calculated height
	//		PRESERVE_WIDTH: width = percentage of total screen width, height = percentage of calculated width
	//		STRETCH: both values are percentage of screen
	//	drawOrder - determines when this GameZone should be drawn, zones with higher numbers get drawn over 
	//		zones with lower numbers
	public GameZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		this((int)(screenWidth * x), (int)(screenHeight * y), originCorner, width, height, sizeMode, drawOrder);
	}
	
	//	constructs a new rectangular GameZone
	//	actX, actY - absolute coordinates in 2D space where the origin is located for the zone's active state
	//	inactX, inactY - absolute coordinates in 2D space where the origin is located for the zone's inactive state
	//	originCorner - the corner of the rectangle where the origin is located
	//		(in the event of anything other than TOP_LEFT, the actual coordinates stored in the object
	//		will be modified to correspond to the top left corner of the rectangle, this is just to make
	//		instantiating GameZones easier, this modifier applies to both the active and inactive states)
	//	width, height - the width and height expressed as a percentage of screen width/height
	//	sizeMode - determines the behavior of the width and height variables:
	//		PRESERVE_HEIGHT: height = percentage of total screen height, width = percentage of calculated height
	//		PRESERVE_WIDTH: width = percentage of total screen width, height = percentage of calculated width
	//		STRETCH: both values are percentage of screen
	//	drawOrder - determines when this GameZone should be drawn, zones with higher numbers get drawn over 
	//		zones with lower numbers
	public GameZone(int actX, int actY, int inactX, int inactY, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		this(actX, actY, originCorner, width, height, sizeMode, drawOrder);
		
		switch (originCorner) {
		case TOP_LEFT:		
			this.inactivePoint = new Point(inactX, inactY);
			break;
		case TOP_RIGHT:		
			this.inactivePoint = new Point(inactX - this.width, inactY);
			break;
		case BOTTOM_LEFT:	
			this.inactivePoint = new Point(inactX, inactY - this.height);
			break;
		case BOTTOM_RIGHT:	
			this.inactivePoint = new Point(inactX - this.width, inactY - this.height);
			break;
		default:
			Log.e(TAG, "invalid origin corner indicator, defaulting to upper-left");
			this.inactivePoint = new Point(inactX, inactY);
		}
		
		hasInactiveState = true;
	}
	
	//	constructs a new rectangular GameZone
	//	actX, actY - coordinates in 2D space where the origin is located in the zone's active state expressed 
	//		as a percentage of screen width/height
	//	inactX, inactY - coordinates in 2D space where the origin is located in the zone's inactive state expressed 
	//		as a percentage of screen width/height
	//	originCorner - the corner of the rectangle where the origin is located
	//		(in the event of anything other than TOP_LEFT, the actual coordinates stored in the object
	//		will be modified to correspond to the top left corner of the rectangle, this is just to make
	//		instantiating GameZones easier, this modifier applies to both the active and inactive states)
	//	width, height - the width and height expressed as a percentage of screen width/height
	//	sizeMode - determines the behavior of the width and height variables:
	//		PRESERVE_HEIGHT: height = percentage of total screen height, width = percentage of calculated height
	//		PRESERVE_WIDTH: width = percentage of total screen width, height = percentage of calculated width
	//		STRETCH: both values are percentage of screen
	//	drawOrder - determines when this GameZone should be drawn, zones with higher numbers get drawn over 
	//		zones with lower numbers
	public GameZone(float actX, float actY, float inactX, float inactY, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		this((int)(screenWidth * actX), (int)(screenHeight * actY),
			(int)(screenWidth * inactX), (int)(screenHeight * inactY),
			originCorner, width, height, sizeMode, drawOrder);
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
	
	public int drawPriority() { return drawPriority; }
	public void setDrawPriority (int priority) {
		drawPriority = priority;
		manager.generateDrawList();
	}
	
	public boolean isActive() { return active; }
	
	//	forcefully sets the active state to the specified state, and immediately updates
	//	the position of the zone to match
	public void setActive(boolean active) {
		this.active = active;
		if (active) this.move.setPosition(activePoint.x, activePoint.y);
		else this.move.setPosition(inactivePoint.x, inactivePoint.y);
	}
	
	//	deactivates this zone, moving it to the deactivated area with an animation
	public void deactivate() {
		if (!this.hasInactiveState) {	//	return an error if this zone has no inactive state
			Log.e(TAG, String.format("Error: game zone %s has no inactive state", TAG));
			return;
		} else if (!this.active) {	//	return an error if this state is already inactive
			Log.e(TAG, String.format("Error: game zone %s is already inactive", TAG));
			return;
		} else {
			this.active = false;
			this.ani.moveToPoint(inactivePoint.x, inactivePoint.y, ZONE_MOVE_SPEED);
		}
	}
	
	//	activates this zone, moving it to the activated area with an animation
	public void activate() {
		if (!this.hasInactiveState) {	//	return an error if this zone has no inactive state
			Log.e(TAG, String.format("Error: game zone %s has no inactive state", TAG));
			return;
		} else if (this.active) {	//	return an error if this state is already active
			Log.e(TAG, String.format("Error: game zone %s is already active", TAG));
			return;
		} else {
			this.active = true;
			this.ani.moveToPoint(activePoint.x, activePoint.y, ZONE_MOVE_SPEED);
		}
	}
	
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
	
	public void update() {
		this.move.update();
		this.ani.update();
	}
	
	public abstract void draw(Canvas canvas);
}
