package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.IDObject;

public class InfoZone extends REGameZone {
	
	public static final String TAG = InfoZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	public InfoZone(Rect area) {
		super(area);
	}
	public InfoZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public InfoZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public void postInit() { }
	
	public void update() { super.update(); }
	public void handleDownTouch(MotionEvent event) { }
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }
	public void handlePressTouch(MotionEvent event) { }

	//	draws this InfoZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		drawTestBorder(canvas);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TITLE_TEXT_SIZE);
		int textLocation = top() + TITLE_TEXT_SIZE + 5;
		
		drawTestData(canvas, generateInfoStrings(), textLocation);
	}
	
	private String[] generateInfoStrings() {
		String[] data = new String[] {
				getVisiblePlayer().character().getName(),
				String.format("health:   %d / %d", getVisiblePlayer().health, getVisiblePlayer().maxHealth),
				String.format("actions:  %d", getVisiblePlayer().actions),
				String.format("buys:     %d", getVisiblePlayer().buys),
				String.format("explores: %d", getVisiblePlayer().explores),
				String.format("ammo:     %d", getVisiblePlayer().ammo),
				String.format("gold:     %d", getVisiblePlayer().gold),
		};
		if (getVisiblePlayer().mustExplore) data[3] += "  (must explore)";
		return data;
	}
}
