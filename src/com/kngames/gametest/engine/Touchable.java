package com.kngames.gametest.engine;

import android.view.MotionEvent;

public interface Touchable {
	public void handleDownTouch(MotionEvent event);
	public void handleMoveTouch(MotionEvent event);
	public void handleUpTouch(MotionEvent event);
}
