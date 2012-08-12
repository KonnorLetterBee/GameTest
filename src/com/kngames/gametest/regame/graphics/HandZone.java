package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.GameZone;
import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.cards.graphics.test.TestZone;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard;

public class HandZone extends GameZone {
	
	public static final String TAG = HandZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	private REDeck hand;
	private TestZone test;
	private String testTag;
	
	public HandZone(Rect area) {
		super(area);
	}
	public HandZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public HandZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	
	//	sets the tag the DeckZone will use to search the ZoneManager's hashmap
	public void setTestTag(String tag) {
		testTag = tag;
	}
	public void postInit() {
		test = (TestZone) manager.getZone(testTag);
	}
	
	//	sets the deck of this zone to that of the current viewing player
	//	(the player sitting in front of the screen)
	public void setHand() {
		hand = manager.getGame().players()[manager.getGame().viewingPlayer()].deck();
	}
	
	public void update() {
		setHand();
	}
	
	public void handleDownTouch(MotionEvent event) {
		RECard temp = (RECard) hand.popTop();
		if (temp != null) test.addData(temp.getName());
	}
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }

	//	draws this DeckZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
	private final int SUB_TEXT_SIZE = 20;
	private final int BORDER_THICKNESS = 4;
	public void draw(Canvas canvas) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		canvas.drawRect(area, paint);

		paint.setColor(Color.BLACK);
		canvas.drawRect(new Rect (
				area.left + BORDER_THICKNESS,
				area.top + BORDER_THICKNESS,
				area.right - BORDER_THICKNESS,
				area.bottom - BORDER_THICKNESS), paint);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TITLE_TEXT_SIZE);
		int textLocation = area.top + TITLE_TEXT_SIZE + 5;
		canvas.drawText(TAG, area.left + 10, textLocation, paint);
		for (int i = 0; i < hand.size(); i++) {
			textLocation += TITLE_TEXT_SIZE + 5;
			canvas.drawText(((RECard)hand.peek(i)).getName(), area.left + 10, textLocation, paint);
		}
	}
}
