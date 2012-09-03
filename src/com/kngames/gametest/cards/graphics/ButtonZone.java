package com.kngames.gametest.cards.graphics;

import com.kngames.gametest.regame.graphics.REGameZone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ButtonZone extends REGameZone {

	protected OnPressListener press;
	public String buttonText;
	
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
	public void handleDownTouch(float x, float y) {
		super.handleDownTouch(x, y);
	}
	public void handleOffDownTouch(float x, float y) { }
	public void handleMoveTouch(float x, float y) { }
	public void handleUpTouch(float x, float y) { super.handleUpTouch(x, y); }
	public void handlePressTouch(float x, float y) { if (active && press != null) press.buttonPressed(); }
	
	public void activate() {
		this.active = true;
	}
	public void deactivate() {
		this.active = false;
	}

	public void update() { }

	private final int TEXT_SIZE = 20;
	public void drawToBitmapCanvas(Canvas canvas) {
		if (active) {
			Paint paint = new Paint();
			drawTestBorder(canvas);
			
			paint.setColor(Color.WHITE);
			paint.setTextSize(TEXT_SIZE);
			int textLocation = TEXT_SIZE + 5;
			canvas.drawText(buttonText, 10, textLocation, paint);
		}
	}

	public static interface OnPressListener {
		public void buttonPressed();
	}
}
