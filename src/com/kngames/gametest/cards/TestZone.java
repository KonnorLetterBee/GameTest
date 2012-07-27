package com.kngames.gametest.cards;

import java.util.ArrayList;

import com.kngames.gametest.engine.DrawObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

public class TestZone extends GameZone {

	protected ArrayList<DrawObject> drawables;
	protected TestZone otherZone;
	protected String data;
	
	public TestZone(Rect area, String data) {
		super(area);
		drawables = new ArrayList<DrawObject>();
		this.data = data;
	}

	public void setOtherZone(TestZone other) {
		this.otherZone = other;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public void handleDownTouch(MotionEvent event) {
		otherZone.setData(data);
		this.data = "";
	}

	@Override
	public void handleMoveTouch(MotionEvent event) { }

	@Override
	public void handleUpTouch(MotionEvent event) { }
	
	//	draws this TestZone to the screen
	private final int TITLE_TEXT_SIZE = 25;
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
		canvas.drawText("TEST", area.left + 10, area.top + TITLE_TEXT_SIZE + 5, paint);
		canvas.drawText(data, area.left + 10, area.top + (2 * (TITLE_TEXT_SIZE + 5)), paint);
	}
}
