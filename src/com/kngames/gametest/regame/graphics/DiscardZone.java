package com.kngames.gametest.regame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.kngames.gametest.R;
import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.engine.ContentManager;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.regame.graphics.drawable.TestRECard;

public class DiscardZone extends REGameZone {
	
	public static final String TAG = DiscardZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	private TestRECard deckBackground;
	private int cardTop;
	
	public DiscardZone(Rect area) {
		super(area);
		setBackground();
	}
	public DiscardZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
		setBackground();
	}
	public DiscardZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
		setBackground();
	}
	private void setBackground() {
		float ratio = (float)area.height() / area.width();
		int cardWidth = 0;
		int cardHeight = 0;
		int cardX = area.left;
		int cardY = area.top;
		if (ratio > 1.4f) {	//	taller than necessary
			cardWidth = area.width();
			cardHeight = (int) (cardWidth * 1.4);
			cardY += (area.height() - cardHeight) / 2;
		} else {	//	wider than necessary
			cardHeight = area.height();
			cardWidth = (int) (cardHeight * 0.714f);
			cardX += (area.width() - cardWidth) / 2;
		}
		ContentManager content = ContentManager.getContentManager();
		assert (content != null);
		Bitmap b = content.getScaledBitmap(R.drawable.card_back, cardWidth, cardHeight);
		deckBackground = new TestRECard(cardX + cardWidth/2, cardY + cardHeight/2, null, b);
		
		this.cardTop = cardY;
	}
	
	public void postInit() { }
	
	public void update() { }
	public void handleDownTouch(MotionEvent event) { }
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }
	public void handlePressTouch(MotionEvent event) { }
	
	//	draws this DiscardZone to the screen
	private final int TEXT_SIZE = 25;
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		if (getVisibleDiscard().size() > 0) deckBackground.draw(canvas);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TEXT_SIZE);
		int x = area.left + (area.width() / 2);
		int y = area.top + (area.height() / 2) - (TEXT_SIZE / 2);
		canvas.drawText(""+getVisibleDiscard().size(), x, y, paint);
		
		String[] data = new String[getVisibleDiscard().size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = ((RECard)getVisibleDiscard().peek(i)).getName();
		}
		drawTestData(canvas, data, cardTop + 20);
	}
}
