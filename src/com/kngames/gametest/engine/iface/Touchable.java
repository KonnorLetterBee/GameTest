package com.kngames.gametest.engine.iface;

import android.view.MotionEvent;

public interface Touchable {
	public boolean isTouched(float x, float y);
	public void handleDownTouch(MotionEvent event);
	public void handleMoveTouch(MotionEvent event);
	public void handleUpTouch(MotionEvent event);
}
