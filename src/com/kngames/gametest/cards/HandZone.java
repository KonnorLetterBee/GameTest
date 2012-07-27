package com.kngames.gametest.cards;

import java.util.ArrayList;

import com.kngames.gametest.engine.DrawObject;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class HandZone extends GameZone {

	protected ArrayList<DrawObject> drawables;
	
	public HandZone(Rect area) {
		super(area);
		drawables = new ArrayList<DrawObject>();
	}

	@Override
	public void handleDownTouch(MotionEvent event) { }

	@Override
	public void handleMoveTouch(MotionEvent event) { }

	@Override
	public void handleUpTouch(MotionEvent event) { }
	
	@Override
	public void draw(Canvas canvas) {
		
	}
}
