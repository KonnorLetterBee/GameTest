package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.IDObject;

public class GameStateZone extends REGameZone {
	
	public static final String TAG = GameStateZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	public GameStateZone(Rect area) {
		super(area);
	}
	public GameStateZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public GameStateZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public void postInit() { }
	
	public void update() { }
	public void handleDownTouch(MotionEvent event) { }
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }
	public void handlePressTouch(MotionEvent event) { }

	//	draws this InfoZone to the screen
	private final int TITLE_TEXT_SIZE = height;
	private int textX = left();
	private int textY = top() + TITLE_TEXT_SIZE;
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TITLE_TEXT_SIZE);
		
		String message = String.format("Turn %d: %s", game.gameTurn(), game.gameStateMessage);
		canvas.drawText(message, textX, textY, paint);
	}
}
