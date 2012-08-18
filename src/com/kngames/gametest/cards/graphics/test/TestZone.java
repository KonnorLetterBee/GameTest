package com.kngames.gametest.cards.graphics.test;

import java.util.ArrayList;

import com.kngames.gametest.cards.graphics.GameZone;
import com.kngames.gametest.cards.graphics.IDObject;
import com.kngames.gametest.engine.graphics.DrawObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

public class TestZone extends GameZone {

	public static final String TAG = TestZone.class.getSimpleName();
	public static final IDObject id = new IDObject(TAG);
	public String getName() { return id.getName(); }
	public int getId() { return id.getId(); }
	
	protected ArrayList<DrawObject> drawables;
	protected TestZone otherZone;
	protected ArrayList<String> data;
	
	public TestZone(Rect area) {
		super(area);
		drawables = new ArrayList<DrawObject>();
		this.data = new ArrayList<String>();
	}
	
	public TestZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
		drawables = new ArrayList<DrawObject>();
		this.data = new ArrayList<String>();
	}
	
	public void postInit() {
		
	}

	public void setOtherZone(TestZone other) {
		this.otherZone = other;
	}
	
	public void addData(String data) {
		this.data.add(data);
	}
	
	public String popData() {
		if (data.size() > 0) return this.data.remove(0);
		else return null;
	}
	
	public void handleDownTouch(MotionEvent event) {
		String d = popData();
		if (otherZone != null && d != null) otherZone.addData(d);
	}
	public void handleOffDownTouch(MotionEvent event) { }
	public void handleMoveTouch(MotionEvent event) { }
	public void handleUpTouch(MotionEvent event) { }
	public void handlePressTouch(MotionEvent event) { }
	public void update() { }
	
	//	draws this TestZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
	private final int SUB_TEXT_SIZE = 20;
	private final int BORDER_THICKNESS = 4;
	@Override
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
		canvas.drawText("TEST", area.left + 10, textLocation, paint);
		textLocation += TITLE_TEXT_SIZE + 5;
		paint.setTextSize(SUB_TEXT_SIZE);
		canvas.drawText(String.format("%d x %d", area.right-area.left, area.bottom-area.top), area.left + 10, textLocation, paint);
		for (int i = 0; i < data.size(); i++) {
			textLocation += TITLE_TEXT_SIZE + 5;
			canvas.drawText(data.get(i), area.left + 10, textLocation, paint);
		}
	}
}
