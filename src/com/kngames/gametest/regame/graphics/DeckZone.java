package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.regame.gamestruct.Game;

public class DeckZone extends REGameZone {
	
	public static final String TAG = DeckZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	public DeckZone(Rect area) {
		super(area);
	}
	public DeckZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public DeckZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	
	public void postInit() { }
	
	public void update() { }
	public void handleDownTouch(MotionEvent event) {
		if (Game.DEBUG_MODE) getVisiblePlayer().drawToHand();
	}
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }
	public void handlePressTouch(MotionEvent event) { }
	
	//	draws this DeckZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
	private final int SUB_TEXT_SIZE = 20;
	public void draw(Canvas canvas) {
		Paint paint = new Paint(); 
		drawTestBorder(canvas);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TITLE_TEXT_SIZE);
		int textLocation = area.top + TITLE_TEXT_SIZE + 5;
		canvas.drawText(TAG, area.left + 10, textLocation, paint);
		textLocation += TITLE_TEXT_SIZE + 5;
		paint.setTextSize(SUB_TEXT_SIZE);
		canvas.drawText(String.format("%d x %d", area.right-area.left, area.bottom-area.top), area.left + 10, textLocation, paint);
		textLocation += TITLE_TEXT_SIZE + 5;
		canvas.drawText("Cards left: " + getVisibleDeck().size(), area.left + 10, textLocation, paint);
	}
}
