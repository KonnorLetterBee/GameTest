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
import com.kngames.gametest.regame.gamestruct.GameState.State;
import com.kngames.gametest.regame.graphics.drawable.TestRECard;

public class REDeckViewZone extends REGameZone {
	
	public static final String TAG = REDeckViewZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	private Bitmap cardBack;
	
	private REDeck watchStack;	//	reference to an REDeck that is to always reference the REDeck this zone is responsible for displaying and handling
	
	private REDeck cards;
	private ArrayList<TestRECard> cardPics;
	private boolean[] canPlay;
	
	public static interface REViewZoneCallback {
		REDeck getCompareStack();
		void onNonPINDownTouch(int index);
	}
	
	private REViewZoneCallback callback;
	
	public REDeckViewZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder, Bitmap image, REDeck stack, REViewZoneCallback callback) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder);
		init(image, stack, callback);
	}
	public REDeckViewZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder, Bitmap image, REDeck stack, REViewZoneCallback callback) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder);
		init(image, stack, callback);
	}
	public REDeckViewZone(int actX, int actY, int inactX, int inactY, int originCorner, float width, float height, int sizeMode, int drawOrder, Bitmap image, REDeck stack, REViewZoneCallback callback) {
		super (actX, actY, inactX, inactY, originCorner, width, height, sizeMode, drawOrder);
		init(image, stack, callback);
	}
	public REDeckViewZone(float actX, float actY, float inactX, float inactY, int originCorner, float width, float height, int sizeMode, int drawOrder, Bitmap image, REDeck stack, REViewZoneCallback callback) {
		super (actX, actY, inactX, inactY, originCorner, width, height, sizeMode, drawOrder);
		init(image, stack, callback);
	}
	
	private void init(Bitmap image, REDeck stack, REViewZoneCallback callback) {
		cards = new REDeck();
		this.watchStack = stack;
		this.callback = callback;
		cardPics = new ArrayList<TestRECard>();
		int cardHeight = this.height();
		cardBack = Bitmap.createScaledBitmap(image, cardHeight * 2/3, cardHeight, false);
	}
	public void postInit() { }
	
	//	XPosition variables
	private int currentXPos = 0;
	private int getNextXPos() {
		int temp = currentXPos;
		currentXPos += cardBack.getWidth() + 10;
		return temp;
	}
	private void resetYPos() { currentXPos = left() + 10; }
	
	//	checks to see if the card list is the same as before
	//	if there is a difference, recreate the pics list
	public void update() {
		super.update();
		if (callback != null) {
			REDeck newCards = callback.getCompareStack();
			//	(check no longer applied)
			//	if the list of cards to display has changed, recreate the list of cards to draw
			//if (!cards.equals(newCards)) {
			cards = new REDeck(newCards);
			resetYPos();
			cardPics = new ArrayList<TestRECard>();
				
			//	add the card pics for each card, and test to see whether or not they're playable
			for (int i = 0; i < cards.size(); i++) {
				cardPics.add(new TestRECard(getNextXPos() + cardBack.getWidth()/2, top() + cardBack.getHeight()/2 - BORDER_WIDTH, 
						(RECard)cards.peek(i), cardBack));
			}
		}
	}
	public void handleDownTouch(MotionEvent event) {
		for (int i = cardPics.size() - 1; i >= 0; i--) {
			if (cardPics.get(i).isTouched(event.getX(), event.getY(), true)) {
				if (state.currentState() == State.PlayerInput) {
					state.playerState().onCardSelected(watchStack, i);
				} else {
					callback.onNonPINDownTouch(i);
					return;
				}
			}
		}
	}
	//	to be implemented by extending class, specifies what to do (if anything) when
	//	a card is pressed on when it's not part of a PlayerInputState
	
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }
	public void handlePressTouch(MotionEvent event) { }
	
	//	draws this REDeckViewZone to the screen
	private final int BORDER_WIDTH = 3;
	public void draw(Canvas canvas) {
		drawTestBorder(canvas);
		setCanPlayList();
		
		for (int i = 0; i < cardPics.size(); i++) {
			if (canPlay[i]) {
				TestRECard temp = cardPics.get(i);
				int left = (int)(temp.X() - temp.halfWidth() - BORDER_WIDTH);
				int top = (int)(temp.Y() - temp.halfHeight() - BORDER_WIDTH);
				int right = left + temp.width() + (BORDER_WIDTH*2);
				int bottom = top + temp.height() + (BORDER_WIDTH*2);
				drawCardColorBorder(canvas, new Rect(left, top, right, bottom));
			}
			cardPics.get(i).draw(canvas);
		}
	}
	
	//	iterates through the cards list and marks the ones that can be played
	private void setCanPlayList() {
		canPlay = new boolean[cards.size()];
		for (int i = 0; i < canPlay.length; i++) {
			RECard card = (RECard) cards.peek(i);
			
			if (state.currentState() == State.PlayerInput)
				try { 
					canPlay[i] = state.playerState().isSelectable(callback.getCompareStack(), i);
				} catch (IndexOutOfBoundsException e) {
					canPlay[i] = false;
				}
			else canPlay[i] = card.canPlay(game, game.getActivePlayer(), callback.getCompareStack());
		}
	}
	
	private void drawCardColorBorder(Canvas canvas, Rect area) {
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		canvas.drawRect(area, paint);
	}
}
