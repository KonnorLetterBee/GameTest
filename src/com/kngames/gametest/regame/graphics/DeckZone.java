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

public class DeckZone extends GameZone {
	
	public static final String TAG = DeckZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	private REDeck deck;
	private TestZone test;
	private String testTag;
	
	public DeckZone(Rect area) {
		super(area);
	}
	public DeckZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public DeckZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
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
	public void setDeck() {
		deck = manager.getGame().players()[manager.getGame().viewingPlayer()].deck();
	}
	
	public void update() {
		setDeck();
	}
	
	public void handleDownTouch(MotionEvent event) {
		setDeck();
		RECard temp = (RECard) deck.popTop();
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
		textLocation += TITLE_TEXT_SIZE + 5;
		paint.setTextSize(SUB_TEXT_SIZE);
		canvas.drawText(String.format("%d x %d", area.right-area.left, area.bottom-area.top), area.left + 10, textLocation, paint);
		textLocation += TITLE_TEXT_SIZE + 5;
		canvas.drawText("Cards left: " + deck.size(), area.left + 10, textLocation, paint);
	}
}
