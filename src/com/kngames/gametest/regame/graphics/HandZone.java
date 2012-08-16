package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.redata.CardTypes.RECard;

public class HandZone extends REGameZone {
	
	public static final String TAG = HandZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	public HandZone(Rect area) {
		super(area);
	}
	public HandZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public HandZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public void postInit() { }
	
	public void update() { }
	public void handleDownTouch(MotionEvent event) {
		if (getVisiblePlayer().hand().size() > 0)
			getVisiblePlayer().playCard(0);
	}
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }

	//	draws this HandZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
	public void draw(Canvas canvas) {
		Paint paint = new Paint(); 
		drawTestBorder(canvas);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TITLE_TEXT_SIZE);
		int textLocation = area.top + TITLE_TEXT_SIZE + 5;
		canvas.drawText(TAG, area.left + 10, textLocation, paint);

		String[] data = new String[getVisibleHand().size()];
		for (int i = 0; i < getVisibleHand().size(); i++) {
			data[i] = ((RECard)getVisibleHand().peek(i)).getName();
		}
		drawTestData(canvas, data, textLocation + TITLE_TEXT_SIZE + 15);
	}
}
