package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.redata.CardTypes.RECard;

@Deprecated
public class InPlayZone extends REGameZone {
	
	public static final String TAG = InPlayZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	public InPlayZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder);
	}
	public InPlayZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder);
	}
	public void postInit() { }
	
	public void update() { super.update(); }
	public void handleDownTouch(float x, float y) { }
	public void handleOffDownTouch(float x, float y) { }
	public void handleMoveTouch(float x, float y) { }
	public void handleUpTouch(float x, float y) { }
	public void handlePressTouch(float x, float y) { }

	//	draws this DeckZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
	public void drawToBitmapCanvas(Canvas canvas) {
		Paint paint = new Paint(); 
		drawTestBorder(canvas);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(TITLE_TEXT_SIZE);
		int textLocation = top() + TITLE_TEXT_SIZE + 5;
		canvas.drawText(TAG, left() + 10, textLocation, paint);

		String[] data = new String[getVisibleInPlay().size()];
		for (int i = 0; i < getVisibleInPlay().size(); i++) {
			data[i] = ((RECard)getVisibleInPlay().peek(i)).getName();
		}
		drawTestData(canvas, data, textLocation + TITLE_TEXT_SIZE + 15);
	}
}
