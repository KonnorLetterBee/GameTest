package com.kngames.gametest.engine.interfaces;

import android.view.MotionEvent;

public interface Touchable {
	//	checks if, given an (x,y) coordinate, the touch was inside the given object
	//	returns true if object was touched, false otherwise
	public boolean isTouched(float x, float y);
	
	//	handles a touch event where the object is initially being touched
	public void handleDownTouch(MotionEvent event);
	
	//	handles a case when a down touch is detected at a coordinate outside this object
	public void handleOffDownTouch(MotionEvent event);
	
	//	handles a touch event where the objects is already touched and the location of the touch is moving
	public void handleMoveTouch(MotionEvent event);
	
	//	handles a touch event where the object is no longer being touched
	public void handleUpTouch(MotionEvent event);
}
