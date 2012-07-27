package com.kngames.gametest.cards;

import com.kngames.gametest.engine.iface.*;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

//	GameZone -
//
//	game zones are areas on the screen that support a multitude of functions, such as keeping track of
//	DrawObjects, providing drawing functions for those objects (and the zone as a whole), handling/passing
//	touch input, and are designed to be scaled to different screen sizes (based on the size of the zone)
//
//	this class is designed to be subclassed to provide differentiating touch behaviors and drawing algorithms
public class GameZone implements Touchable, Drawable {
	
	protected Rect area;
	
	public GameZone(Rect area) {
		this.area = area;
	}

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
	
	@Override
	public boolean isTouched(float x, float y) {
		return area.contains((int)x, (int)y);
	}
	
	@Override
	public void handleDownTouch(MotionEvent event) { }

	@Override
	public void handleMoveTouch(MotionEvent event) { }

	@Override
	public void handleUpTouch(MotionEvent event) { }
	
	@Override
	public void draw(Canvas canvas) { }
}
