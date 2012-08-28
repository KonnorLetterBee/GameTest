package com.kngames.gametest.cards.graphics;

import com.kngames.gametest.regame.graphics.REGameZone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class ButtonZone extends REGameZone {

	protected OnPressListener press;
	protected String buttonText;
	public boolean active = true;
	
	public ButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder, String text, ButtonZone.OnPressListener listener) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder);
		press = listener;
		this.buttonText = text;
	}
	
	public ButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder, String text, ButtonZone.OnPressListener listener) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder);
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
		if (active && press != null) press.buttonPressed();
	}

	public void update() { }

	public void draw(Canvas canvas) {
		if (active) {
			Paint paint = new Paint();
			drawTestBorder(canvas);
			
			paint.setColor(Color.WHITE);
			paint.setTextSize(20);
			int textLocation = (int)move.y() + 20 + 5;
			canvas.drawText(buttonText, (int)move.x() + 10, textLocation, paint);
		}
	}

	public static interface OnPressListener {
		public void buttonPressed();
	}
}
