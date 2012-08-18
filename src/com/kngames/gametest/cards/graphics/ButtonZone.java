package com.kngames.gametest.cards.graphics;

import com.kngames.gametest.regame.graphics.REGameZone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class ButtonZone extends REGameZone {

	private OnPressListener press;
	private String buttonText;
	
	public ButtonZone(Rect area, String text, OnPressListener listener) {
		super(area);
		press = listener;
		this.buttonText = text;
	}
	
	public ButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode, String text, ButtonZone.OnPressListener listener) {
		super(x, y, originCorner, width, height, sizeMode);
		press = listener;
		this.buttonText = text;
	}
	
	public ButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode, String text, ButtonZone.OnPressListener listener) {
		super(x, y, originCorner, width, height, sizeMode);
		press = listener;
		this.buttonText = text;
	}

	public void postInit() { }
	public void handleDownTouch(MotionEvent event) {
		super.handleDownTouch(event);
	}
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) {
		super.handleUpTouch(event);
	}
	public void handlePressTouch(MotionEvent event) {
		if (press != null) press.buttonPressed();
	}

	public void update() { }

	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		drawTestBorder(canvas);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		int textLocation = area.top + 20 + 5;
		canvas.drawText(buttonText, area.left + 10, textLocation, paint);
	}

	public static interface OnPressListener {
		public void buttonPressed();
	}
}
