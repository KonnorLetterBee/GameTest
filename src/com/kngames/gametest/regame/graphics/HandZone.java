package com.kngames.gametest.regame.graphics;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.regame.graphics.drawable.TestRECard;

public class HandZone extends REGameZone {
	
	public static final String TAG = HandZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	private Bitmap cardBack;
	
	private REDeck cards;
	private ArrayList<TestRECard> cardPics;
	
	public HandZone(Rect area, Bitmap image) {
		super(area);
		init(image);
	}
	public HandZone(int x, int y, int originCorner, float width, float height, int sizeMode, Bitmap image) {
		super(x, y, originCorner, width, height, sizeMode);
		init(image);
	}
	public HandZone(float x, float y, int originCorner, float width, float height, int sizeMode, Bitmap image) {
		super(x, y, originCorner, width, height, sizeMode);
		init(image);
	}
	private void init(Bitmap image) {
		cards = new REDeck();
		cardPics = new ArrayList<TestRECard>();
		int cardHeight = this.height() - 55;
		cardBack = Bitmap.createScaledBitmap(image, cardHeight * 2/3, cardHeight, false);
	}
	public void postInit() { }
	
	private int currentXPos = 0;
	private int getNextXPos() {
		int temp = currentXPos;
		currentXPos += cardBack.getWidth() + 10;
		return temp;
	}
	private void resetYPos() { currentXPos = area.left + 10; }
	
	//	checks to see if the card list is the same as before
	//	if there is a difference, recreate the pics list
	public void update() {
		REDeck newCards = game.getActivePlayer().hand();
		if (!cards.equals(newCards)) {
			cards = new REDeck(newCards);
			resetYPos();
			cardPics = new ArrayList<TestRECard>();
			for (int i = 0; i < cards.size(); i++) {
				cardPics.add(new TestRECard(getNextXPos() + cardBack.getWidth()/2, area.top + cardBack.getHeight()/2 + 35, (RECard)cards.peek(i), cardBack));
			}
		}
	}
	public void handleDownTouch(MotionEvent event) {
		for (int i = 0; i < cardPics.size(); i++) {
			if (cardPics.get(i).isTouched(event.getX(), event.getY())) {
				getVisiblePlayer().playCard(i);
				return;
			}
		}
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
		
		for (int i = 0; i < cardPics.size(); i++) {
			cardPics.get(i).draw(canvas);
		}
	}
}
